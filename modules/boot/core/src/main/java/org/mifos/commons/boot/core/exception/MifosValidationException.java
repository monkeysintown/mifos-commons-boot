/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.exception;

import java.io.Serial;
import lombok.Getter;
import org.springframework.validation.Errors;

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
        this.errors = errors;
    }
}
