/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MifosCommonsErrorCode implements MifosErrorCode {
    MIFOS_COMMONS_ERROR_VALIDATION(100),
    MIFOS_COMMONS_ERROR_USECASE_DUPLICATE(210),
    MIFOS_COMMONS_ERROR_USECASE_NOT_FOUND(220),
    MIFOS_COMMONS_ERROR_USECASE_REQUEST_TYPE_NULL(310),
    MIFOS_COMMONS_ERROR_USECASE_REQUEST_TYPE_ILLEGAL(320),
    MIFOS_COMMONS_ERROR_USECASE_REQUEST_TYPE_NOT_FOUND(330),
    MIFOS_COMMONS_ERROR_USECASE_REQUEST_INSTANCE_EMPTY(340),
    MIFOS_COMMONS_ERROR_USECASE_RESPONSE_SERIALIZATION(410),
    ;

    private final int value;

    @Override
    public String getName() {
        return name();
    }
}
