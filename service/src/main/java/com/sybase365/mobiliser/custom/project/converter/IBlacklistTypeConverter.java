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
package com.sybase365.mobiliser.custom.project.converter;

import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;

/**
 * Converts between custom JPA beans and custom contract JAX beans.
 *
 * @since 2012-02-03
 */
public interface IBlacklistTypeConverter {

    /**
     * Converts a dto into an entity.
     *
     * @param contract
     *            the dto
     * @return the entity
     */
    BlacklistType fromContract(
	    com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType contract);

    /**
     * Converts an entity into a dto.
     *
     * @param model
     *            the entity
     * @return the dto
     */
    com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType toContract(
	    BlacklistType model);

}