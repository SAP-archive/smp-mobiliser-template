/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic.impl;

import java.util.concurrent.atomic.AtomicReference;

import com.sybase365.mobiliser.framework.gateway.security.api.CallerInformation;
import com.sybase365.mobiliser.money.businesslogic.transaction.bean.authorise.IAuthorisationRequest;
import com.sybase365.mobiliser.money.businesslogic.transaction.flow.ITransactionFlowOverlay;
import com.sybase365.mobiliser.money.businesslogic.util.beans.IKeyValue;
import com.sybase365.mobiliser.money.businesslogic.util.beans.IMoneyRequest;
import com.sybase365.mobiliser.money.businesslogic.util.beans.ITransactionResponse;

/**
 * @since 2012-03-19
 */
public class CustomTransactionFlowOverlay implements
	ITransactionFlowOverlay<IMoneyRequest, ITransactionResponse> {

    /** The <code>Log</code> instance to use. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(CustomTransactionFlowOverlay.class);

    @Override
    public void afterAuthorisationCancelSuccess(final IMoneyRequest request,
	    final ITransactionResponse response,
	    final AtomicReference<CallerInformation> callerRef,
	    final AtomicReference<Long> authCancIdRef,
	    final AtomicReference<Long> authIdRef) {

	LOG.info("#afterAuthorisationCancelSuccess");

	if (request instanceof IAuthorisationRequest) {
	    LOG.info("useCase: {}",
		    ((IAuthorisationRequest) request).getUsecase());
	} else {
	    LOG.info("unknown request type: {}", request.getClass().getName());
	}

	response.getUnstructuredData().add(new IKeyValue() {

	    /** serial version uid. */
	    private static final long serialVersionUID = 2487548847332741754L;

	    @Override
	    public String getKey() {
		return "afterAuthorisationCancelSuccess";
	    }

	    @Override
	    public String getValue() {
		return "I was here!";
	    }
	});
    }

    @Override
    public void afterAuthorisationSuccess(final IMoneyRequest request,
	    final ITransactionResponse response,
	    final AtomicReference<CallerInformation> callerRef,
	    final AtomicReference<Long> authIdRef) {

	LOG.info("#afterAuthorisationSuccess");

	if (request instanceof IAuthorisationRequest) {
	    LOG.info("useCase: {}",
		    ((IAuthorisationRequest) request).getUsecase());
	} else {
	    LOG.info("unknown request type: {}", request.getClass().getName());
	}

	response.getUnstructuredData().add(new IKeyValue() {

	    /** serial version uid. */
	    private static final long serialVersionUID = -1719304575234016746L;

	    @Override
	    public String getKey() {
		return "afterAuthorisationSuccess";
	    }

	    @Override
	    public String getValue() {
		return "I was here!";
	    }
	});
    }

    @Override
    public void afterCaptureCancelSuccess(final IMoneyRequest request,
	    final ITransactionResponse response,
	    final AtomicReference<CallerInformation> callerRef,
	    final AtomicReference<Long> captCancIdRef,
	    final AtomicReference<Long> captIdRef) {

	LOG.info("#afterCaptureCancelSuccess");

	if (request instanceof IAuthorisationRequest) {
	    LOG.info("useCase: {}",
		    ((IAuthorisationRequest) request).getUsecase());
	} else {
	    LOG.info("unknown request type: {}", request.getClass().getName());
	}

	response.getUnstructuredData().add(new IKeyValue() {

	    /** serial version uid. */
	    private static final long serialVersionUID = 1606603482285612174L;

	    @Override
	    public String getKey() {
		return "afterCaptureCancelSuccess";
	    }

	    @Override
	    public String getValue() {
		return "I was here!";
	    }
	});
    }

    @Override
    public void afterCaptureSuccess(final IMoneyRequest request,
	    final ITransactionResponse response,
	    final AtomicReference<CallerInformation> callerRef,
	    final AtomicReference<Long> captIdRef,
	    final AtomicReference<Long> authIdRef) {

	LOG.info("#afterCaptureSuccess");

	if (request instanceof IAuthorisationRequest) {
	    LOG.info("useCase: {}",
		    ((IAuthorisationRequest) request).getUsecase());
	} else {
	    LOG.info("unknown request type: {}", request.getClass().getName());
	}

	response.getUnstructuredData().add(new IKeyValue() {

	    /** serial version uid. */
	    private static final long serialVersionUID = -5105660570387738540L;

	    @Override
	    public String getKey() {
		return "afterCaptureSuccess";
	    }

	    @Override
	    public String getValue() {
		return "I was here!";
	    }
	});
    }

    @Override
    public void afterInitTransaction(final IMoneyRequest request,
	    final ITransactionResponse response,
	    final AtomicReference<CallerInformation> callerRef,
	    final AtomicReference<Long> authIdRef, final boolean persist) {

	LOG.info("#afterInitTransaction");

	if (request instanceof IAuthorisationRequest) {
	    LOG.info("useCase: {}",
		    ((IAuthorisationRequest) request).getUsecase());
	} else {
	    LOG.info("unknown request type: {}", request.getClass().getName());
	}

	response.getUnstructuredData().add(new IKeyValue() {

	    /** serial version uid. */
	    private static final long serialVersionUID = -5813557918929137977L;

	    @Override
	    public String getKey() {
		return "afterInitTransaction";
	    }

	    @Override
	    public String getValue() {
		return "I was here!";
	    }
	});
    }

    @Override
    public void beforeCreateTransaction(final IMoneyRequest request,
	    final ITransactionResponse response,
	    final AtomicReference<CallerInformation> callerRef) {

	LOG.info("#beforeCreateTransaction");

	if (request instanceof IAuthorisationRequest) {
	    LOG.info("useCase: {}",
		    ((IAuthorisationRequest) request).getUsecase());
	} else {
	    LOG.info("unknown request type: {}", request.getClass().getName());
	}

	response.getUnstructuredData().add(new IKeyValue() {

	    /** serial version uid. */
	    private static final long serialVersionUID = 8395279499150312206L;

	    @Override
	    public String getKey() {
		return "beforeCreateTransaction";
	    }

	    @Override
	    public String getValue() {
		return "I was here!";
	    }
	});
    }

    @Override
    public void cleanupInitTransaction(final IMoneyRequest request,
	    final ITransactionResponse response,
	    final AtomicReference<CallerInformation> callerRef,
	    final AtomicReference<Long> authIdRef) {

	LOG.info("#cleanupInitTransaction");

	if (request instanceof IAuthorisationRequest) {
	    LOG.info("useCase: {}",
		    ((IAuthorisationRequest) request).getUsecase());
	} else {
	    LOG.info("unknown request type: {}", request.getClass().getName());
	}

	response.getUnstructuredData().add(new IKeyValue() {

	    /** serial version uid. */
	    private static final long serialVersionUID = -2943458476616687546L;

	    @Override
	    public String getKey() {
		return "cleanupInitTransaction";
	    }

	    @Override
	    public String getValue() {
		return "I was here!";
	    }
	});
    }

    @Override
    public String getName() {
	return "Custom Txn Flow";
    }

    @Override
    public int getRanking() {
	return 0;
    }
}
