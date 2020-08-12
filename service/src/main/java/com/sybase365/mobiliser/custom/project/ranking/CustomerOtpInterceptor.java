/**
 */
package com.sybase365.mobiliser.custom.project.ranking;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Example of overriding a single method in an exported service interface.
 *
 * @since 2012-06-19
 */
public final class CustomerOtpInterceptor implements MethodInterceptor {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
	    .getLogger(CustomerOtpInterceptor.class);

    /**
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
	// this will only be invoked for methods matching the pointcut
	// configured in xml
	LOG.warn("CALLING: {}", invocation.getMethod().getName());

	return invocation.proceed();
    }
}
