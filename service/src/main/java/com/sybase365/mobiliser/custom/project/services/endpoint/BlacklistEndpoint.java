/**
 */
package com.sybase365.mobiliser.custom.project.services.endpoint;

import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.custom.project.businesslogic.IBlacklistLogic;
import com.sybase365.mobiliser.custom.project.converter.IBlacklistConverter;
import com.sybase365.mobiliser.custom.project.services.contract.api.IBlacklistEndpoint;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.CreateBlacklistResponse;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetBlacklistResponse;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.Blacklist;
import com.sybase365.mobiliser.framework.gateway.security.api.ICallerUtils;

/**
 * @since 2012-01-24
 */
public class BlacklistEndpoint implements IBlacklistEndpoint, InitializingBean {

    /** The <code>Log</code> instance to use. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(BlacklistEndpoint.class);

    private IBlacklistConverter blacklistConverter;
    private IBlacklistLogic blacklistLogic;
    private ICallerUtils callerUtils;

    @Override
    public void afterPropertiesSet() {
	if (this.callerUtils == null) {
	    throw new IllegalStateException("callerUtils is required");
	}

	if (this.blacklistLogic == null) {
	    throw new IllegalStateException("blacklistLogic is required");
	}

	if (this.blacklistConverter == null) {
	    throw new IllegalStateException("blacklistConverter is required");
	}
    }

    @Override
    public CreateBlacklistResponse createBlacklist(
	    final CreateBlacklistRequest request) {

	LOG.debug("Calling BL for blacklist retrieval");

	final CreateBlacklistResponse response = new CreateBlacklistResponse();

	final com.sybase365.mobiliser.custom.project.persistence.model.Blacklist blacklist = this.blacklistConverter
		.fromContract(request.getBlacklist());

	final Long id = Long.valueOf(this.blacklistLogic.createBlacklist(
		blacklist, getCallerId()));

	LOG.debug("Return blacklist id #{}", id);

	response.setBlacklistId(id);

	return response;
    }

    @Override
    public GetBlacklistResponse getBlacklist(final GetBlacklistRequest request) {

	LOG.debug("Calling BL for blacklist retrieval");

	final GetBlacklistResponse response = new GetBlacklistResponse();

	final long blacklistId = request.getBlacklistId();

	final com.sybase365.mobiliser.custom.project.persistence.model.Blacklist blacklist2 = this.blacklistLogic
		.getBlacklist(blacklistId, getCallerId());

	final Blacklist blacklist = this.blacklistConverter
		.toContract(blacklist2);

	LOG.debug("Return blacklist");

	response.setBlacklist(blacklist);

	return response;
    }

    private long getCallerId() {
	return this.callerUtils.getCallerId();
    }

    /**
     * @param blacklistConverter
     *            the blacklistConverter to set
     */
    public void setBlacklistConverter(
	    final IBlacklistConverter blacklistConverter) {
	this.blacklistConverter = blacklistConverter;
    }

    /**
     * @param blacklistLogic
     *            the blacklistLogic to set
     */
    public void setBlacklistLogic(final IBlacklistLogic blacklistLogic) {
	this.blacklistLogic = blacklistLogic;
    }

    /**
     * @param callerUtils
     *            the callerUtils to set
     */
    public void setCallerUtils(final ICallerUtils callerUtils) {
	this.callerUtils = callerUtils;
    }

}
