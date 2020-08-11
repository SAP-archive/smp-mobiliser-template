/**
 */
package com.sybase365.mobiliser.custom.project.jobs.event.model;

import com.sybase365.mobiliser.framework.event.model.RegularEvent;
import com.sybase365.mobiliser.framework.event.model.data.EventData;

/**
 * The <code>BlacklistEvent</code> is generated if a known customer was put onto
 * the blacklist.
 *
 * @since 2012-01-23
 */
public class BlacklistEvent extends RegularEvent {

    private static final long serialVersionUID = 1L;

    /** event type. */
    public static final String EVENT_TYPE = "BlacklistEvent";

    private static final String KEY_CUSTOMER_ID = "customerId";
    private static final String KEY_BLACKLIST_ID = "blacklistId";
    private static final String KEY_BLACKLIST_TYPE = "blacklistType";
    private static final String KEY_BLACKLIST_ADDED = "addedToBlacklist";

    /**
     */
    public BlacklistEvent() {
	super();
    }

    /**
     * @param data
     */
    public BlacklistEvent(final EventData data) {
	super(data);
    }

    /**
     * @param name
     * @param data
     */
    public BlacklistEvent(final String name, final EventData data) {
	super(name, data);
    }

    @Override
    public void init() {
	this.setName(EVENT_TYPE);

	if (this.getData() == null) {
	    this.setData(new EventData());
	}
    }

    /**
     * Returns the customer id.
     *
     * @return the customer id
     */
    public long getCustomerId() {
	return Long.parseLong(this.getData().get(KEY_CUSTOMER_ID));
    }

    /**
     * Sets the customer id.
     *
     * @param customerId
     *            the customer id
     */
    public void setCustomerId(final long customerId) {
	this.getData().put(KEY_CUSTOMER_ID, Long.toString(customerId));
    }

    /**
     * Returns the blacklist id.
     *
     * @return the blacklist id
     */
    public long getBlacklistId() {
	return Long.parseLong(this.getData().get(KEY_BLACKLIST_ID));
    }

    /**
     * Sets the blacklist id.
     *
     * @param blacklistId
     *            the blacklist id
     */
    public void setBlacklistId(final long blacklistId) {
	this.getData().put(KEY_BLACKLIST_ID, Long.toString(blacklistId));
    }

    /**
     * Returns the blacklist type.
     *
     * @return the blacklist type
     */
    public int getBlacklistType() {
	return Integer.parseInt(this.getData().get(KEY_BLACKLIST_TYPE));
    }

    /**
     * Sets the blacklist type.
     *
     * @param blacklistType
     *            the blacklist type
     */
    public void setBlacklistType(final int blacklistType) {
	this.getData().put(KEY_BLACKLIST_TYPE, Integer.toString(blacklistType));
    }

    /**
     * Returns whether it is added to the blacklist.
     *
     * @return true or false
     */
    public boolean isAddedToBlacklist() {
	return Boolean.parseBoolean(this.getData().get(KEY_BLACKLIST_ADDED));
    }

    /**
     * Marks added to the blacklist as true or false.
     *
     * @param addedToBlacklist
     *            the flag to set
     */
    public void setAddedToBlacklist(final boolean addedToBlacklist) {
	this.getData().put(KEY_BLACKLIST_TYPE,
		Boolean.toString(addedToBlacklist));
    }

}
