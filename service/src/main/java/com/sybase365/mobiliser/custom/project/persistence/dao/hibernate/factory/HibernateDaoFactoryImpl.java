/**
 */
package com.sybase365.mobiliser.custom.project.persistence.dao.hibernate.factory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.sybase365.mobiliser.custom.project.persistence.dao.api.BlacklistDAO;
import com.sybase365.mobiliser.custom.project.persistence.dao.api.BlacklistTypeDAO;
import com.sybase365.mobiliser.custom.project.persistence.dao.factory.api.DaoFactory;

/**
 * Factory providing the Hibernate-backed DAO implementations
 *
 * @since 2012-01-18
 */
public class HibernateDaoFactoryImpl implements DaoFactory, InitializingBean {

    private BlacklistDAO blacklistDao;
    private BlacklistTypeDAO blacklistTypeDao;

    @Override
    public void afterPropertiesSet() {
	Assert.notNull(this.blacklistDao, "blacklistDao is required");
	Assert.notNull(this.blacklistTypeDao, "blacklistTypeDao is required");
    }

    @Override
    public BlacklistDAO getBlacklistDao() {
	return this.blacklistDao;
    }

    @Override
    public BlacklistTypeDAO getBlacklistTypeDao() {
	return this.blacklistTypeDao;
    }

    /**
     * @param blacklistDao
     *            the blacklistDao to set
     */
    public void setBlacklistDao(final BlacklistDAO blacklistDao) {
	this.blacklistDao = blacklistDao;
    }

    /**
     * @param blacklistTypeDao
     *            the blacklistTypeDao to set
     */
    public void setBlacklistTypeDao(final BlacklistTypeDAO blacklistTypeDao) {
	this.blacklistTypeDao = blacklistTypeDao;
    }

}
