/**
 */
package com.sybase365.mobiliser.custom.project.channels;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.support.WebContentGenerator;
import org.springframework.web.util.NestedServletException;

import com.sybase365.mobiliser.util.messaging.api.Message;
import com.sybase365.mobiliser.util.messaging.api.sms.SmsMessage;
import com.sybase365.mobiliser.util.messaging.api.ussd.UssdTextMessage;
import com.sybase365.mobiliser.util.messaging.channelmanager.api.HttpChannel;
import com.sybase365.mobiliser.util.messaging.channelmanager.api.callbacks.SynchronousChannelReceiveCallback;
import com.sybase365.mobiliser.util.messaging.template.api.IMessagingEngine;

/**
 * A channel which forwards messages and then waits for the answer. Useful for
 * testing but not much else.
 *
 * @since 2012-01-26
 */
public final class HttpChannelEnd extends WebContentGenerator implements
	HttpChannel, InitializingBean {

    private static final Logger LOG = LoggerFactory
	    .getLogger(HttpChannelEnd.class);

    private String channelId;

    @SuppressWarnings("rawtypes")
    private final HttpMessageConverter converter = new FormHttpMessageConverter();

    private String incomingChannelId;

    private final MediaType mediaType = MediaType
	    .parseMediaType("application/x-www-form-urlencoded");

    private IMessagingEngine messagingEngine;

    private SynchronousChannelReceiveCallback receiveCallback;

    private String urlSupplement;

    /**
     */
    public HttpChannelEnd() {
	super(METHOD_POST);
    }

    @Override
    public void afterPropertiesSet() {
	if (StringUtils.isBlank(this.channelId)) {
	    throw new IllegalStateException("channelId is required");
	}

	if (StringUtils.isBlank(this.incomingChannelId)) {
	    throw new IllegalStateException("incomingChannelId is required");
	}

	if (StringUtils.isBlank(this.urlSupplement)) {
	    throw new IllegalStateException("urlSupplement is required");
	}

	if (this.receiveCallback == null) {
	    throw new IllegalStateException("receiveCallback is required");
	}

	if (this.messagingEngine == null) {
	    throw new IllegalStateException("messagingEngine is required");
	}
    }

    @Override
    public String getChannelId() {
	return this.channelId;
    }

    @Override
    public String getUrlSupplement() {
	return this.urlSupplement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void processRequest(final HttpServletRequest request,
	    final HttpServletResponse response) throws ServletException,
	    IOException {

	LOG.debug("Incoming {} request", request.getMethod());

	checkAndPrepare(request, response, false);

	final MultiValueMap<String, String> result = (MultiValueMap<String, String>) this.converter
		.read(null, new ServletServerHttpRequest(request));

	final List<String> textList = result.get("text");
	final List<String> fromList = result.get("from");
	final List<String> toList = result.get("to");
	final List<String> typeList = result.get("type");

	if (textList == null || textList.isEmpty()) {
	    throw new MissingServletRequestParameterException("text", "string");
	}

	if (fromList == null || fromList.isEmpty()) {
	    throw new MissingServletRequestParameterException("from", "string");
	}

	if (toList == null || toList.isEmpty()) {
	    throw new MissingServletRequestParameterException("to", "string");
	}

	final String type;
	if (null == typeList || typeList.isEmpty()) {
	    type = "sms";
	} else {
	    type = typeList.get(0);
	}

	final Message req = this.messagingEngine.parseSimpleTextMessage(type,
		textList.get(0));
	req.setSender(fromList.get(0));
	req.setRecipient(toList.get(0));

	if (LOG.isDebugEnabled()) {
	    LOG.debug("{} message received for {} from {}", new Object[] {
		    type, req.getRecipient(), req.getSender() });
	}

	final Future<Message> responseMessage = this.receiveCallback
		.receiveAndRespondMessage(req, this.channelId,
			this.incomingChannelId);

	if (LOG.isDebugEnabled()) {
	    LOG.debug("Handed off message to {} for {} awaiting response",
		    this.receiveCallback, this.incomingChannelId);
	}

	final Message message;
	try {
	    message = responseMessage.get();

	    if (message == null) {
		LOG.warn("Timed out waiting for response from {}",
			responseMessage);

		throw new NestedServletException(
			"Timed out waiting for message");
	    }
	} catch (final InterruptedException e) {
	    Thread.currentThread().interrupt(); // reset flag

	    throw new NestedServletException("Interrupted during processing", e);

	} catch (final ExecutionException e) {
	    if (e.getCause() instanceof InterruptedException) {
		throw new NestedServletException( // NOSONAR
			"Interrupted during processing", e.getCause());
	    }

	    throw new NestedServletException("Processing message failed", // NOSONAR
		    e.getCause());
	}

	LOG.debug("Writing response back to client");

	final LinkedMultiValueMap<String, Object> responseMap = new LinkedMultiValueMap<String, Object>();

	responseMap.add("from", message.getSender().getAddress());
	responseMap.add("to", message.getRecipient().getAddress());

	if (message instanceof SmsMessage) {
	    responseMap.add("text", new String(((SmsMessage) message).getText()
		    .getContent(), ((SmsMessage) message).getText()
		    .getCharset()));
	} else if (message instanceof UssdTextMessage) {
	    responseMap.add("text", new String(((UssdTextMessage) message)
		    .getText().getContent(), ((UssdTextMessage) message)
		    .getText().getCharset()));
	}

	this.converter.write(responseMap, this.mediaType,
		new ServletServerHttpResponse(response));

    }

    /**
     * @param channelId
     *            the channelId to set
     */
    public void setChannelId(final String channelId) {
	this.channelId = channelId;
    }

    /**
     * @param incomingChannelId
     *            the incomingChannelId to set
     */
    public void setIncomingChannelId(final String incomingChannelId) {
	this.incomingChannelId = incomingChannelId;
    }

    /**
     * @param messagingEngine
     *            the messagingEngine to set
     */
    public void setMessagingEngine(final IMessagingEngine messagingEngine) {
	this.messagingEngine = messagingEngine;
    }

    @Override
    public void setReceiveCallback(
	    final SynchronousChannelReceiveCallback receiveCallback) {
	this.receiveCallback = receiveCallback;
    }

    /**
     * @param urlSupplement
     *            the urlSupplement to set
     */
    public void setUrlSupplement(final String urlSupplement) {
	this.urlSupplement = urlSupplement;
    }
}
