/**
 * Copyright (C) 2012-2015 SAP SE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
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
