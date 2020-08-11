/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic;

import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;

/**
 * Defines actions related to {@link BlacklistType}s.
 *
 * @since 2012-02-03
 */
public interface IBlacklistTypeLogic {

    /**
     * Creates a new blacklist type in the persistent store.
     *
     * @param balcklistType
     *            the type to craete
     * @param callerId
     *            the id of the user calling this method
     */
    void createBlacklistType(final BlacklistType balcklistType,
	    final long callerId);

    /**
     * Fetches a blacklist type by id from the persistent store.
     *
     * @param id
     *            the id
     * @param callerId
     *            the id of the user calling this method
     * @return the blacklist type or null if no entry is found
     */
    BlacklistType getBlacklistType(final int id, final long callerId);

}
