/**
 */
package com.sybase365.mobiliser.custom.project.persistence.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;

import com.sybase365.mobiliser.custom.project.persistence.dao.api.BlacklistDAO;
import com.sybase365.mobiliser.custom.project.persistence.model.Blacklist;
import com.sybase365.mobiliser.framework.persistence.hibernate.dao.GeneratedIdDAOHbnImpl;
import com.sybase365.mobiliser.money.persistence.model.customer.Customer;

/**
 * @since 2012-01-18
 */
public class BlacklistDaoHbnImpl extends GeneratedIdDAOHbnImpl<Blacklist>
	implements BlacklistDAO {

    /** The <code>Log</code> instance to use. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(BlacklistDaoHbnImpl.class);

    /**
     */
    public BlacklistDaoHbnImpl() {
	super();
    }

    @Override
    public List<Blacklist> getBlacklistsForName(final String name,
	    final Integer blacklistType) {

	String hql = "from Blacklist b where b.name = :name ";

	if (blacklistType != null) {
	    hql += " and b.blacklistType = :type ";
	}

	final Query query = getSession().createQuery(hql).setString("name",
		name);

	if (blacklistType != null) {
	    query.setInteger("type", blacklistType.intValue());
	}

	@SuppressWarnings("unchecked")
	final List<Blacklist> result = query.list();

	if (result == null || result.isEmpty()) {
	    return Collections.emptyList();
	}

	return result;
    }

    @Override
    public List<Customer> getCustomersByDisplayName(final String name) {
	final Query query = getSession().createQuery(
		"FROM Customer where displayName = :name").setString("name",
		name);

	@SuppressWarnings("unchecked")
	final List<Customer> result = query.list();

	if (result == null || result.isEmpty()) {
	    LOG.trace("Returning empty collection");
	    return Collections.emptyList();
	}

	return result;

    }

    @Override
    public Class<Blacklist> getEntityClass() {
	return Blacklist.class;
    }
}
