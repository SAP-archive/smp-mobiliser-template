/**
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
