/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.exception;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_MESSAGE_BASE;

import com.google.errorprone.annotations.Var;
import java.io.Serial;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.mifos.commons.boot.core.model.MifosError;

/**
 * Abstract base class for all custom exceptions thrown by the Mifos system.
 *
 * <p>This exception enriches the standard runtime exception with a structured error code, a configurable message
 * prefix, and a formatted message that includes a unique bolt symbol ({@value #BOLT_SYMBOL}). The final message is
 * composed as: {@code <errorCode> <boltSymbol> <localizedErrorMessage>}.
 *
 * <p>Subclasses must provide a concrete implementation by supplying a {@code prefix} and using a specific
 * {@link MifosError} enum value. The error message is resolved from resource bundles based on the {@code prefix} and
 * the error key, supporting internationalization. Optional arguments can be passed for parameterized messages.
 *
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 * @since 1.0.0
 * @see RuntimeException
 * @see MifosError
 */
public abstract class MifosBaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String BOLT_SYMBOL = "\uD83C\uDF72"; // bolt symbol

    protected final String prefix;
    protected final MifosError error;

    @Getter
    protected final String message;

    @Getter
    protected final String code;

    protected MifosBaseException(String prefix, MifosError error, Object... args) {
        this.code = StringUtils.leftPad(String.valueOf(error.getCode().getValue()), 6, '0');
        this.prefix = prefix;
        this.error = error;

        // TODO: retrieve current language
        @Var
        var errorMessage = ResourceBundle.getBundle(MIFOS_COMMONS_BOOT_MESSAGE_BASE, Locale.US)
                .getString(prefix + "." + error.getCode().getName());

        if (args != null && args.length > 0) {
            errorMessage = MessageFormat.format(errorMessage, Arrays.copyOf(args, args.length));
        }

        this.message = "%s %s %s".formatted(this.code, BOLT_SYMBOL, errorMessage);
    }

    protected MifosBaseException(String prefix, MifosError error, Throwable cause, Object... args) {
        super(cause);

        this.code = StringUtils.leftPad(String.valueOf(error.getCode().getValue()), 6, '0');
        this.prefix = prefix;
        this.error = error;

        // TODO: retrieve current language
        @Var
        var errorMessage = ResourceBundle.getBundle(MIFOS_COMMONS_BOOT_MESSAGE_BASE, Locale.US)
                .getString(prefix + "." + error.getCode().getName());

        if (args != null && args.length > 0) {
            errorMessage = MessageFormat.format(errorMessage, Arrays.copyOf(args, args.length));
        }

        this.message = "%s %s %s".formatted(this.code, BOLT_SYMBOL, errorMessage);
    }
}
