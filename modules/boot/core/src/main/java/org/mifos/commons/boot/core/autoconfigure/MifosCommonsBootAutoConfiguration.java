/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.autoconfigure;

import com.google.errorprone.annotations.Var;
import jakarta.validation.MessageInterpolator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.mifos.commons.boot.core.MifosCommonsBootProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_CORE_PACKAGE;
import static org.mifos.commons.boot.core.MifosConstants.MIFOS_MESSAGE_BASE;

@Slf4j
@EnableConfigurationProperties({MifosCommonsBootProperties.class})
@ComponentScan(MIFOS_COMMONS_BOOT_CORE_PACKAGE)
class MifosCommonsBootAutoConfiguration {
    private static final String CLASSPATH = "classpath:";

    @Bean
    MessageSource messageSource() throws IOException {
        var messageSource = new ReloadableResourceBundleMessageSource();
        var resolver = new PathMatchingResourcePatternResolver();
        var resources = resolver.getResources(CLASSPATH + MIFOS_MESSAGE_BASE + "/*/messages.properties");
        var basenames = new HashSet<String>();

        for (var resource : resources) {
            String path = resource.getFilePath().toString();
            var basename = path.replace(CLASSPATH, "").replaceAll("/[^/]+\\.properties$", "");
            basenames.add(basename);
        }

        messageSource.setBasenames(basenames.toArray(new String[0]));
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    MessageInterpolator messageInterpolator(MessageSource messageSource) {
        MessageSourceResourceBundleLocator resourceBundleLocator =
                new MessageSourceResourceBundleLocator(messageSource);
        ResourceBundleMessageInterpolator messageInterpolator =
                new ResourceBundleMessageInterpolator(resourceBundleLocator);

        return new PhRecursiveLocaleContextMessageInterpolator(messageInterpolator);
    }

    @Bean
    Validator validatorFactoryBean(MessageSource messageSource) {
        var localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);

        return localValidatorFactoryBean;
    }

    static class PhRecursiveLocaleContextMessageInterpolator implements MessageInterpolator {
        private static final Pattern PATTERN_PLACEHOLDER = Pattern.compile("\\{([^}]+)\\}");

        private final MessageInterpolator interpolator;

        public PhRecursiveLocaleContextMessageInterpolator(ResourceBundleMessageInterpolator interpolator) {
            this.interpolator = interpolator;
        }

        @Override
        public String interpolate(String messageTemplate, Context context) {
            return interpolate(messageTemplate, context, Locale.getDefault());
        }

        @Override
        @SuppressWarnings("PMD.AvoidReassigningParameters")
        public String interpolate(@Var String messageTemplate, Context context, Locale locale) {
            @Var var level = 0;
            while (containsPlaceholder(messageTemplate) && level < 2) {
                messageTemplate = this.interpolator.interpolate(messageTemplate, context, locale);
                level++;
            }
            return messageTemplate;
        }

        private static boolean containsPlaceholder(String code) {
            Matcher matcher = PATTERN_PLACEHOLDER.matcher(code);
            return matcher.find();
        }
    }
}
