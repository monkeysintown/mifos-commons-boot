///
/// This Source Code Form is subject to the terms of the Mozilla Public
/// License, v. 2.0. If a copy of the MPL was not distributed with this
/// file, You can obtain one at http://mozilla.org/MPL/2.0/.
///
package org.mifos.commons.boot.debug.cache;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_DEBUG_PROPERTY_CACHE_CAFFEINE_ENABLED;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnBooleanProperty(MIFOS_COMMONS_BOOT_DEBUG_PROPERTY_CACHE_CAFFEINE_ENABLED)
final class CaffeineRemovalListener implements RemovalListener<Object, Object> {
    @Override
    public void onRemoval(@Nullable Object key, @Nullable Object value, RemovalCause cause) {
        if (cause.wasEvicted()) {
            log.debug("Removed ({}): {} ({})", cause, key, cause.getDeclaringClass());
        }
    }
}
