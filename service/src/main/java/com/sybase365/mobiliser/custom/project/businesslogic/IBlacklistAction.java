/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionException;

import com.sybase365.mobiliser.framework.service.api.MobiliserServiceException;
import com.sybase365.mobiliser.money.businesslogic.transaction.bean.authorise.IAuthorisationRequest;
import com.sybase365.mobiliser.money.businesslogic.transaction.bean.authorise.IAuthorisationResponse;

/**
 * Common actions for processing limits. Implementations should perform
 * transaction demarcation and not rely the client code starting a transaction
 * themselves.
 *
 * @param <Req>
 * @param <Res>
 * @since 2012-01-23
 */
public interface IBlacklistAction<Req extends IAuthorisationRequest, Res extends IAuthorisationResponse> {

    /**
     * <p>
     * Perform a blacklist check for the given <code>Transaction</code>.
     * </p>
     * <p>
     * <b>Notes on transactional semantics:</b> This operation will run inside
     * its own database transaction. Any failure will trigger a rollback.
     * Exception will <b>always</b> cause the <code>SubTransaction</code> and
     * the main <code>Transaction</code> to be marked as failed in a fresh
     * database transaction.
     * </p>
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @param subTransactionId
     *            the sub-transaction's id
     * @param actorId
     *            the actorId (authenticated user)
     * @throws MobiliserServiceException
     *             if blacklist checking fails for some reason (business rules)
     * @throws DataAccessException
     *             if some error occurs communicated / writing / reading
     *             persistent storage
     * @throws TransactionException
     *             if some error occurs while handling the transactions related
     *             to persistent storage
     */
    void blacklistCheck(final Req request, final Res response,
	    final long subTransactionId, final Long actorId)
	    throws MobiliserServiceException, DataAccessException,
	    TransactionException;
}
