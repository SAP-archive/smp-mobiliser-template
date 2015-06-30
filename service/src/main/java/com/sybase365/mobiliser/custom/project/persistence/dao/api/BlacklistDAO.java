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
import com.sybase365.mobiliser.framework.persistence.dao.api.GeneratedIdDAO;
import com.sybase365.mobiliser.money.persistence.model.customer.Customer;

/**
 * The <code>BlacklistDAO</code> provides the access methods for
 * {@link Blacklist} beans.
 *
 * @since 2012-01-18
 */
public interface BlacklistDAO extends GeneratedIdDAO<Blacklist> {

    /**
     * Searches for list entries for the given name. Optionally, can be
     * restricted to certain list types.
     *
     * @param name
     *            The customer name, no wild cards allowed
     * @param blacklistType
     *            Optional filter on blacklist type
     * @return The list of matching blacklist entries
     */
    List<Blacklist> getBlacklistsForName(final String name,
	    final Integer blacklistType);

    /**
     * Searches for customers with the passed display name.
     *
     * @param name
     *            the name to search for
     * @return a list of customers with the given display name
     */
    List<Customer> getCustomersByDisplayName(final String name);
}
