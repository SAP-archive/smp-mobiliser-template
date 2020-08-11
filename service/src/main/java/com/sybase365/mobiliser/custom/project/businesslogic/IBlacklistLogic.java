/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic;

import java.util.List;

import com.sybase365.mobiliser.custom.project.businesslogic.exceptions.BlacklistException;
import com.sybase365.mobiliser.custom.project.persistence.model.Blacklist;
import com.sybase365.mobiliser.money.businesslogic.util.EntityNotFoundException;

/**
 * Defines actions related to {@link Blacklist}s.
 *
 * @since 2012-01-20
 */
public interface IBlacklistLogic {

    /**
     * Checks for the given name in the blacklist.
     *
     * @param name
     *            the name to query
     * @param callerId
     *            the id of the user calling this method
     * @throws BlacklistException
     *             if matches are found for the name
     */
    void checkBlacklist(final String name, final long callerId)
	    throws BlacklistException;

    /**
     * Creates a new blacklist entry in the peristent store.
     *
     * @param blacklist
     *            the entry to create
     * @param callerId
     *            the id of the user calling this method
     * @return the id of the newly created blacklist entry
     */
    long createBlacklist(final Blacklist blacklist, final long callerId);

    /**
     * Deactivates the blacklist entry with the given id.
     *
     * @param blacklistId
     *            the id
     * @param callerId
     *            the id of the user calling this method
     * @throws EntityNotFoundException
     *             if no blacklist entry exists with this id
     */
    void deactivateBlacklist(final long blacklistId, final long callerId)
	    throws EntityNotFoundException;

    /**
     * Fetch a blacklist from the persistent store via its identifier.
     *
     * @param blacklistId
     *            the id of the entry to fetch
     * @param callerId
     *            the id of the user calling this method
     * @return the entry
     * @throws EntityNotFoundException
     *             if no entry is found with this id
     */
    Blacklist getBlacklist(final long blacklistId, final long callerId)
	    throws EntityNotFoundException;

    /**
     * Fetches the matching blacklist entries for the given name.
     *
     * @param name
     *            the name to query
     * @param callerId
     *            the id of the user calling this method
     * @return the matching blacklist entries for the given name
     */
    List<Blacklist> matchBlacklist(final String name, final long callerId);

    /**
     * @param blacklist
     * @param callerId
     *            the id of the user calling this method
     * @throws EntityNotFoundException
     */
    void updateBlacklist(final Blacklist blacklist, final long callerId)
	    throws EntityNotFoundException;

}
