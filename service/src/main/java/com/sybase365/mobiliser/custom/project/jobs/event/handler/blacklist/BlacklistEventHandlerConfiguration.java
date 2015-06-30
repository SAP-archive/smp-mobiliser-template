/**
 * Copyright (C) 2012-2015 SAP SE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sybase365.mobiliser.custom.project.jobs.event.handler.blacklist;

import com.sybase365.mobiliser.money.jobs.event.handler.util.MoneyEventHandlerConfiguration;

/**
 * @since 2012-01-23
 */
public class BlacklistEventHandlerConfiguration extends
	MoneyEventHandlerConfiguration {

    /**
     * The preferences key for the template to send if customer is added to
     * blacklist
     */
    public static final String PREFS_BLACKLIST_ADDED_TEMPLATE = "blacklist.template.added";

    /**
     * The preferences key for the template to send if customer is removed from
     * blacklist
     */
    public static final String PREFS_BLACKLIST_REMOVED_TEMPLATE = "blacklist.template.removed";

    /**
     * Gets the template to send if customer is added to blacklist
     *
     * @return the template to send if customer is added to blacklist
     *
     * @see BlacklistEventHandlerConfiguration#PREFS_BLACKLIST_ADDED_TEMPLATE
     */
    public String getBlacklistAddedTemplate() {
	return this.preferences.get(PREFS_BLACKLIST_ADDED_TEMPLATE, null);
    }

    /**
     * Gets the template to send if customer is removed from blacklist
     *
     * @return the template to send if customer is removed from blacklist
     *
     * @see BlacklistEventHandlerConfiguration#PREFS_BLACKLIST_REMOVED_TEMPLATE
     */
    public String getBlacklistRemovedTemplate() {
	return this.preferences.get(PREFS_BLACKLIST_REMOVED_TEMPLATE, null);
    }

}
