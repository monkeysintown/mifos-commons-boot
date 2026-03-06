/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core;

import lombok.experimental.UtilityClass;

/**
 * Utility class holding constant values used throughout the Mifos messaging system.
 *
 * <p>This class defines base paths, prefixes, and specific message keys for resolving internationalized messages and
 * error codes within the Mifos application.
 *
 * <p>Since this is a utility class (annotated with Lombok's {@code @UtilityClass}), it is not intended to be
 * instantiated, and no public constructor is provided.
 *
 * @since 1.0.0
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 * @see java.util.ResourceBundle
 * @see lombok.experimental.UtilityClass
 */
@UtilityClass
public class MifosCommonsBootConstants {
    public static final String MIFOS_COMMONS_BOOT_BASE_PACKAGE = "org.mifos.commons.boot";
    public static final String MIFOS_COMMONS_BOOT_CORE_PACKAGE = MIFOS_COMMONS_BOOT_BASE_PACKAGE + ".core";
    public static final String MIFOS_COMMONS_BOOT_MESSAGE_BASE = "org/mifos/commons/messages";
    public static final String MIFOS_COMMONS_BOOT_MESSAGE_PREFIX = "org.mifos.commons.";
    public static final String MIFOS_COMMONS_BOOT_MESSAGE_VALIDATION_ERROR =
            MIFOS_COMMONS_BOOT_MESSAGE_PREFIX + "validation.error";
    public static final String MIFOS_COMMONS_BOOT_HEADER_TENANT_ID = "x-mifos-tenant-id";
    public static final String MIFOS_COMMONS_BOOT_PROPERTIES_PREFIX = "mifos.commons";
}
