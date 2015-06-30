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
