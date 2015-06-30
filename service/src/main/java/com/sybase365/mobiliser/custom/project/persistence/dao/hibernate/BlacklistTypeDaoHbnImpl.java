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
package com.sybase365.mobiliser.custom.project.persistence.dao.hibernate;

import java.util.Collections;
import java.util.List;

import com.sybase365.mobiliser.custom.project.persistence.dao.api.BlacklistTypeDAO;
import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;
import com.sybase365.mobiliser.framework.persistence.hibernate.dao.IdDAOHbnImpl;

/**
 * @since 2012-02-03
 */
public class BlacklistTypeDaoHbnImpl extends
	IdDAOHbnImpl<BlacklistType, Integer> implements BlacklistTypeDAO {

    @Override
    public List<BlacklistType> findBlacklistTypesByErrorCode(final int errorCode) {

	@SuppressWarnings("unchecked")
	final List<BlacklistType> result = getSession()
		.createQuery(
			"from BlacklistType bt where bt.errorCode = :errorCode")
		.setInteger("errorCode", errorCode).list();

	if (result == null || result.isEmpty()) {
	    return Collections.emptyList();
	}

	return result;
    }

    @Override
    public Class<BlacklistType> getEntityClass() {
	return BlacklistType.class;
    }

}
