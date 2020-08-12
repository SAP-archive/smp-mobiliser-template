/**
 */
package com.sybase365.mobiliser.custom.project.handlers.exchangerate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import com.sybase365.mobiliser.money.businesslogic.payment.api.IExchangeRateHandler;
import com.sybase365.mobiliser.money.businesslogic.payment.api.types.ExchangeRate;
import com.sybase365.mobiliser.money.businesslogic.payment.api.types.OrgUnitCurrencyCombination;
import com.sybase365.mobiliser.money.persistence.model.transaction.Transaction;

/**
 * This is a demonstration exchange rate handler that will compute the current
 * exchange rate from the currency symbols.
 *
 * @since 2012-05-15
 */
public final class CustomExchangeRateHandler implements IExchangeRateHandler {

    /**
     * <p>
     * Returns the currency combinations this handler is responsible for.
     * </p>
     *
     * @return an array of <code>OrgUnitCurrencyCombination</code>s
     */
    @Override
    public OrgUnitCurrencyCombination[] getCoverage() {
	return new OrgUnitCurrencyCombination[] { new OrgUnitCurrencyCombination(
		"5555", null, null) };
    }

    /**
     * <p>
     * Sample magic currency exchange rate determination
     * </p>
     *
     * @param monTxn
     *            the money transaction
     * @param fromCurr
     *            the source currency
     * @param toCurr
     *            the target currency
     * @return the matching exchange rate
     *
     */
    @Override
    public ExchangeRate getExchangeRate(final Transaction monTxn,
	    final Currency fromCurr, final Currency toCurr) {

	BigDecimal rate = BigDecimal.ONE;

	rate = rate.multiply(BigDecimal.valueOf(fromCurr.getCurrencyCode()
		.hashCode()));

	rate = rate.divide(BigDecimal.valueOf(toCurr.getCurrencyCode()
		.hashCode()));

	rate.setScale(4, RoundingMode.HALF_UP);

	return new ExchangeRate(fromCurr.getCurrencyCode(),
		toCurr.getCurrencyCode(), 1L, 1L, rate);
    }

    @Override
    public String getName() {
	return "CustomExchangeRateHandler";
    }

}
