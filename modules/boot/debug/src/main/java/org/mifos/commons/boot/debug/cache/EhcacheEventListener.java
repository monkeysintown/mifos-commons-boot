///
/// This Source Code Form is subject to the terms of the Mozilla Public
/// License, v. 2.0. If a copy of the MPL was not distributed with this
/// file, You can obtain one at http://mozilla.org/MPL/2.0/.
///
package org.mifos.commons.boot.debug.cache;

import static org.mifos.commons.boot.core.MifosCommonsBootConstants.MIFOS_COMMONS_BOOT_DEBUG_PROPERTY_CACHE_EHCACHE_ENABLED;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnBooleanProperty(MIFOS_COMMONS_BOOT_DEBUG_PROPERTY_CACHE_EHCACHE_ENABLED)
final class EhcacheEventListener implements CacheEventListener<Object, Object> {
    @Override
    public void onEvent(CacheEvent event) {
        switch (event.getType()) {
            case CREATED ->
                log.debug(
                        "Element created: {} - old = {}, new = {}",
                        event.getKey(),
                        event.getOldValue(),
                        event.getNewValue());
            case UPDATED ->
                log.debug(
                        "Element update: {} - old = {}, new = {}",
                        event.getKey(),
                        event.getOldValue(),
                        event.getNewValue());
            case REMOVED ->
                log.debug(
                        "Element removed: {} - old = {}, new = {}",
                        event.getKey(),
                        event.getOldValue(),
                        event.getNewValue());
            case EXPIRED ->
                log.debug(
                        "Element expired: {} - old = {}, new = {}",
                        event.getKey(),
                        event.getOldValue(),
                        event.getNewValue());
            case EVICTED ->
                log.debug(
                        "Element evicted: {} - old = {}, new = {}",
                        event.getKey(),
                        event.getOldValue(),
                        event.getNewValue());
        }
    }
}
