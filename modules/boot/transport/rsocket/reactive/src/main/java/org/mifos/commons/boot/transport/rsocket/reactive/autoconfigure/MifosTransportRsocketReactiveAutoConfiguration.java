/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.transport.rsocket.reactive.autoconfigure;

import static org.mifos.commons.boot.transport.rsocket.core.MifosTransportRsocketConstants.MIFOS_COMMONS_BOOT_TRANSPORT_RSOCKET_REACTIVE_PACKAGE;

import org.mifos.commons.boot.transport.rsocket.core.MifosTransportRsocketProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MifosTransportRsocketProperties.class})
@ComponentScan(MIFOS_COMMONS_BOOT_TRANSPORT_RSOCKET_REACTIVE_PACKAGE)
class MifosTransportRsocketReactiveAutoConfiguration {
    // TODO: implement this
}
