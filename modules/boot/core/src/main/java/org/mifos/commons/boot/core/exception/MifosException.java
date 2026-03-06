 /**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.exception;

 import lombok.Getter;
 import lombok.RequiredArgsConstructor;
 import org.mifos.commons.boot.core.model.MifosError;
 import org.mifos.commons.boot.core.model.MifosErrorCode;

 import java.io.Serial;

 import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_ERROR_CODE_INCREMENT;
 import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_ERROR_CODE_START;
 import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_MESSAGE_ERROR_PREFIX;
 import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_MESSAGE_PREFIX;

 public class MifosException extends MifosBaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MifosException(MifosError error) {
        super(error);
    }

    @Getter
    @RequiredArgsConstructor
    public enum MifosCommonErrorCode implements MifosErrorCode {
        // TODO: finish this

        /** Write some more Javadoc documentation... */
        MIFOS_COMMONS_ERROR_UNKNOWN(MIFOS_COMMONS_BOOT_ERROR_CODE_START, MIFOS_COMMONS_BOOT_MESSAGE_ERROR_PREFIX + ".unknown"),
        /** Write some Javadoc documentation... */
        MIFOS_COMMONS_ERROR_NOT_FOUND(MIFOS_COMMONS_BOOT_ERROR_CODE_START + MIFOS_COMMONS_BOOT_ERROR_CODE_INCREMENT, MIFOS_COMMONS_BOOT_MESSAGE_ERROR_PREFIX + ".not-found");

        private final int value;
        private final String key;

        @Override
        public String getName() {
            return name();
        }
    }
}
