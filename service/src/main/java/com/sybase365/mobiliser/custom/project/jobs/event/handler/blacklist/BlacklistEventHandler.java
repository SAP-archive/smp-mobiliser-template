/**
 */
package com.sybase365.mobiliser.custom.project.jobs.event.handler.blacklist;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sybase365.mobiliser.custom.project.jobs.event.model.BlacklistEvent;
import com.sybase365.mobiliser.framework.event.model.Event;
import com.sybase365.mobiliser.money.businesslogic.customer.INotificationLogic;
import com.sybase365.mobiliser.money.jobs.event.handler.util.MoneyEventHandler;
import com.sybase365.mobiliser.money.jobs.event.handler.util.MoneyEventHandlerConfiguration;
import com.sybase365.mobiliser.money.persistence.dao.factory.api.DaoFactory;
import com.sybase365.mobiliser.money.persistence.model.customer.Customer;
import com.sybase365.mobiliser.money.persistence.model.system.CustomerType;
import com.sybase365.mobiliser.money.persistence.model.system.OrgUnit;
import com.sybase365.mobiliser.money.services.util.InternalServiceCallConfiguration;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.SendTemplateRequest;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.SendTemplateResponse;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.beans.Locale;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.beans.Map;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.beans.Map.Entry;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.beans.MessageDetails;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.beans.Receiver;
import com.sybase365.mobiliser.util.contract.v5_0.messaging.beans.TemplateMessage;
import com.sybase365.mobiliser.util.messaging.service.api.IMessagingService;

/**
 * @since 2012-01-23
 */
