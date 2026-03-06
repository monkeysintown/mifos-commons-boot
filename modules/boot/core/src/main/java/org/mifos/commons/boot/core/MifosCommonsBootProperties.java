/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_PROPERTIES_PREFIX;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Mifos common functionality.
 *
 * <p>This class holds configuration settings that can be defined in the application's configuration files (e.g.,
 * {@code application.yml} or {@code application.properties}) under the prefix {@code mifos.common}. It is designed to
 * be used with Spring Boot's {@code @ConfigurationProperties} binding.
 *
 * <p>The class is annotated with Lombok annotations to reduce boilerplate:
 *
 * <ul>
 *   <li>{@code @Builder} provides a builder pattern for constructing instances.
 *   <li>{@code @Data} generates getters, setters, {@code toString()}, {@code equals()}, and {@code hashCode()} methods.
 *   <li>{@code @NoArgsConstructor} and {@code @AllArgsConstructor} generate the corresponding constructors.
 * </ul>
 *
 * <p>Example usage in {@code application.yml}:
 *
 * <pre>{@code
 * mifos:
 *   common:
 *     error-url: "https://custom.domain/errors"
 * }</pre>
 *
 * @author <a href="https://github.com/vidakovic" target="_blank">Aleksandar Vidakovic</a>
 * @since 1.0.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = MIFOS_COMMONS_BOOT_PROPERTIES_PREFIX)
public class MifosCommonsBootProperties {
    @Builder.Default
    private String errorUrl = "https://doc.mifos.org/projects/common/errors";
}
