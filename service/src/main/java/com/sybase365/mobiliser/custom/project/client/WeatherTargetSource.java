/**
 */
package com.sybase365.mobiliser.custom.project.client;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

import com.cdyne.ws.weatherws.WeatherSoap;
import com.sybase365.mobiliser.util.prefs.api.IPreferences;
import com.sybase365.mobiliser.util.prefs.util.AbstractRefreshableTargetSource;

/**
 * @since 2012-05-10
 */
public class WeatherTargetSource extends
	AbstractRefreshableTargetSource<WeatherSoap> implements
	BeanClassLoaderAware {

    private ClassLoader classLoader;

    private final IPreferences node;

    /**
     * @param node
     */
    public WeatherTargetSource(final IPreferences node) {
	super();
	this.node = node;
    }

    @Override
    public Class<WeatherSoap> getTargetClass() {
	return WeatherSoap.class;
    }

    @Override
    protected WeatherSoap instantiate() {

	final ClassLoader contextClassLoader = Thread.currentThread()
		.getContextClassLoader();

	try {
	    Thread.currentThread().setContextClassLoader(this.classLoader);

	    final JaxWsPortProxyFactoryBean jaxWsProxyFactory = new JaxWsPortProxyFactoryBean();
	    jaxWsProxyFactory.setBeanClassLoader(this.classLoader);
	    jaxWsProxyFactory.setServiceInterface(WeatherSoap.class);
	    jaxWsProxyFactory.setServiceName("WeatherSoap");
	    jaxWsProxyFactory.setEndpointAddress(this.node.get("service.url",
		    "http://wsf.cdyne.com/WeatherWS/Weather.asmx"));
	    jaxWsProxyFactory.afterPropertiesSet();

	    return (WeatherSoap) jaxWsProxyFactory.getObject();
	} finally {
	    Thread.currentThread().setContextClassLoader(contextClassLoader);
	}

    }

    @Override
    public void releaseTarget(final Object target) throws Exception {
	// nothing to do
    }

    @Override
    public void setBeanClassLoader(final ClassLoader classLoader) {
	this.classLoader = classLoader;
    }

}
