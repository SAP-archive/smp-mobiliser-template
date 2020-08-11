/**
 */
package com.sybase365.mobiliser.custom.project.services.endpoint;

import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.custom.project.businesslogic.IBlacklistTypeLogic;
import com.sybase365.mobiliser.custom.project.converter.IBlacklistTypeConverter;
import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;
import com.sybase365.mobiliser.custom.project.services.contract.api.IBlacklistTypeEndpoint;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistTypeRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistTypeResponse;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistTypeRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistTypeResponse;
import com.sybase365.mobiliser.framework.gateway.security.api.ICallerUtils;

/**
 * @since 2012-02-03
 */
public class BlacklistTypeEndpoint implements IBlacklistTypeEndpoint,
	InitializingBean {

    /** The <code>Log</code> instance to use. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(BlacklistTypeEndpoint.class);

    private IBlacklistTypeConverter blacklistTypeConverter;
    private IBlacklistTypeLogic blacklistTypeLogic;
    private ICallerUtils callerUtils;

    @Override
    public void afterPropertiesSet() {
	if (this.callerUtils == null) {
	    throw new IllegalStateException("callerUtils is required");
	}

	if (this.blacklistTypeLogic == null) {
	    throw new IllegalStateException("blacklistTypeLogic is required");
	}

	if (this.blacklistTypeConverter == null) {
	    throw new IllegalStateException(
		    "blacklistTypeConverter is required");
	}
    }

    @Override
    public CreateBlacklistTypeResponse createBlacklistType(
	    final CreateBlacklistTypeRequest request) {
	LOG.trace("#createBlacklisType({})", request);

	final BlacklistType bt = this.blacklistTypeConverter
		.fromContract(request.getBlacklistType());

	this.blacklistTypeLogic.createBlacklistType(bt, getCallerId());

	return new CreateBlacklistTypeResponse();
    }

    @Override
    public GetBlacklistTypeResponse getBlacklistType(
	    final GetBlacklistTypeRequest request) {

	LOG.trace("#getBlacklistType({})", request);

	final BlacklistType bt = this.blacklistTypeLogic.getBlacklistType(
		request.getBlacklistTypeId(), getCallerId());

	final GetBlacklistTypeResponse response = new GetBlacklistTypeResponse();

	response.setBlacklistType(this.blacklistTypeConverter.toContract(bt));

	return response;
    }

    private long getCallerId() {
	return this.callerUtils.getCallerId();
    }

    /**
     * @param blacklistTypeConverter
     *            the blacklistTypeConverter to set
     */
    public void setBlacklistTypeConverter(
	    final IBlacklistTypeConverter blacklistTypeConverter) {
	this.blacklistTypeConverter = blacklistTypeConverter;
    }

    /**
     * @param blacklistTypeLogic
     *            the blacklistTypeLogic to set
     */
    public void setBlacklistTypeLogic(
	    final IBlacklistTypeLogic blacklistTypeLogic) {
	this.blacklistTypeLogic = blacklistTypeLogic;
    }

    /**
     * @param callerUtils
     *            the callerUtils to set
     */
    public void setCallerUtils(final ICallerUtils callerUtils) {
	this.callerUtils = callerUtils;
    }

}
