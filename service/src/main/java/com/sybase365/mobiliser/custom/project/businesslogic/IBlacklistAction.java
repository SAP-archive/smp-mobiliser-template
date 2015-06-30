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
/**
 *
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
