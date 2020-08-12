/**
 */
package com.sybase365.mobiliser.custom.project.converter.impl;

import com.sybase365.mobiliser.custom.project.converter.IBlacklistConverter;
import com.sybase365.mobiliser.custom.project.persistence.dao.factory.api.DaoFactory;
import com.sybase365.mobiliser.custom.project.persistence.model.Blacklist;
import com.sybase365.mobiliser.money.businesslogic.util.IllegalDataException;

/**
 * Converts between custom JPA beans and custom contract JAX beans.
 *
 * @since 2012-01-24
 */
public class BlacklistConverter implements IBlacklistConverter {

    private DaoFactory daoFactory;

    @Override
    public Blacklist fromContract(
	    final com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.Blacklist blacklist)
	    throws IllegalDataException {

	final Blacklist dbBlacklist;
	if (blacklist.getId() != null) {
	    dbBlacklist = this.daoFactory.getBlacklistDao().getById(
		    blacklist.getId());
	} else {
	    dbBlacklist = this.daoFactory.getBlacklistDao().newInstance();
	}

	// take care if the customer could not be loaded from the database
	if (dbBlacklist == null) {
	    return null;
	}

	dbBlacklist.setBlacklistType(this.daoFactory.getBlacklistTypeDao()
		.getById(Integer.valueOf(blacklist.getBlacklistType())));

	dbBlacklist.setName(blacklist.getName());

	return dbBlacklist;

    }

    /**
     * @param daoFactory
     *            the daoFactory to set
     */
    public void setDaoFactory(final DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.Blacklist toContract(
	    final Blacklist dbBlacklist) {

	if (dbBlacklist == null) {
	    return null;
	}

	final com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.Blacklist contract = new com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.Blacklist();

	contract.setBlacklistType(dbBlacklist.getBlacklistType().getId()
		.intValue());
	contract.setName(dbBlacklist.getName());

	return contract;
    }

}
