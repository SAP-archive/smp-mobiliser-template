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
