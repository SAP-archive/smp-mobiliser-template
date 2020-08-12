/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic.configuration;

import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.util.prefs.api.IPreferences;

/**
 * Central configuration point for custom business logic.
 *
 * @since 2012-01-20
 */
public class CustomProjectConfiguration implements InitializingBean {

    private IPreferences preferences;

    @Override
    public void afterPropertiesSet() {
	if (this.preferences == null) {
	    throw new IllegalStateException(
		    "preferences not set in spring configuration.");
	}
    }

    /**
     * Returns the the value of the preferences key 'intValue'.
     *
     * @return the value as an int or 0 if it did not exist
     */
    public int getIntValue() {
	return this.preferences.getInt("intValue", 0);
    }

    /**
     * Returns the value of the preferences key 'stringValue'.
     *
     * @return the value as a string or null if it did not exist
     */
    public String getStrValue() {
	return this.preferences.get("strValue", null);
    }

    /**
     * @param preferences
     *            the preferences to set
     */
    public void setPreferences(final IPreferences preferences) {
	this.preferences = preferences;
    }

}
