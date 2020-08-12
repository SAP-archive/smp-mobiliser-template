/**
 */
package com.sybase365.mobiliser.custom.project.converter;

import com.sybase365.mobiliser.custom.project.persistence.model.BlacklistType;

/**
 * Converts between custom JPA beans and custom contract JAX beans.
 *
 * @since 2012-02-03
 */
public interface IBlacklistTypeConverter {

    /**
     * Converts a dto into an entity.
     *
     * @param contract
     *            the dto
     * @return the entity
     */
    BlacklistType fromContract(
	    com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType contract);

    /**
     * Converts an entity into a dto.
     *
     * @param model
     *            the entity
     * @return the dto
     */
    com.sybase365.mobiliser.custom.project.services.contract.v1_0.beans.BlacklistType toContract(
	    BlacklistType model);

}
