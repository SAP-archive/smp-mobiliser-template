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
