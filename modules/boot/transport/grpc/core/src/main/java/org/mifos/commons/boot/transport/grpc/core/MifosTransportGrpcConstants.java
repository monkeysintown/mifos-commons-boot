/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.transport.grpc.core;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_BASE_PACKAGE;
import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_PROPERTIES_PREFIX;

import lombok.experimental.UtilityClass;

/**
 * Utility class holding constant values used throughout the Mifos messaging system.
 *
 * @since 1.0.0
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 */
@UtilityClass
public class MifosTransportGrpcConstants {
    public static final String MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_BASE_PACKAGE =
            MIFOS_COMMONS_BOOT_BASE_PACKAGE + ".transport.grpc";
    public static final String MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_CORE_PACKAGE =
            MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_BASE_PACKAGE + ".core";
    public static final String MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_IMPERATIVE_PACKAGE =
            MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_BASE_PACKAGE + ".imperative";
    public static final String MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_REACTIVE_PACKAGE =
            MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_BASE_PACKAGE + ".reactive";
    public static final String MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_PROPERTIES_PREFIX =
            MIFOS_COMMONS_BOOT_PROPERTIES_PREFIX + ".transport.grpc";
}
