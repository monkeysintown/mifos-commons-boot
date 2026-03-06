/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.transport.rest.imperative.autoconfigure;

import static org.mifos.commons.boot.transport.rest.core.MifosTransportRestConstants.MIFOS_COMMONS_BOOT_TRANSPORT_REST_IMPERATIVE_PACKAGE;

import org.mifos.commons.boot.transport.rest.core.MifosTransportRestProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for setting up REST transport components in the Mifos Initiative.
 *
 * <p>This configuration provides beans for locale resolution, pagination argument handling, OpenAPI documentation, CORS
 * policies, and tenant identification.
 *
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 * @since 1.0.0
 * @see MifosTransportRestProperties
 * @see Configuration
 */
@Configuration
@ComponentScan(MIFOS_COMMONS_BOOT_TRANSPORT_REST_IMPERATIVE_PACKAGE)
class MifosTransportRestImperativeAutoConfiguration {
    // TODO: fix modules (UnitJacksonModule, MoneyModule, JtsModule, ShapesAsGeoJSONModule)
}
