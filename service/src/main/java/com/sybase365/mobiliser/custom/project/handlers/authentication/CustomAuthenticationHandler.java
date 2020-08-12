/**
 */
package com.sybase365.mobiliser.custom.project.handlers.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.money.businesslogic.authentication.api.IAuthenticationHandler;
import com.sybase365.mobiliser.money.businesslogic.authentication.api.exception.AuthenticationException;
import com.sybase365.mobiliser.money.businesslogic.authentication.api.exception.AuthenticationFailedException;
import com.sybase365.mobiliser.money.businesslogic.authentication.api.exception.AuthenticationFailedPermanentlyException;
import com.sybase365.mobiliser.money.businesslogic.util.StatusCodes;
import com.sybase365.mobiliser.money.persistence.dao.factory.api.DaoFactory;
import com.sybase365.mobiliser.money.persistence.model.transaction.SubTransaction;

/**
 * This is a demonstration authentication handler that allows only amounts
 * ending with '0' to pass through directly. All other transactions must be
 * authenticated by the payer by passing the transaction amount back in to
 * continue the transaction through the authentication-continue service.
 *
 * @since 2012-05-15
 */
public final class CustomAuthenticationHandler implements
	IAuthenticationHandler, InitializingBean {

    private static final Logger LOG = LoggerFactory
	    .getLogger(CustomAuthenticationHandler.class);

    private DaoFactory daoFactory;

    /**
     * @see com.sybase365.mobiliser.money.businesslogic.authentication.api.IAuthenticationHandler#getCoverage()
     *
     * @return authentication method 100
     */
    @Override
    public Integer[] getCoverage() {
	return new Integer[] { Integer.valueOf(100) };
    }

    /**
     * Checks it the authentication token equals the transaction amount
     *
     * @throws AuthenticationException
     *
     *             {@link com.sybase365.mobiliser.money.businesslogic.authentication.api.IAuthenticationHandler#authenticate(SubTransaction, String, boolean)}
     */
    @Override
    public void authenticate(final SubTransaction transaction,
	    final String authToken, final boolean payer)
	    throws AuthenticationException {

	LOG.debug(
		"#authenticate(subtransaction = {}, authToken = {}, isPayer = {})",
		new Object[] { transaction.toString(), "xxxx",
			Boolean.toString(payer) });

	long amount = transaction.getSubAmount();

	LOG.trace("using amount #{}", Long.valueOf(amount));

	if (authToken == null || authToken.equals("")) {
	    LOG.debug("The given token was empty/null. Authentication is retrieable");

	    throw new AuthenticationFailedException(
		    StatusCodes.ERROR_TXN_AUTH_WRONG);
	}

	try {
	    long token = Long.parseLong(authToken);

	    if (token == amount) {
		LOG.debug("Token matched amount");
		return;
	    } else if (token % 10 == 0) {
		LOG.debug("Token ended with 0 - allowing retry");
		throw new AuthenticationFailedException(
			StatusCodes.ERROR_TXN_AUTH_WRONG);

	    } else {
		LOG.debug("Token wrong - failing transaction");

		throw new AuthenticationFailedPermanentlyException(
			StatusCodes.ERROR_TXN_AUTH_WRONG_FINAL);

	    }
	} catch (final NumberFormatException ex) {
	    LOG.debug("Could not parse token; must be a number!");

	    throw new AuthenticationFailedPermanentlyException(
		    StatusCodes.ERROR_TXN_AUTH_WRONG_FINAL);

	}
    }

    @Override
    public boolean initAuthentication(final SubTransaction transaction,
	    final boolean payer) throws AuthenticationException {

	if (transaction.getSubAmount() % 10 == 0) {
	    LOG.debug("Amount ends with '0' - no extra authentication required");

	    return true;
	}

	LOG.debug("Amount does not end with '0' - authentication required");

	return false;

    }

    @Override
    public void afterPropertiesSet() {
	if (this.daoFactory == null) {
	    throw new IllegalStateException("daoFactory is requited");
	}
    }

    @Override
    public boolean isInlineAuthentication() {
	return true;
    }

    @Override
    public boolean isDynamicCredentials() {
	return true;
    }

    @Override
    public String getName() {
	return "amountCredential";
    }

    /**
     * @param daoFactory
     *            the daoFactory to set
     */
    public void setDaoFactory(final DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

}
