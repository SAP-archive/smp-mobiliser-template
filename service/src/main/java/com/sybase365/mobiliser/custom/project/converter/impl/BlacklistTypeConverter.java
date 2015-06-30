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
package com.sybase365.mobiliser.custom.project.converter.impl;

import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.custom.project.converter.IBlacklistTypeConverter;
import com.sybase365.mobiliser.custom.project.persistence.dao.factory.api.DaoFactory;
import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;

/**
 * @since 2012-02-03
 */
public class BlacklistTypeConverter implements InitializingBean,
	IBlacklistTypeConverter {

    private DaoFactory daoFactory;

    @Override
    public void afterPropertiesSet() {
	if (this.daoFactory == null) {
	    throw new IllegalStateException("datFactory must be set.");
	}
    }

    @Override
    public BlacklistType fromContract(
	    final com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType contract) {

	BlacklistType model = this.daoFactory.getBlacklistTypeDao().getById(
		Integer.valueOf(contract.getId()));

	if (model == null) {
	    model = this.daoFactory.getBlacklistTypeDao().newInstance(
		    Integer.valueOf(contract.getId()));
	}

	model.setName(contract.getName());
	model.setErrorCode(contract.getErrorCode());

	return model;
    }

    /**
     * @param daoFactory
     *            the daoFactory to set
     */
    public void setDaoFactory(final DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType toContract(
	    final BlacklistType model) {

	final com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType contract = new com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType();

	contract.setId(model.getId().intValue());
	contract.setName(model.getName());
	contract.setErrorCode(model.getErrorCode());

	return contract;
    }

}
