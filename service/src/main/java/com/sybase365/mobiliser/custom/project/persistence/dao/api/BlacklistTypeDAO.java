/**
 */
package com.sybase365.mobiliser.custom.project.persistence.dao.api;

import java.util.List;

import com.sybase365.mobiliser.custom.project.persistence.model.Blacklist;
import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;
import com.sybase365.mobiliser.framework.persistence.dao.api.IdDAO;

/**
 * The <code>BlacklistDAO</code> provides the access methods for
 * {@link Blacklist} beans.
 *
 * @since 2012-02-03
 */
public interface BlacklistTypeDAO extends IdDAO<BlacklistType, Integer> {

    /**
     * Fetches the blacklist types based on an error code.
     *
     * @param errorCode
     *            the error code for filtering
     * @return the list of matches
     */
    List<BlacklistType> findBlacklistTypesByErrorCode(final int errorCode);
}
