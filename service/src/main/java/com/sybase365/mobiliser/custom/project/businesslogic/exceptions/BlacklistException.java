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
