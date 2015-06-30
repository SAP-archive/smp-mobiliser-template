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
