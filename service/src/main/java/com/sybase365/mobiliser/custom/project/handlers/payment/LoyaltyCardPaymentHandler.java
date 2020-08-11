/**
 */
package com.sybase365.mobiliser.custom.project.handlers.payment;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.money.businesslogic.payment.api.IPaymentInstrumentTypeHandler;
import com.sybase365.mobiliser.money.businesslogic.payment.api.exception.PaymentHandlerException;
import com.sybase365.mobiliser.money.businesslogic.payment.api.types.Balance;
import com.sybase365.mobiliser.money.businesslogic.payment.api.types.PaymentInstrumentTypeCombination;
import com.sybase365.mobiliser.money.businesslogic.payment.api.types.PaymentMode;
import com.sybase365.mobiliser.money.businesslogic.payment.api.types.TransactionData;
import com.sybase365.mobiliser.money.businesslogic.util.StatusCodes;
import com.sybase365.mobiliser.money.persistence.dao.factory.api.DaoFactory;
import com.sybase365.mobiliser.money.persistence.model.pi.Card;
import com.sybase365.mobiliser.money.persistence.model.pi.PaymentInstrument;

/**
 * @since 2012-05-15
 */
public class LoyaltyCardPaymentHandler implements
	IPaymentInstrumentTypeHandler, InitializingBean {

    /** The <code>Log</code> instance to use. */
    private static final Logger LOG = LoggerFactory
	    .getLogger(LoyaltyCardPaymentHandler.class);

    private DaoFactory daoFactory;

    @Override
    public void afterPropertiesSet() {
	if (this.daoFactory == null) {
	    throw new IllegalStateException("daoFactory is missing");
	}
    }

    @Override
    public void authorise(final TransactionData transactionData,
	    final PaymentMode paymentMode) throws PaymentHandlerException {

	final Card card = this.daoFactory.getCardDao().getById(
		transactionData.getTransaction().getTransaction()
			.getPaymentInstrumentPayee().getId());

	final String cardNumber = card.getCardNumber();
	final String accountHolder = card.getCardHolder();

	final long amount = transactionData.getPayeeNetAmount();
	final String currency = transactionData.getTransaction()
		.getTransaction().getPaymentInstrumentPayee().getCurrency()
		.getCurrencyCode();

	final long refId = transactionData.getTransaction().getTransaction()
		.getId().longValue();

	// TODO: (soap) call to issuer
	LOG.info(
		"sending: {},{},{},{},{}",
		new Object[] { cardNumber, accountHolder, currency,
			Long.toString(amount), Long.toString(refId) });

	// if the amount is 5 EUR, we reject the transaction (just for the fun
	// of it)
	if (transactionData.getTransaction().getSubAmount() == 500) {
	    throw new PaymentHandlerException("authorization rejected.",
		    StatusCodes.ERROR_PAYEE_PAYMENTINSTRUMENT_NO_FUNDS, null);
	}

	transactionData.getTransaction().setTransactionReferencePayee(
		String.valueOf(System.currentTimeMillis()));
	transactionData.getTransaction().setTransactionTimestampPayee(
		new Date());
    }

    @Override
    public Balance balanceInquiry(final PaymentInstrument pi)
	    throws UnsupportedOperationException, PaymentHandlerException {
	throw new UnsupportedOperationException("not supported");
    }

    @Override
    public void preAuthorise(final TransactionData txnData,
	    final PaymentMode mode) throws UnsupportedOperationException,
	    PaymentHandlerException {
	throw new UnsupportedOperationException("not supported");
    }

    @Override
    public void cancelAuthorisation(final TransactionData txnData,
	    final PaymentMode mode) throws PaymentHandlerException {
	// empty
    }

    @Override
    public void cancelCapture(final TransactionData txnData,
	    final PaymentMode mode) throws PaymentHandlerException {
	// empty
    }

    @Override
    public void capture(final TransactionData transactionData,
	    final PaymentMode arg1) throws PaymentHandlerException {

	final String issuerRefid = transactionData.getRefTxn()
		.getTransactionReferencePayee();

	final long amount = transactionData.getPayeeNetAmount();

	LOG.info("capture message: {},{}",
		new Object[] { issuerRefid, Long.toString(amount) });

	transactionData.getTransaction().setTransactionReferencePayee(
		String.valueOf(System.currentTimeMillis()));
	transactionData.getTransaction().setTransactionTimestampPayee(
		new Date());

    }

    @Override
    public boolean supportsCaptureCancel() {
	return true;
    }

    @Override
    public PaymentInstrumentTypeCombination[] getCoverage() {
	// only register for doing credits to payment instruments of type 23
	// odn't foget to provide an SQL script that creates the new PI type 23
	// in table MOB_PI_TYPES.
	return new PaymentInstrumentTypeCombination[] { new PaymentInstrumentTypeCombination(
		null, Integer.valueOf(23)) };

    }

    @Override
    public String getName() {
	return "LoyaltyCardPaymentHandler";
    }

    /**
     * @param daoFactory
     *            the daoFactory to set
     */
    public void setDaoFactory(final DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

}
