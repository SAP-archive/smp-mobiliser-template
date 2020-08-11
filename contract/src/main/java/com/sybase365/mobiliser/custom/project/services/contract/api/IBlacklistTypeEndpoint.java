/**
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
