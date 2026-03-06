/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.exception;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_MESSAGE_PREFIX;

import java.io.Serial;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mifos.commons.boot.core.model.MifosError;
import org.mifos.commons.boot.core.model.MifosErrorCode;

public class MifosException extends MifosBaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MifosException(MifosError error, Object... args) {
        super(MIFOS_COMMONS_BOOT_MESSAGE_PREFIX, error, args);
    }

    public MifosException(MifosError error, Throwable cause, Object... args) {
        super(MIFOS_COMMONS_BOOT_MESSAGE_PREFIX, error, cause, args);
    }

    @Getter
    @RequiredArgsConstructor
    public enum MifosCommonErrorCode implements MifosErrorCode {
        // TODO: finish this

        /** Write some Javadoc documentation... */
        MIFOS_COMMONS_ERROR_NOT_FOUND(100),
        /** Write some more Javadoc documentation... */
        MIFOS_COMMONS_ERROR_UNKNOWN(100_000);

        private final int value;

        @Override
        public String getName() {
            return name();
        }
    }
}
