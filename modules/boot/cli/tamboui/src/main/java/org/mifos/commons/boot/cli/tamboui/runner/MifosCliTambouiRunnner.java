///
/// This Source Code Form is subject to the terms of the Mozilla Public
/// License, v. 2.0. If a copy of the MPL was not distributed with this
/// file, You can obtain one at http://mozilla.org/MPL/2.0/.
///
package org.mifos.commons.boot.cli.tamboui.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mifos.commons.boot.core.cli.MifosCliRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
final class MifosCliTambouiRunnner implements MifosCliRunner {
    private int exitCode;

    @Override
    public void run(String... args) throws Exception {
        // TODO: implement this
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
