/**
 */
package com.sybase365.mobiliser.custom.project.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sybase365.mobiliser.framework.persistence.model.GeneratedIdEntry;

/**
 * The <code>Blacklist</code> represents a blacklisted person by name.
 *
 * @since 2012-01-19
 */
@Entity
@Table(name = "CUS_BLACKLIST")
@AttributeOverride(name = "id", column = @Column(name = "ID_ENTITY"))
public class Blacklist extends GeneratedIdEntry {

    // /////////////////////////////////////////////////////////////////////////
    // CONSTANTS////////////////////////////////////////////////////////////////

    /** Serial version ID. */
    private static final long serialVersionUID = -3719016483766796488L;

    // /////////////////////////////////////////////////////////////////////////
    // FIELDS///////////////////////////////////////////////////////////////////

    /** The blacklist type - no mapping for lookup tables. */
    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_BLACKLIST_TYPE", nullable = false)
    private BlacklistType blacklistType;

    /** The name of the blacklisted person. */
    @Basic(optional = true)
    @Column(name = "STR_NAME", nullable = false, length = LENGTH_STRING_LARGE)
    private String name;

    // /////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS/////////////////////////////////////////////////////////////

    /**
     * <p>
     * Creates a new instance of <code>Blacklist</code>.
     * </p>
     *
     * @deprecated <b style="color:red">This constructor is available for DAO's
     *             <code>newInstance</code> method only and should not be used
     *             programmatically. Please use the <code>newInstance</code>
     *             method of the corresponding DAO.</b>
     */
    @Deprecated
    public Blacklist() {
	super();
    }

    // /////////////////////////////////////////////////////////////////////////
    // METHODS//////////////////////////////////////////////////////////////////

    // GETTER_AND_SETTER////////////////////////////////////////////////////////

    /**
     * <p>
     * Returns the blacklist type of this name.
     * </p>
     *
     * @return an <code>int</code> representing the blacklist reason
     */
    public BlacklistType getBlacklistType() {
	return this.blacklistType;
    }

    /**
     * <p>
     * Checks whether the <i>blacklistType</i> of this instance is set.
     * </p>
     *
     * @return <code>false</code> if the <tt>blacklistType</tt> is
     *         <code>null</code>. Otherwise, it returns <code>true</code>.
     */
    public boolean isSetBlacklistType() {
	return this.blacklistType != null;
    }

    /**
     * <p>
     * Sets the <i>blacklistType</i> of this instance.
     * </p>
     *
     * @param blacklistType
     *            the <tt>blacklistType</tt> value to set
     */
    public void setBlacklistType(BlacklistType blacklistType) {
	this.blacklistType = blacklistType;
    }

    /**
     * <p>
     * Returns the name of this customer.
     * </p>
     *
     * @return the name
     */
    public String getName() {
	return this.name;
    }

    /**
     * <p>
     * Checks whether the <i>name</i> of this instance is set.
     * </p>
     *
     * @return <code>false</code> if the <tt>name</tt> is <code>null</code>.
     *         Otherwise, it returns <code>true</code>.
     */
    public boolean isSetName() {
	return this.name != null;
    }

    /**
     * <p>
     * Sets the <i>name</i> of this instance.
     * </p>
     *
     * @param name
     *            the <tt>name</tt> value to set
     */
    public void setName(String name) {
	this.name = name;
    }

    // /////////////////////////////////////////////////////////////////////////
    // NESTED_CLASSES///////////////////////////////////////////////////////////

}
