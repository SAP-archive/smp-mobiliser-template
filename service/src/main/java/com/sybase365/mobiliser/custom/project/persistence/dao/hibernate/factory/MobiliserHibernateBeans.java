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
package com.sybase365.mobiliser.custom.project.persistence.dao.hibernate.factory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.util.ConfigHelper;

import com.sybase365.mobiliser.custom.project.persistence.model.Blacklist;
import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;
import com.sybase365.mobiliser.framework.persistence.hibernate.sessionfactory.api.PersistenceClass;
import com.sybase365.mobiliser.framework.persistence.hibernate.sessionfactory.api.PersistenceServiceProvider;

/**
 * @since 2012-01-18
 */
public class MobiliserHibernateBeans implements PersistenceServiceProvider {

    private String ehCacheConfigurationFileName;

    @Override
    public PersistenceClass[] getPersistenceClasses() {

	final List<PersistenceClass> lpc = new ArrayList<PersistenceClass>();

	// First add all bean classes that needs cache concurrency mode
	// read/write
	lpc.addAll(PersistenceClass.createBeans(new Class[] { Blacklist.class,
		BlacklistType.class }, CacheConcurrencyStrategy.READ_WRITE));

	// Some persistence class instances are updated rarely therefore use
	// NONSTRICT_READ_WRITE
	lpc.addAll(PersistenceClass.createBeans(new Class[] {},
		CacheConcurrencyStrategy.NONSTRICT_READ_WRITE));

	// special handling for non chached collection
	lpc.addAll(PersistenceClass.createBeans(new Class[] {},
		CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
		CacheConcurrencyStrategy.NONE));

	lpc.addAll(PersistenceClass.createBeans(new Class[] {},
		CacheConcurrencyStrategy.NONE));

	// Some persistence class instances are never updated. These can use
	// the concurrency strategy READ

	lpc.addAll(PersistenceClass.createBeans(new Class[] {},
		CacheConcurrencyStrategy.READ_ONLY));

	// in case the superclass is already cached it is not allowed to set a
	// cache concurrency strategy on any children!!
	lpc.addAll(PersistenceClass.createBeans(new Class[] {}, null));

	return lpc.toArray(new PersistenceClass[lpc.size()]);
    }

    @Override
    public com.sybase365.mobiliser.framework.persistence.hibernate.sessionfactory.api.DbVersion getRequiredDatabaseVersion() {
	return new com.sybase365.mobiliser.framework.persistence.hibernate.sessionfactory.api.DbVersion(
		"CUST_PROJ", 1, 0);
    }

    @Override
    public String getEhCacheConfigurationFileName() {
	return ConfigHelper.findAsResource(this.ehCacheConfigurationFileName)
		.toExternalForm();
    }

    /**
     * @param ehCacheConfigurationFileName
     *            the ehCacheConfigurationFileName to set
     */
    public void setEhCacheConfigurationFileName(
	    final String ehCacheConfigurationFileName) {
	this.ehCacheConfigurationFileName = ehCacheConfigurationFileName;
    }

}
