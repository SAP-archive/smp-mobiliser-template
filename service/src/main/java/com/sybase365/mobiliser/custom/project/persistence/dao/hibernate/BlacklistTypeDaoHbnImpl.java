/**
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
