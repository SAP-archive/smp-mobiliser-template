/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic.exceptions;

import com.sybase365.mobiliser.framework.service.api.MobiliserServiceException;

/**
 * Possible exception reasons related to blacklist
 *
 * @since 2012-02-01
 */
public class BlacklistExistsException extends MobiliserServiceException {

    /** serial version uid. */
    private static final long serialVersionUID = -1807979017667858653L;

    /**
     * @param message
     */
    public BlacklistExistsException(final String message) {
	super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public BlacklistExistsException(final String message, final Throwable cause) {
	super(message, cause);
    }

    @Override
    public int getErrorCode() {
	return StatusCodes.ERROR_BLACKLIST_NOT_UNIQUE;
    }

}
