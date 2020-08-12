/**
 */
package com.sybase365.mobiliser.custom.project.services.contract.api;

import javax.annotation.security.RolesAllowed;

import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetWeatherRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetWeatherResponse;

/**
 * Defines all services that are exposed related to the current weather.
 *
 * @since 2012-05-10
 */
public interface IWeatherEndpoint {

    /**
     * @param request
     * @return the response
     */
    @RolesAllowed(value = "WS_GET_WEATHER")
    GetWeatherResponse getWeather(final GetWeatherRequest request);

}
