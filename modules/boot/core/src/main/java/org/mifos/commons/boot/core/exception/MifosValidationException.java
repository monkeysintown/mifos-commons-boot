/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mifos.commons.boot.core.model.MifosErrorCode;
import org.springframework.validation.Errors;

import java.io.Serial;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_ERROR_CODE_VALIDATION_INCREMENT;
import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_ERROR_CODE_VALIDATION_START;
import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_MESSAGE_ERROR_VALIDATION_PREFIX;

/**
 * Exception thrown when validation errors occur within the Mifos system.
 *
 * <p>This runtime exception encapsulates a collection of validation errors represented by an {@link Errors} object. It
 * is intended to be used during request validation to signal that the input data does not satisfy business constraints.
 *
 * <p>The {@code errors} field is marked as {@code transient} to avoid serialization of the error details, as the
 * {@link Errors} object may not be serializable. The exception still carries the validation information in-memory for
 * handling within the same JVM instance.
 *
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 * @see Errors
 * @since 1.0.0
 */
@Getter
public class MifosValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final transient Errors errors;

    public MifosValidationException(Errors errors) {
        // TODO: should be based on MifosBaseException
        this.errors = errors;
    }

    @Getter
    @RequiredArgsConstructor
    public enum MifosValidationErrorCode implements MifosErrorCode {
        MIFOS_COMMONS_ERROR_VALIDATION_UNKNOWN(MIFOS_COMMONS_BOOT_ERROR_CODE_VALIDATION_START, MIFOS_COMMONS_BOOT_MESSAGE_ERROR_VALIDATION_PREFIX),
        MIFOS_COMMONS_ERROR_VALIDATION(MIFOS_COMMONS_BOOT_ERROR_CODE_VALIDATION_START + MIFOS_COMMONS_BOOT_ERROR_CODE_VALIDATION_INCREMENT, MIFOS_COMMONS_BOOT_MESSAGE_ERROR_VALIDATION_PREFIX),
        MIFOS_COMMONS_ERROR_VALIDATION_CONSTRAINT(MIFOS_COMMONS_BOOT_ERROR_CODE_VALIDATION_START + 2 * MIFOS_COMMONS_BOOT_ERROR_CODE_VALIDATION_INCREMENT, MIFOS_COMMONS_BOOT_MESSAGE_ERROR_VALIDATION_PREFIX),
        ;

        private final int value;
        private final String key;

        @Override
        public String getName() {
            return name();
        }
    }
}