public class BlacklistEventHandler extends
	MoneyEventHandler<MoneyEventHandlerConfiguration> {

    private static final Logger LOG = LoggerFactory
	    .getLogger(BlacklistEventHandler.class);

    private DaoFactory daoFactory;

    private IMessagingService messagingService;
    private INotificationLogic notificationLogic;

    /** The SMS message type */
    private static final String MSG_TYPE_SMS = "sms";

    /** The EMAIL message type */
    private static final String MSG_TYPE_EMAIL = "email";

    @Override
    public void afterPropertiesSet() {
	if (this.messagingService == null) {
	    throw new IllegalStateException("messagingService is required");
	}

	if (this.notificationLogic == null) {
	    throw new IllegalStateException("notificationLogic is required");
	}

	if (this.daoFactory == null) {
	    throw new IllegalStateException("daoFactory is required");
	}
    }

    @Override
    public String getHandlerName() {
	return "BlacklistEventHandler";
    }

    @Override
    public String getEventName() {
	return BlacklistEvent.EVENT_TYPE;
    }

    @Transactional
    @Override
    public boolean process(final Event e) {
	final BlacklistEvent blacklistEvent = new BlacklistEvent(e.getData());

	if (LOG.isDebugEnabled()) {
	    LOG.debug(
		    "Processing BlacklistEvent [id = #{}, "
			    + "blacklistId = #{}, blacklistType = {}, "
			    + "addedToBlacklist = {}, customerId = #{}] ",
		    new Object[] {
			    Long.toString(e.getId()),
			    Long.toString(blacklistEvent.getBlacklistId()),
			    Integer.toString(blacklistEvent.getBlacklistType()),
			    Boolean.toString(blacklistEvent
				    .isAddedToBlacklist()),
			    Long.toString(blacklistEvent.getCustomerId()) });
	}

	if (getConfiguration().getUsername() == null) {
	    LOG.warn("!!!! MISSING PREFERENCE CONFIGURATION FOR INTERNAL USERNAME !!!");

	    LOG.info("Check preference values: Class {} username {}",
		    new Object[] { this.getClass().getCanonicalName(),
			    InternalServiceCallConfiguration.PREFS_USERNAME });

	    LOG.warn("!!! Skipp processing of BlacklistEvent #{} !!!",
		    Long.toString(e.getId()));

	    return false;
	}

	final boolean processed = sendNotification(blacklistEvent);

	if (LOG.isDebugEnabled() && processed) {
	    LOG.debug("processed event #{}", Long.toString(e.getId()));
	}

	return processed;
    }

    /**
     * Retrieves the Transaction Template Mapping based on the given
     * transactionId, status and error code. If there is an entry sends the
     * configured transaction notification
     *
     * @param transactionId
     * @param status
     * @param errorCode
     */
    private boolean sendNotification(final BlacklistEvent event) {

	try {
	    final Customer customer = this.daoFactory.getCustomerDao().getById(
		    Long.valueOf(event.getCustomerId()));

	    final BlacklistEventHandlerConfiguration config = (BlacklistEventHandlerConfiguration) getConfiguration();

	    if (customer != null) {
		Map attr = new Map();

		Entry mapEntry = new Entry();

		mapEntry.setKey("blacklist.type");
		mapEntry.setValue(Integer.toString(event.getBlacklistType()));
		attr.getEntry().add(mapEntry);

		sendNotification(
			customer,
			event.isAddedToBlacklist() ? config
				.getBlacklistAddedTemplate() : config
				.getBlacklistRemovedTemplate(), attr);
	    }

	    if (LOG.isDebugEnabled()) {
		LOG.debug("processed event notification for blacklist #{}",
			Long.toString(event.getBlacklistId()));
	    }

	    return true;
	} catch (final Exception e) {
	    LOG.error(
		    "error in processing event " + event.getId() + ": "
			    + e.getMessage(), e);

	    return false; // Status: RETRY
	}
    }

    private Boolean sendNotification(final Customer customer,
	    final String template, Map attr) {

	// Check if customer is a merchant or consumer
	final CustomerType ctype = customer.getCustomerType();

	if (ctype == null) {
	    if (LOG.isInfoEnabled()) {
		LOG.info("Customer #{} is neither a consumer nor a merchant."
			+ " No customer type available! "
			+ "Not sending out any notification.", customer.getId());
	    }

	    return Boolean.FALSE;
	}

	if (ctype.getId().intValue() != 2 && ctype.getId().intValue() != 3) {

	    if (LOG.isInfoEnabled()) {
		LOG.info("Customer #{} is neither a consumer nor a merchant."
			+ " Customertype is #{}! "
			+ "Not sending out any notification.", new Object[] {
			customer.getId(), ctype.getId().toString() });
	    }

	    return Boolean.FALSE;
	}

	final List<String> emails = this.notificationLogic
		.getCustomersNotificationEmail(customer, 0);

	if (!CollectionUtils.isEmpty(emails)) {

	    final Locale locale = getLocale(customer);

	    final Receiver receiver = new Receiver();
	    receiver.setValue(emails.get(0));

	    final MessageDetails details = new MessageDetails();
	    details.setLocale(locale);
	    details.setTemplate(template);

	    // use default channel
	    final TemplateMessage msg = new TemplateMessage();
	    msg.setType(MSG_TYPE_EMAIL);
	    msg.setDetails(details);
	    msg.setParameters(attr);
	    msg.getReceiver().add(receiver);

	    final SendTemplateRequest request = new SendTemplateRequest();
	    request.setMessage(msg);

	    final SendTemplateResponse response = this.messagingService
		    .sendTemplate(request);

	    if (response == null || response.getStatus() == null) {
		LOG.warn("Problem occurred when trying to send notification of transaction  "
			+ (response == null ? "no response returned"
				: "no status set in response"));

		return null;
	    }

	    if (response.getStatus().getCode() != 0) {
		LOG.warn(
			"Problem occurred when trying to send notification of transaction. Messaging gateway returned status code {}",
			Integer.toString(response.getStatus().getCode()));
	    }

	}

	final List<String> msisdns = this.notificationLogic.getCustomersMsisdn(
		customer, 0);

	if (!CollectionUtils.isEmpty(msisdns)) {

	    final Locale locale = getLocale(customer);

	    final Receiver receiver = new Receiver();
	    receiver.setValue(msisdns.get(0));

	    final MessageDetails details = new MessageDetails();
	    details.setLocale(locale);
	    details.setTemplate(template);

	    // use default channel
	    final TemplateMessage msg = new TemplateMessage();
	    msg.setDetails(details);
	    msg.setParameters(attr);
	    msg.getReceiver().add(receiver);
	    msg.setType(MSG_TYPE_SMS);

	    final SendTemplateRequest request = new SendTemplateRequest();
	    request.setMessage(msg);

	    final SendTemplateResponse response = this.messagingService
		    .sendTemplate(request);

	    if (response == null || response.getStatus() == null) {
		LOG.warn("Problem occurred when trying to send notification of transaction  "
			+ (response == null ? "no response returned"
				: "no status set in response"));
		return null;
	    }

	    if (response.getStatus().getCode() != 0) {
		LOG.warn(
			"Problem occurred when trying to send notification of transaction. Messaging gateway returned status code {}",
			Integer.toString(response.getStatus().getCode()));
	    }
	}

	return Boolean.TRUE;

    }

    /**
     * Gets the locale used to send notifications of the corresponding
     * BalanceAlert and owner of the payment instrument respectively.
     *
     * @param ba
     *            a balance alert that fits to the current general conditions
     * @param pi
     *            payment instrument whose (orgUnit's) locale is used as default
     *            locale
     * @return the locale of the balance alert extended by the orgUnit of the
     *         payment instrument's customer, if the locale of the balance alert
     *         is set. Otherwise, null is returned, if the payment instrument
     *         has no owner. If the owner of the payment instrument is set, the
     *         locale of the owner respectively the owner's orgunit's locale is
     *         returned.
     *
     */
    private Locale getLocale(final Customer ct) {
	final OrgUnit orgUnit = ct.getOrgUnit();

	java.util.Locale locale = ct.getLocale();

	if (locale != null && (StringUtils.hasText(locale.getLanguage()))) {

	    locale = new java.util.Locale(locale.getLanguage(),
		    locale.getCountry(), orgUnit != null ? orgUnit.getId() : "");

	} else {
	    locale = ct.getLocale() != null ? ct.getLocale()
		    : orgUnit == null ? null : orgUnit.getLocale();
	}

	if (locale == null) {
	    return null;
	}

	final Locale result = new Locale();
	result.setCountry(locale.getCountry());
	result.setLanguage(locale.getLanguage());
	result.setVariant(locale.getVariant());

	return result;
    }

    /**
     * @param daoFactory
     *            the daoFactory to set
     */
    public void setDaoFactory(final DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    /**
     * @param messagingService
     *            the messagingService to set
     */
    public void setMessagingService(final IMessagingService messagingService) {
	this.messagingService = messagingService;
    }

    /**
     * @param notificationLogic
     *            the notificationLogic to set
     */
    public void setNotificationLogic(final INotificationLogic notificationLogic) {
	this.notificationLogic = notificationLogic;
    }

}
