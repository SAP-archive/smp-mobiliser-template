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
