/**
 */
package com.sybase365.mobiliser.custom.project.handlers.authentication;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import com.sybase365.mobiliser.money.businesslogic.authentication.api.IAuthenticationHandler;
import com.sybase365.mobiliser.money.businesslogic.authentication.api.exception.AuthenticationException;
import com.sybase365.mobiliser.money.businesslogic.authentication.api.exception.AuthenticationFailedPermanentlyException;
import com.sybase365.mobiliser.money.businesslogic.customer.INotificationLogic;
import com.sybase365.mobiliser.money.businesslogic.util.StatusCodes;
import com.sybase365.mobiliser.money.persistence.model.customer.Customer;
import com.sybase365.mobiliser.money.persistence.model.transaction.SubTransaction;

/**
 * @since 2012-05-15
 */
public class GtalkAuthenticationHandler implements IAuthenticationHandler,
	DisposableBean {

    /** The <code>Log</code> instance to use. */
    static final Logger LOG = LoggerFactory
	    .getLogger(GtalkAuthenticationHandler.class);

    private INotificationLogic notificationLogic;

    private XMPPConnection connection;

    private synchronized void lazyInit() {
	if (this.connection != null && this.connection.isConnected()
		&& this.connection.isAuthenticated()) {
	    return;
	}

	if (this.connection != null) {
	    this.connection.disconnect();
	}

	final ConnectionConfiguration config = new ConnectionConfiguration(
		"talk.google.com", 5222, "gmail.com");
	config.setSASLAuthenticationEnabled(true);

	SASLAuthentication.supportSASLMechanism("PLAIN", 0);

	this.connection = new XMPPConnection(config);

	try {
	    this.connection.connect();
	    this.connection.login("sybasemobiliser365@gmail.com", "sybase365");

	    LOG.debug("XMPP connection established");
	} catch (final XMPPException e) {
	    LOG.warn("Connection error: " + e.getMessage(), e);
	}

    }

    @Override
    public void destroy() {
	LOG.info("destroying XMPP connection");

	if (this.connection != null && this.connection.isConnected()) {
	    this.connection.disconnect();
	}
    }

    @Override
    public void authenticate(final SubTransaction transaction,
	    final String authToken, final boolean payer)
	    throws AuthenticationException {

	lazyInit();

	final Customer customer;
	final String otherName;
	if (payer) {
	    customer = transaction.getTransaction().getPayer();
	    otherName = transaction.getTransaction().getPayee()
		    .getDisplayName();
	} else {
	    customer = transaction.getTransaction().getPayee();
	    otherName = transaction.getTransaction().getPayer()
		    .getDisplayName();
	}

	final String email = this.notificationLogic.getCustomersEmail(customer,
		0).get(0);

	final AtomicReference<String> userResponse = new AtomicReference<String>();

	final ChatManager chatmanager = this.connection.getChatManager();

	final MessageListener messageListener = new MessageListener() {

	    @Override
	    public void processMessage(Chat chat, Message message) {
		synchronized (userResponse) {
		    LOG.info("got user response: " + message.getBody());
		    userResponse.set(message.getBody());
		    userResponse.notify();
		}
	    }

	};

	final Chat newChat = chatmanager.createChat(email, messageListener);

	try {
	    try {
		newChat.sendMessage("Please confirm payment to " + otherName
			+ " by entering yes.");
	    } catch (final XMPPException e) {
		LOG.info(
			"can not send message to user " + email + ": "
				+ e.getMessage(), e);

		throw new AuthenticationFailedPermanentlyException(
			StatusCodes.ERROR_IVR_NO_PICKUP);
	    }

	    synchronized (userResponse) {
		try {
		    userResponse.wait(10000);
		} catch (final InterruptedException e) {
		    LOG.info("Interrupted while waiting", e);
		    Thread.currentThread().interrupt();
		}
	    }

	    if (userResponse.get() == null) {
		LOG.info("did not receive a response in time.");

		throw new AuthenticationFailedPermanentlyException(
			"did not receive a response in time.",
			StatusCodes.ERROR_IVR_NO_USER_INPUT,
			new HashMap<String, String>());
	    }

	    if ("yes".equalsIgnoreCase(userResponse.get())) {

		LOG.debug("received confirmation.");

		try {
		    newChat.sendMessage("Thank You.");
		} catch (final XMPPException e1) {
		    LOG.debug("Thank you Response was not sent. Ignored", e1);
		}

		return;
	    }

	    LOG.debug("received unknown response: " + userResponse.get());

	    try {
		newChat.sendMessage("Transaction will be cancelled.");
	    } catch (final XMPPException e2) {
		LOG.debug("Response was not sent. Ignored", e2);
	    }

	    throw new AuthenticationFailedPermanentlyException(
		    StatusCodes.ERROR_IVR_HANGUP);

	} finally {
	    newChat.removeMessageListener(messageListener);
	}

    }

    @Override
    public boolean initAuthentication(final SubTransaction transaction,
	    final boolean payer) throws AuthenticationException {
	return true;
    }

    @Override
    public Integer[] getCoverage() {
	// if you return a new ID here, please remember to provide an SQL script
	// to insert the new ID into MOB_AUTH_METHODS
	return new Integer[] { Integer.valueOf(11) };
    }

    @Override
    public boolean isInlineAuthentication() {
	return false;
    }

    @Override
    public boolean isDynamicCredentials() {
	return false;
    }

    @Override
    public String getName() {
	return this.getClass().getSimpleName();
    }

    /**
     * @param notificationLogic
     *            the notificationLogic to set
     */
    public void setNotificationLogic(final INotificationLogic notificationLogic) {
	this.notificationLogic = notificationLogic;
    }

}
