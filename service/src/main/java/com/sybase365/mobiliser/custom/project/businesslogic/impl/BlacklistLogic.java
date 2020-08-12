/**
 */
package com.sybase365.mobiliser.custom.project.businesslogic.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.sybase365.mobiliser.custom.project.businesslogic.IBlacklistLogic;
import com.sybase365.mobiliser.custom.project.businesslogic.configuration.CustomProjectConfiguration;
import com.sybase365.mobiliser.custom.project.businesslogic.exceptions.BlacklistException;
import com.sybase365.mobiliser.custom.project.businesslogic.exceptions.BlacklistExistsException;
import com.sybase365.mobiliser.custom.project.jobs.event.model.BlacklistEvent;
import com.sybase365.mobiliser.custom.project.persistence.dao.factory.api.DaoFactory;
import com.sybase365.mobiliser.custom.project.persistence.model.Blacklist;
import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;
import com.sybase365.mobiliser.framework.event.generator.EventGenerator;
import com.sybase365.mobiliser.money.businesslogic.util.EntityMandatoryException;
import com.sybase365.mobiliser.money.businesslogic.util.EntityNotFoundException;
import com.sybase365.mobiliser.money.businesslogic.util.IllegalDataException;
import com.sybase365.mobiliser.money.persistence.model.customer.Customer;
import com.sybase365.mobiliser.util.messaging.service.api.IMessagingService;

/**
 * The <code>Blacklist</code> represents a blacklisted person by name
 *
 * @since 2012-01-20
 */
public class BlacklistLogic implements IBlacklistLogic, InitializingBean {

    /** The <code>Log</code> instance to use. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(BlacklistLogic.class);

    private CustomProjectConfiguration configuration;
    private DaoFactory daoFactory;
    private EventGenerator eventGenerator;
    private IMessagingService messagingService;

    @Override
    public void afterPropertiesSet() {
	if (this.daoFactory == null) {
	    throw new IllegalStateException("daoFactory is required");
	}

	if (this.eventGenerator == null) {
	    throw new IllegalStateException("eventGenerator is required");
	}

	if (this.messagingService == null) {
	    throw new IllegalStateException("messagingService is required");
	}

	if (this.configuration == null) {
	    throw new IllegalStateException("configuration is required");
	}
    }

    @Override
    public void checkBlacklist(final String name, long callerId)
	    throws BlacklistException {

	if (!this.daoFactory.getBlacklistDao().getBlacklistsForName(name, null)
		.isEmpty()) {
	    throw new BlacklistException(
		    BlacklistException.BlacklistReason.NAME_MATCH);
	}
    }

    /**
     * Checks if there's a customer with that display name and generates an
     * event in this case
     *
     * @param blacklistId
     * @param blacklistType
     * @param name
     * @param added
     */
    private void checkForExistingCustomerAndCreateEvent(final long blacklistId,
	    final BlacklistType blacklistType, final String name,
	    final boolean added) {

	final List<Customer> customers = this.daoFactory.getBlacklistDao()
		.getCustomersByDisplayName(name);

	for (final Customer c : customers) {
	    final BlacklistEvent event = new BlacklistEvent();

	    event.setBlacklistId(blacklistId);
	    event.setBlacklistType(blacklistType.getId().intValue());
	    event.setAddedToBlacklist(added);
	    event.setCustomerId(c.getId().longValue());

	    this.eventGenerator.create(event);
	}
    }

    @Override
    public long createBlacklist(final Blacklist blacklist, final long callerId) {
	LOG.trace("#createBlacklist({},{})", blacklist, Long.toString(callerId));

	// check if an object has been given
	if (blacklist == null) {
	    throw new EntityMandatoryException("No blacklist information set");
	}

	// id must not be set when creating a new entity
	if (blacklist.getId() != null) {
	    throw new IllegalDataException(
		    "Blacklist ID must not be set when creating new one", "id");
	}

	try {
	    this.checkBlacklist(blacklist.getName(), callerId);
	} catch (final BlacklistException e) {
	    throw new BlacklistExistsException("blacklist with name "
		    + blacklist.getName() + " already exists.", e);
	}

	// and now save...
	this.daoFactory.getBlacklistDao().save(blacklist,
		Long.valueOf(callerId));

	checkForExistingCustomerAndCreateEvent(blacklist.getId().longValue(),
		blacklist.getBlacklistType(), blacklist.getName(), true);

	return blacklist.getId().longValue();
    }

    @Override
    public void deactivateBlacklist(final long blacklistId, final long callerId)
	    throws EntityNotFoundException {

	if (LOG.isTraceEnabled()) {
	    LOG.trace("#deactivateBlacklist({},{})",
		    Long.toString(blacklistId), Long.toString(callerId));
	}

	final Blacklist blacklist = this.daoFactory.getBlacklistDao().getById(
		Long.valueOf(blacklistId));

	if (blacklist == null) {
	    throw new EntityNotFoundException("No blacklist found with ID #"
		    + blacklistId, Long.toString(blacklistId));
	}

	this.daoFactory.getBlacklistDao().delete(blacklist);
    }

    @Override
    public Blacklist getBlacklist(final long blacklistId, final long callerId)
	    throws EntityNotFoundException {
	LOG.trace("#getBlacklist({},{})", Long.toString(blacklistId),
		Long.toString(callerId));

	final Blacklist blacklist = this.daoFactory.getBlacklistDao().getById(
		Long.valueOf(blacklistId));

	if (blacklist == null) {
	    throw new EntityNotFoundException("Blacklist id " + blacklistId
		    + " was not found.", Long.toString(blacklistId));
	}

	return blacklist;

    }

    @Override
    public List<Blacklist> matchBlacklist(final String name, long callerId) {
	return this.daoFactory.getBlacklistDao().getBlacklistsForName(name,
		null);

    }

    /**
     * @param configuration
     *            the configuration to set
     */
    public void setConfiguration(final CustomProjectConfiguration configuration) {
	this.configuration = configuration;
    }

    /**
     * @param daoFactory
     *            the daoFactory to set
     */
    public void setDaoFactory(final DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    /**
     * @param eventGenerator
     *            the eventGenerator to set
     */
    public void setEventGenerator(final EventGenerator eventGenerator) {
	this.eventGenerator = eventGenerator;
    }

    /**
     * @param messagingService
     *            the messagingService to set
     */
    public void setMessagingService(final IMessagingService messagingService) {
	this.messagingService = messagingService;
    }

    @Override
    public void updateBlacklist(Blacklist blacklist, long callerId) {
	LOG.trace("#updateBlacklist({},{})", blacklist, Long.valueOf(callerId));

	this.daoFactory.getBlacklistDao().evict(blacklist);

	final Blacklist dbBlacklist = this.daoFactory.getBlacklistDao()
		.getById(blacklist.getId());

	final String originalName = dbBlacklist.getName();

	this.daoFactory.getBlacklistDao().evict(dbBlacklist);

	this.daoFactory.getBlacklistDao().update(blacklist,
		Long.valueOf(callerId));

	checkForExistingCustomerAndCreateEvent(blacklist.getId().longValue(),
		blacklist.getBlacklistType(), blacklist.getName(), true);
	checkForExistingCustomerAndCreateEvent(blacklist.getId().longValue(),
		blacklist.getBlacklistType(), originalName, false);
    }

}
