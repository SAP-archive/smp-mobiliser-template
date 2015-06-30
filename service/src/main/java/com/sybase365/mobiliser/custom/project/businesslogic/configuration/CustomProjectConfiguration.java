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
