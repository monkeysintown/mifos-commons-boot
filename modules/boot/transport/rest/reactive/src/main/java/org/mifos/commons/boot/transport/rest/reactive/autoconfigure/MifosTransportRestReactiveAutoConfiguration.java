/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.transport.rest.reactive.autoconfigure;

import static org.mifos.commons.boot.transport.rest.core.MifosTransportRestConstants.MIFOS_COMMONS_BOOT_TRANSPORT_REST_REACTIVE_PACKAGE;

import org.mifos.commons.boot.transport.rest.core.MifosTransportRestProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

/**
 * Spring configuration class for setting up REST transport components in the Mifos Initiative.
 *
 * <p>This configuration provides beans for locale resolution, pagination argument handling, OpenAPI documentation, CORS
 * policies, and tenant identification.
 *
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 * @since 1.0.0
 * @see MifosTransportRestProperties
 * @see org.springframework.context.annotation.Configuration
 */
@Configuration
@ComponentScan(MIFOS_COMMONS_BOOT_TRANSPORT_REST_REACTIVE_PACKAGE)
class MifosTransportRestReactiveAutoConfiguration {
    // TODO: fix modules (UnitJacksonModule, MoneyModule, JtsModule, ShapesAsGeoJSONModule)

    @Bean
    WebFluxConfigurer springDataArgumentResolversConfigurer(PageRequest pageRequest) {
        return new WebFluxConfigurer() {
            @Override
            public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
                var argumentResolver = new ReactiveSortHandlerMethodArgumentResolver();

                var resolver = new ReactivePageableHandlerMethodArgumentResolver(argumentResolver);
                resolver.setFallbackPageable(pageRequest);

                configurer.addCustomResolver(argumentResolver, resolver);
            }
        };
    }
}
