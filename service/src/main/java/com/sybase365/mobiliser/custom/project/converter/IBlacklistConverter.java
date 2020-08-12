/**
 */
package com.sybase365.mobiliser.custom.project.converter;

import com.sybase365.mobiliser.custom.project.persistence.model.Blacklist;
import com.sybase365.mobiliser.money.businesslogic.util.IllegalDataException;

/**
 * Converts between custom JPA beans and custom contract JAX beans.
 *
 */

public interface IBlacklistConverter {

    /**
     * Converts a dto into an entity.
     *
     * @param contract
     *            the dto
     * @return the entity
     * @throws IllegalDataException
     *             if the incoming data is illegal
     */
    Blacklist fromContract(
	    final com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.Blacklist contract)
	    throws IllegalDataException;

    /**
     * Converts an entity into a dto.
     *
     * @param db
     *            the entity
     * @return the dto
     */
    com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.Blacklist toContract(
	    final Blacklist db);

}
