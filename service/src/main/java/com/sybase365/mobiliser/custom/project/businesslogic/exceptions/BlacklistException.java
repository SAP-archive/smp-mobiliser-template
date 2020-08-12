/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic.exceptions;

import com.sybase365.mobiliser.framework.service.api.MobiliserServiceException;

/**
 * Possible exception reasons related to blacklist
 *
 * @since 2012-01-20
 */
public class BlacklistException extends MobiliserServiceException {

    /**
     */
    public static enum BlacklistReason {
	/** name was matched in blacklist. */
	NAME_MATCH,
	/** name was a probable match in blacklist. */
	NAME_PROBABLE_MATCH;
    }

    /** serial version uid. */
    private static final long serialVersionUID = -1376518613813690278L;

    private final BlacklistReason blacklistReason;

    /**
     * @param blacklistReason
     */
    public BlacklistException(final BlacklistReason blacklistReason) {
	this.blacklistReason = blacklistReason;
    }

    /**
     * @param message
     * @param blacklistReason
     */
    public BlacklistException(final String message,
	    final BlacklistReason blacklistReason) {
	super(message);
	this.blacklistReason = blacklistReason;
    }

    /**
     * @param message
     * @param cause
     * @param blacklistReason
     */
    public BlacklistException(final String message, final Throwable cause,
	    final BlacklistReason blacklistReason) {
	super(message, cause);
	this.blacklistReason = blacklistReason;
    }

    /**
     * @param cause
     * @param blacklistReason
     */
    public BlacklistException(final Throwable cause,
	    final BlacklistReason blacklistReason) {
	super(cause);
	this.blacklistReason = blacklistReason;
    }

    /**
     * @return the blacklistReason
     */
    public BlacklistReason getBlacklistReason() {
	return this.blacklistReason;
    }

    @Override
    public int getErrorCode() {
	switch (this.blacklistReason) {
	case NAME_MATCH:
	    return StatusCodes.ERROR_NAME_ON_BLACKLIST;
	case NAME_PROBABLE_MATCH:
	    return StatusCodes.ERROR_SIMILAR_NAME_ON_BLACKLIST;
	default:
	    break;
	}

	return StatusCodes.ERROR_NAME_ON_BLACKLIST;
    }

}
