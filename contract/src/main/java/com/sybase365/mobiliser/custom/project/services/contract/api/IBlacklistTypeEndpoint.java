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
package com.sybase365.mobiliser.custom.project.services.contract.api;

import javax.annotation.security.RolesAllowed;

import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistTypeRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistTypeResponse;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistTypeRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistTypeResponse;

/**
 * Defines all services that are exposed related to blacklist types.
 *
 * @since 2012-01-24
 */
public interface IBlacklistTypeEndpoint {

    /**
     * @param request
     * @return the response
     */
    @RolesAllowed(value = "WS_CREATE_BLACKLIST_TYPE")
    CreateBlacklistTypeResponse createBlacklistType(
	    final CreateBlacklistTypeRequest request);

    /**
     *
     * @param request
     * @return the response
     */
    @RolesAllowed(value = "WS_GET_BLACKLIST_TYPE")
    GetBlacklistTypeResponse getBlacklistType(
	    final GetBlacklistTypeRequest request);
}
