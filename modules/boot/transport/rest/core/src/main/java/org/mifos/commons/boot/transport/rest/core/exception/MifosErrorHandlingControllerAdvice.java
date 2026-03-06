/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.transport.rest.core.exception;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_MESSAGE_VALIDATION_ERROR;
import static org.mifos.commons.boot.core.model.MifosCommonsErrorCode.MIFOS_COMMONS_ERROR_VALIDATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mifos.commons.boot.core.MifosCommonsBootProperties;
import org.mifos.commons.boot.core.exception.MifosBaseException;
import org.mifos.commons.boot.core.exception.MifosValidationException;
import org.mifos.commons.boot.core.model.MifosError;
import org.springframework.context.MessageSource;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * Global REST controller advice for handling exceptions and producing Problem Detail responses (<a
 * href="https://datatracker.ietf.org/doc/html/rfc7807" target="_blank">RFC 7807</a>) for the Mifos application.
 *
 * <p>This advice intercepts exceptions thrown by controller methods and transforms them into structured
 * {@link org.springframework.http.ProblemDetail} responses. It is designed to handle both low-level framework
 * exceptions and custom Mifos exceptions, providing consistent error formatting with additional "errors" property where
 * applicable.
 *
 * <p>The advice is generic over {@code T extends Throwable} and participates in exception handling for all controller
 * advice methods. It produces responses with content type {@code application/problem+json}.
 *
 * @param <T> the type of throwable that can be handled by the generic handler method; typically {@link Throwable} or
 *     any subtype
 * @see org.springframework.web.bind.annotation.RestControllerAdvice
 * @see org.springframework.http.ProblemDetail
 * @see MifosCommonsBootProperties
 * @see org.springframework.context.MessageSource
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class MifosErrorHandlingControllerAdvice<T extends Throwable> {

    private final MessageSource messageSource;
    private final MifosCommonsBootProperties properties;

    @RequestMapping(produces = APPLICATION_PROBLEM_JSON_VALUE)
    @ExceptionHandler(DataBufferLimitException.class)
    public ResponseEntity<ProblemDetail> handleMaxSizeException(DataBufferLimitException exc) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "File is too large!");
        return ResponseEntity.status(EXPECTATION_FAILED).body(problemDetails);
    }

    @RequestMapping(produces = APPLICATION_PROBLEM_JSON_VALUE)
    @ExceptionHandler(Throwable.class)
    ResponseEntity<ProblemDetail> handleValidationException(T e) {
        // see:
        // https://dev.to/noelopez/spring-rest-exception-handling-problem-details-2hkj#:~:text=ProblemDetail%3A%20The%20main%20class%20representing,additional%2C%20non%2Dstandard%20properties.
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getLocalizedMessage());
        problemDetails.setType(URI.create(properties.getErrorUrl() + "/validation"));
        problemDetails.setTitle(messageSource.getMessage(
                MIFOS_COMMONS_BOOT_MESSAGE_VALIDATION_ERROR, null, Locale.US)); // TODO: check this, get locale

        List<MifosError> errors = new ArrayList<>();

        switch (e) {
            case MifosBaseException rcbe -> {
                problemDetails.setType(URI.create(properties.getErrorUrl() + "/" + rcbe.getCode()));
                problemDetails.setTitle(rcbe.getCode());
            }
            case ConstraintViolationException cve -> {
                for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                    errors.add(MifosError.of(
                            MIFOS_COMMONS_ERROR_VALIDATION,
                            violation.getPropertyPath().toString(),
                            violation.getMessage()));
                }
            }
            case WebExchangeBindException webe -> {
                for (FieldError fieldError : webe.getFieldErrors()) {
                    errors.add(MifosError.of(
                            MIFOS_COMMONS_ERROR_VALIDATION, fieldError.getField(), fieldError.getDefaultMessage()));
                }
            }
            case MethodArgumentNotValidException manve -> {
                for (FieldError fieldError : manve.getBindingResult().getFieldErrors()) {
                    errors.add(MifosError.of(
                            MIFOS_COMMONS_ERROR_VALIDATION, fieldError.getField(), fieldError.getDefaultMessage()));
                }
            }
            case MifosValidationException rcve -> {
                for (FieldError fieldError : rcve.getErrors().getFieldErrors()) {
                    errors.add(MifosError.of(
                            MIFOS_COMMONS_ERROR_VALIDATION, fieldError.getField(), fieldError.getDefaultMessage()));
                }
            }
            default -> {
                if (log.isDebugEnabled()) {
                    log.debug("Unhandled exception: ", e);
                }
            }
        }

        if (!errors.isEmpty()) {
            problemDetails.setProperty("errors", errors);
        }

        return ResponseEntity.badRequest().contentType(APPLICATION_PROBLEM_JSON).body(problemDetails);
    }
}
