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
