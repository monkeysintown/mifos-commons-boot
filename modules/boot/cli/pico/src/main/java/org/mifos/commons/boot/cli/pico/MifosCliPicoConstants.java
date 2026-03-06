///
/// This Source Code Form is subject to the terms of the Mozilla Public
/// License, v. 2.0. If a copy of the MPL was not distributed with this
/// file, You can obtain one at http://mozilla.org/MPL/2.0/.
///
package org.mifos.commons.boot.cli.pico;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_CLI_PACKAGE;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MifosCliPicoConstants {
    public static final String MIFOS_COMMONS_BOOT_CLI_PICO_PACKAGE = MIFOS_COMMONS_BOOT_CLI_PACKAGE + ".pico";
    public static final String MIFOS_COMMONS_BOOT_CLI_PICO_COMMAND_PACKAGE =
            MIFOS_COMMONS_BOOT_CLI_PICO_PACKAGE + ".command";
    public static final String MIFOS_COMMONS_BOOT_CLI_PICO_RUNNER_PACKAGE =
            MIFOS_COMMONS_BOOT_CLI_PICO_PACKAGE + ".runner";
}
