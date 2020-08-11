/**
 */
package com.sybase365.mobiliser.custom.project.services.contract.api;

import javax.annotation.security.RolesAllowed;

import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistResponse;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistResponse;

/**
 * Defines all services that are exposed related to blacklisting.
 *
 * @since 2012-01-24
 */
public interface IBlacklistEndpoint {

    /**
     * @param request
     * @return the response
     */
    @RolesAllowed(value = "WS_CREATE_BLACKLIST")
    CreateBlacklistResponse createBlacklist(final CreateBlacklistRequest request);

    /**
     * @param request
     * @return the response
     */
    @RolesAllowed(value = "WS_GET_BLACKLIST")
    GetBlacklistResponse getBlacklist(final GetBlacklistRequest request);

}
