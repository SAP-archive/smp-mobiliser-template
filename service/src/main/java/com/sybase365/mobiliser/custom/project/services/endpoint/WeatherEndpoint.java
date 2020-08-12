/**
 */
package com.sybase365.mobiliser.custom.project.services.endpoint;

import org.springframework.beans.factory.InitializingBean;

import com.cdyne.ws.weatherws.WeatherReturn;
import com.cdyne.ws.weatherws.WeatherSoap;
import com.sybase365.mobiliser.custom.project.services.contract.api.IWeatherEndpoint;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetWeatherRequest;
import com.sybase365.mobiliser.custom.project.services.contract.v1_0.GetWeatherResponse;
import com.sybase365.mobiliser.framework.gateway.security.api.ICallerUtils;

/**
 * @since 2012-05-10
 */
public class WeatherEndpoint implements IWeatherEndpoint, InitializingBean {

    /** The <code>Log</code> instance to use. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(WeatherEndpoint.class);

    private ICallerUtils callerUtils;
    private WeatherSoap weatherClient;

    @Override
    public void afterPropertiesSet() {
	if (this.callerUtils == null) {
	    throw new IllegalStateException("callerUtils is required");
	}

	if (this.weatherClient == null) {
	    throw new IllegalStateException("blacklistLogic is required");
	}
    }

    @Override
    public GetWeatherResponse getWeather(final GetWeatherRequest request) {

	final GetWeatherResponse response = new GetWeatherResponse();

	LOG.debug("Calling out to weather station");

	final WeatherReturn result = this.weatherClient
		.getCityWeatherByZIP(request.getZip());

	LOG.debug("Return weather");

	response.setWeather("Temperature: " + result.getTemperature());

	return response;
    }

    /**
     * @param callerUtils
     *            the callerUtils to set
     */
    public void setCallerUtils(final ICallerUtils callerUtils) {
	this.callerUtils = callerUtils;
    }

    /**
     * @param weatherClient
     *            the weatherClient to set
     */
    public void setWeatherClient(final WeatherSoap weatherClient) {
	this.weatherClient = weatherClient;
    }

}
