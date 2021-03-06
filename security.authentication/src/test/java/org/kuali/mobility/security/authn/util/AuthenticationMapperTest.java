/**
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.kuali.mobility.security.authn.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class AuthenticationMapperTest {
	private static Logger LOG = LoggerFactory.getLogger(AuthenticationMapperTest.class);

	private static final String TEST_PROPERTIES_FILE = "/authentication.xml";

	@Test
	public void testRequiresAuthentication() {
		AuthenticationMapper am = new AuthenticationMapper(TEST_PROPERTIES_FILE);

		assertTrue("Failed to load properties.", am.getUrlPatterns() != null && am.getUrlPatterns().size() > 0);

		for (String s : am.getUrlPatterns()) {
			LOG.debug("Configured to protect: " + s);
		}
		assertTrue("Failed to find URL pattern /oauth in url patterns.", am.requiresAuthentication("/oauth"));
		assertTrue("Improperly found URL pattern /fluffybunnies in url patterns.", !am.requiresAuthentication("/fluffybunnies"));
	}

}
