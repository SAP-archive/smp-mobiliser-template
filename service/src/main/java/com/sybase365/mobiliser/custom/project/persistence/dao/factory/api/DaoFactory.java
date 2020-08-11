/**
 */
package com.sybase365.mobiliser.custom.project.persistence.dao.factory.api;

import com.sybase365.mobiliser.custom.project.persistence.dao.api.BlacklistDAO;
import com.sybase365.mobiliser.custom.project.persistence.dao.api.BlacklistTypeDAO;

/**
 * The <code>DaoFactory</code> provides a single entry point to retrieve any DAO
 * implementation for persistence beans belonging to this customisation.
 *
 * @since 2012-01-18
 */
public interface DaoFactory {

    /**
     * Returns the DAO for blacklist entries.
     *
     * @return the dao
     */
    BlacklistDAO getBlacklistDao();

    /**
     * Returns the dao for blacklist types.
     *
     * @return the dao
     */
    BlacklistTypeDAO getBlacklistTypeDao();

}
