/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.persistence.relational.reactive;

import static org.mifos.commons.boot.persistence.relational.core.MifosPersistenceRelationalConstants.MIFOS_COMMONS_BOOT_PERSISTENCE_RELATIONAL_BASE_PACKAGE;

import com.infobip.spring.data.r2dbc.EnableQuerydslR2dbcRepositories;
import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepositoryFactoryBean;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spatial.PostGISTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.EnumByNameType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mifos.commons.boot.persistence.relational.core.MifosPersistenceRelationalProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** TBD */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({MifosPersistenceRelationalProperties.class})
@EnableQuerydslR2dbcRepositories
@EnableR2dbcRepositories(
        // TODO: should we just scan "org.mifos"?
        basePackages = MIFOS_COMMONS_BOOT_PERSISTENCE_RELATIONAL_BASE_PACKAGE,
        repositoryFactoryBeanClass = QuerydslR2dbcRepositoryFactoryBean.class)
@EnableR2dbcAuditing
@EnableTransactionManagement
public class MifosPersistenceRelationalReactiveAutoConfiguration {
    private final MifosPersistenceRelationalProperties properties;

    @Bean
    SQLTemplates sqlTemplates() {
        // TODO: figure out auto detection
        return PostGISTemplates.builder().build();
    }

    @Bean
    @Primary
    com.querydsl.sql.Configuration querydslSqlConfiguration(
            SQLTemplates sqlTemplates, List<EnumByNameType<?>> enumsByNameType) {
        var configuration = new com.querydsl.sql.Configuration(sqlTemplates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());

        enumsByNameType.forEach(configuration::register);

        return configuration;
    }
}
