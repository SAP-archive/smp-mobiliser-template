/**
 */
package com.sybase365.mobiliser.custom.project.services.contract.api;

import org.springframework.security.access.prepost.PreAuthorize;

import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistResponse;

/**
 * Defines all services that are exposed related to smartphone.
 *
 * @since 2012-06-15
 */
@PreAuthorize(value = "isRememberMe() and hasRole('WS_MOBILE_ACCESS')")
public interface ISmartPhoneEndpoint {

    /**
     *
     * @param request
     * @return the response
     */
    GetBlacklistResponse getBlacklist(GetBlacklistRequest request);

}
