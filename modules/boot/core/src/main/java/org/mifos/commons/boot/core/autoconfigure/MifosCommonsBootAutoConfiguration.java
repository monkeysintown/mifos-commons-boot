/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.core.autoconfigure;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_CORE_PACKAGE;
import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_MESSAGE_BASE;

import com.google.errorprone.annotations.Var;
import jakarta.validation.MessageInterpolator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.mifos.commons.boot.core.MifosCommonsBootProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

@Slf4j
@EnableConfigurationProperties({MifosCommonsBootProperties.class})
@ComponentScan(MIFOS_COMMONS_BOOT_CORE_PACKAGE)
class MifosCommonsBootAutoConfiguration {

    @Bean
    MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:" + MIFOS_COMMONS_BOOT_MESSAGE_BASE);
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
