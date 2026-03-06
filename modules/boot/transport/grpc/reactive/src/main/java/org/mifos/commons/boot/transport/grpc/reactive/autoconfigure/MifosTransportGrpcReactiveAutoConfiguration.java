/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.transport.grpc.reactive.autoconfigure;

import static org.mifos.commons.boot.transport.grpc.core.MifosTransportGrpcConstants.MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_REACTIVE_PACKAGE;

import org.mifos.commons.boot.transport.grpc.core.MifosTransportGrpcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MifosTransportGrpcProperties.class})
@ComponentScan(MIFOS_COMMONS_BOOT_TRANSPORT_GRPC_REACTIVE_PACKAGE)
class MifosTransportGrpcReactiveAutoConfiguration {
    // TODO: implement this
}
