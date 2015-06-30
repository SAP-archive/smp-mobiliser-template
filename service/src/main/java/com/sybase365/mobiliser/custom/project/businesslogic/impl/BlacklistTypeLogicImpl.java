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
package com.sybase365.mobiliser.custom.project.businesslogic.impl;

import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.custom.project.businesslogic.IBlacklistTypeLogic;
import com.sybase365.mobiliser.custom.project.persistence.dao.factory.api.DaoFactory;
import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;

/**
 * Logic for handling the types of blacklists.
 *
 * @since 2012-02-03
 */
public class BlacklistTypeLogicImpl implements InitializingBean,
	IBlacklistTypeLogic {

    /** The <code>Log</code> instance to use. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(BlacklistTypeLogicImpl.class);

    private DaoFactory daoFactory;

    @Override
    public void afterPropertiesSet() {
	if (this.daoFactory == null) {
	    throw new IllegalStateException("daoFactory is required");
	}
    }

    @Override
    public void createBlacklistType(final BlacklistType blacklistType,
	    final long callerId) {
	LOG.trace("#createBlacklistType({},{})", blacklistType,
		Long.toString(callerId));

	this.daoFactory.getBlacklistTypeDao().save(blacklistType,
		Long.valueOf(callerId));
    }

    @Override
    public BlacklistType getBlacklistType(final int id, final long callerId) {
	LOG.trace("#getBlacklistType({},{})", Integer.toString(id),
		Long.toString(callerId));

	return this.daoFactory.getBlacklistTypeDao().getById(
		Integer.valueOf(id));
    }

    /**
     * @param daoFactory
     *            the daoFactory to set
     */
    public void setDaoFactory(final DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

}
