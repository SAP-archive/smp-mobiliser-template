/**
 */
package com.sybase365.mobiliser.custom.project.handlers.billpayment;

import com.sybase365.mobiliser.money.businesslogic.billpayment.api.BillPaymentHandler;
import com.sybase365.mobiliser.money.businesslogic.billpayment.api.InvoiceStatus;
import com.sybase365.mobiliser.money.businesslogic.billpayment.api.exception.BillPaymentHandlerException;
import com.sybase365.mobiliser.money.persistence.model.customer.Invoice;
import com.sybase365.mobiliser.money.persistence.model.customer.InvoiceConfiguration;
import com.sybase365.mobiliser.money.persistence.model.system.InvoiceType;

/**
 * This bill payment handler does nothing online and just let's the invoice
 * payments pass. The difference to the
 * <code>com.sybase365.mobiliser.money.businesslogic.billpayment.handlers.offline.OfflineBillpaymentHandler</code>
 * is that invoices of no-op type are not expected to be settled through an
 * offline file export process
 *
 * @since 2012-05-15
 */
public class DummyBillpaymentHandler implements BillPaymentHandler {

    /**
     * Offline bill payment handler covers type 3
     *
     * @see com.sybase365.mobiliser.money.businesslogic.billpayment.api.
     *      BillPaymentHandler#getInvoiceTypeHandlerTypeCoverage()
     *
     * @return [3]
     */
    @Override
    public int[] getInvoiceTypeHandlerTypeCoverage() {
	// Please don't forgot to provide an SQL script that creates the new
	// INSERT for table MOB_INVOICE_TYPE_HANDLER_TYPES
	return new int[] { 9 };
    }

    /**
     * Not doing anything
     *
     * TODO should we read from a file in this case
     *
     * @see com.sybase365.mobiliser.money.businesslogic.billpayment.api.
     *      BillPaymentHandler
     *      #updateAndCreateInvoices(com.sybase365.mobiliser.money.persistence
     *      .model.system.InvoiceType)
     */
    @Override
    public void updateAndCreateInvoices(final InvoiceType invoiceType) {
	// no-op
    }

    /**
     * Not doing anything
     *
     * TODO should we read from a file in this case
     *
     * @see com.sybase365.mobiliser.money.businesslogic.billpayment.api.
     *      BillPaymentHandler
     *      #updateAndCreateInvoicesForInvoiceConfiguration(com.sybase365
     *      .mobiliser.money.persistence.model.customer.InvoiceConfiguration)
     */
    @Override
    public void updateAndCreateInvoicesForInvoiceConfiguration(
	    final InvoiceConfiguration invoiceConfiguration) {
	// no-op
    }

    /**
     * Not doint anything
     *
     * @see com.sybase365.mobiliser.money.businesslogic.billpayment.api.
     *      BillPaymentHandler
     *      #checkInvoice(com.sybase365.mobiliser.money.persistence.
     *      model.customer.Invoice)
     */
    @Override
    public void checkInvoice(final Invoice invoice)
	    throws UnsupportedOperationException, BillPaymentHandlerException {
	// no-op
    }

    /**
     * Not doing anything
     *
     * @see com.sybase365.mobiliser.money.businesslogic.billpayment.api.
     *      BillPaymentHandler
     *      #payInvoice(com.sybase365.mobiliser.money.persistence.model
     *      .customer.Invoice, long)
     *
     * @return <code>null</code>
     */
    @Override
    public String payInvoice(final Invoice invoice, final long txnId)
	    throws BillPaymentHandlerException {
	return null;
    }

    /**
     * Does not do anything
     *
     * @see com.sybase365.mobiliser.money.businesslogic.billpayment.api.
     *      BillPaymentHandler
     *      #invoiceStatusChangeAdvice(com.sybase365.mobiliser.money
     *      .persistence.model.customer.Invoice,
     *      com.sybase365.mobiliser.money.businesslogic
     *      .billpayment.api.InvoiceStatus)
     */
    @Override
    public void invoiceStatusChangeAdvice(final Invoice invoice,
	    final InvoiceStatus invoiceStatus) {
	// no-op
    }

    /**
     * Will always return true - shouldn't be called in any case.
     *
     * @return <code>true</code>
     */
    @Override
    public boolean resendInvoiceStatusChange(final Invoice invoice,
	    final int counter) {
	return true;
    }

    @Override
    public String getName() {
	return "DummyBillpaymentHandler";
    }

}
