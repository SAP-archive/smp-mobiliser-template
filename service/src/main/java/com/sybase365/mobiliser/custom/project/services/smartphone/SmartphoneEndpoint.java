/**
 */
package com.sybase365.mobiliser.custom.project.services.smartphone;

import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.custom.project.services.contract.api.IBlacklistEndpoint;
import com.sybase365.mobiliser.custom.project.services.contract.api.ISmartPhoneEndpoint;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistResponse;
import com.sybase365.mobiliser.framework.gateway.security.api.ICallerUtils;
import com.sybase365.mobiliser.money.businesslogic.util.UserAccessingNotOwnedResourceException;

/**
 */
public class SmartphoneEndpoint implements InitializingBean,
	ISmartPhoneEndpoint {

    private IBlacklistEndpoint blacklistEndpoint;
    private ICallerUtils callerUtils;

    @Override
    public void afterPropertiesSet() {
	if (this.callerUtils == null) {
	    throw new IllegalStateException("callerUtils is required");
	}

	if (this.blacklistEndpoint == null) {
	    throw new IllegalStateException("blacklistEndpoint is required");
	}
    }

    /**
     * @param customerId
     */
    protected void checkIfCustomerRequestingUsingOwnId(final long customerId) {
	if (customerId == getCallerId()) {
	    return;
	}

	throw new UserAccessingNotOwnedResourceException(getCallerId()
		+ " is not allowed to access resources of User " + customerId);
    }

    @Override
    public GetBlacklistResponse getBlacklist(final GetBlacklistRequest request) {
	return this.blacklistEndpoint.getBlacklist(request);
    }

    private long getCallerId() {
	return this.callerUtils.getCallerId();
    }

    /**
     * @param blacklistEndpoint
     *            the blacklistEndpoint to set
     */
    public void setBlacklistEndpoint(final IBlacklistEndpoint blacklistEndpoint) {
	this.blacklistEndpoint = blacklistEndpoint;
    }

    /**
     * @param callerUtils
     *            the callerUtils to set
     */
    public void setCallerUtils(final ICallerUtils callerUtils) {
	this.callerUtils = callerUtils;
    }

}
