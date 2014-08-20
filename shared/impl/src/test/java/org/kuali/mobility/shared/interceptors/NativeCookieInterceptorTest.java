/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package org.kuali.mobility.shared.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.http.Cookie;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class NativeCookieInterceptorTest {
	public static final Logger LOG = LoggerFactory.getLogger(NativeCookieInterceptorTest.class);

	private static MockServletContext servletContext;
	private static final String PHONEGAP = "phonegap";
	private static final String PLATFORM = "platform";
	private static final String USER_AGENT = "User-Agent";
	private static final String PLATFORM_VALUES[] = {"iPhone","iPad","iPod","Macintosh","Android","Blackberry","BlackBerry","MSIE","GARBAGE"};
	private static final String RIM = "RIM-Widget";
	private static final String RIM_VALUE = "KME-Blackberry-Application";
	private static final String NATIVE_PARAM = "native";
	private static final String NATIVE_COOKIE = "native";
	private static final String YES = "yes";
	private static final String NO = "no";
	private static final String EMPTY = "";
	private static final String VERSION = "2.2";

	private NativeCookieInterceptor interceptor;

	@Mock
	private java.util.Properties kmeProperties;

	@BeforeClass
	public static void init() {
		servletContext = new MockServletContext();
	}

	@Before
	public void preTest() {
		this.setInterceptor(new NativeCookieInterceptor());
		this.getInterceptor().setKmeProperties(this.getKmeProperties());
        when(getInterceptor().getKmeProperties().getProperty("cookie.max.age","3600")).thenReturn("3600");
	}

	@Test
	public void testCheckPhoneGap() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.addHeader(USER_AGENT,PLATFORM_VALUES[0]);

		request.addParameter(PHONEGAP,EMPTY);

		when(getInterceptor().getKmeProperties().getProperty("kme.secure.cookie","false")).thenReturn("false");

		boolean bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);

		request.removeAllParameters();
		request.addParameter(PHONEGAP,VERSION);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.", bogus);

		request.removeAllParameters();
		Cookie cookieMonster = new Cookie(PHONEGAP,EMPTY);
		request.setCookies(cookieMonster);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.", bogus);

        when(getInterceptor().getKmeProperties().getProperty("kme.secure.cookie","false")).thenReturn("true");

		request.removeAllParameters();
		request.addParameter(PHONEGAP, EMPTY);
		cookieMonster = new Cookie(PHONEGAP,VERSION);
		request.setCookies(cookieMonster);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);
	}

	@Test
	public void testCheckPlatform() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();

        when(getInterceptor().getKmeProperties().getProperty("kme.secure.cookie","false")).thenReturn("false");

		request.addHeader(USER_AGENT,PLATFORM_VALUES[0]);

		request.addParameter(PHONEGAP,VERSION);
		request.addParameter(PLATFORM,EMPTY);
		request.addHeader(RIM,EMPTY);
		boolean bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);

		request.removeAllParameters();

		request.addParameter(PHONEGAP,EMPTY);
		request.addParameter(PLATFORM,EMPTY);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);

		request.removeAllParameters();

		request.addParameter(PHONEGAP,VERSION);
		request.addParameter(PLATFORM,PLATFORM_VALUES[0]);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);

		request.removeAllParameters();

		Cookie[] cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(PHONEGAP,EMPTY);
		cookieMonster[1] = new Cookie(PLATFORM,EMPTY);
		request.setCookies(cookieMonster);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);

        when(getInterceptor().getKmeProperties().getProperty("kme.secure.cookie","false")).thenReturn("true");

		request.removeAllParameters();

		request.addParameter(PHONEGAP,VERSION);
		cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(PHONEGAP,EMPTY);
		cookieMonster[1] = new Cookie(PLATFORM,EMPTY);
		request.setCookies(cookieMonster);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);

		request.removeAllParameters();

		request.addParameter(PHONEGAP,VERSION);
		cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(PHONEGAP,EMPTY);
		cookieMonster[1] = new Cookie(PLATFORM,PLATFORM_VALUES[0]);
		request.setCookies(cookieMonster);
		bogus = getInterceptor().preHandle(request,response,null);
		assertTrue("This test will never fail.",bogus);
	}

	@Test
	public void testUserAgents() throws Exception {
		for( String s: PLATFORM_VALUES ) {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.removeAllParameters();
			request.addParameter(PHONEGAP,EMPTY);
			request.addHeader(USER_AGENT,s);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		}

		for( String s: PLATFORM_VALUES ) {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.removeAllParameters();
			request.addParameter(PHONEGAP,VERSION);
			request.addHeader(USER_AGENT,s);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		}

		for( String s: PLATFORM_VALUES ) {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.removeAllParameters();
			Cookie[] cookieMonster = new Cookie[2];
			cookieMonster[0] = new Cookie(PHONEGAP,EMPTY);
			cookieMonster[1] = new Cookie(PLATFORM,EMPTY);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,s);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		}

		for( String s: PLATFORM_VALUES ) {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.removeAllParameters();
			Cookie cookieMonster = new Cookie(PHONEGAP,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,s);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		}

		for( String s: PLATFORM_VALUES ) {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.removeAllParameters();
			request.addParameter(PLATFORM,EMPTY);
			Cookie cookieMonster = new Cookie(PHONEGAP,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,s);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		}
	}

	@Test
	public void testRIMHeader() {
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie cookieMonster = new Cookie(PHONEGAP,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,EMPTY);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie cookieMonster = new Cookie(PHONEGAP,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,RIM_VALUE);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie cookieMonster = new Cookie(PHONEGAP,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
	}

	@Test
	public void testNative() {
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.addParameter(NATIVE_PARAM,EMPTY);
			Cookie[] cookieMonster = new Cookie[2];
			cookieMonster[0] = new Cookie(PHONEGAP,VERSION);
			cookieMonster[1] = new Cookie(PLATFORM,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.addParameter(NATIVE_PARAM,YES);
			Cookie[] cookieMonster = new Cookie[2];
			cookieMonster[0] = new Cookie(PHONEGAP,VERSION);
			cookieMonster[1] = new Cookie(PLATFORM,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			request.addParameter(NATIVE_PARAM,NO);
			Cookie[] cookieMonster = new Cookie[2];
			cookieMonster[0] = new Cookie(PHONEGAP,VERSION);
			cookieMonster[1] = new Cookie(PLATFORM,VERSION);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie[] cookieMonster = new Cookie[3];
			cookieMonster[0] = new Cookie(PHONEGAP,VERSION);
			cookieMonster[1] = new Cookie(PLATFORM,VERSION);
			cookieMonster[2] = new Cookie(NATIVE_COOKIE,EMPTY);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie[] cookieMonster = new Cookie[3];
			cookieMonster[0] = new Cookie(PHONEGAP,VERSION);
			cookieMonster[1] = new Cookie(PLATFORM,VERSION);
			cookieMonster[2] = new Cookie(NATIVE_COOKIE,YES);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie[] cookieMonster = new Cookie[3];
			cookieMonster[0] = new Cookie(PHONEGAP,VERSION);
			cookieMonster[1] = new Cookie(PLATFORM,VERSION);
			cookieMonster[2] = new Cookie(NATIVE_COOKIE,NO);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie[] cookieMonster = new Cookie[3];
			cookieMonster[0] = new Cookie(PHONEGAP,EMPTY);
			cookieMonster[1] = new Cookie(PLATFORM,VERSION);
			cookieMonster[2] = new Cookie(NATIVE_COOKIE,YES);
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
	}

	@Test
	public void testZeroLengthCookieArray() {
		try {
			MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
			MockHttpServletResponse response = new MockHttpServletResponse();
			Cookie[] cookieMonster = new Cookie[0];
			request.setCookies(cookieMonster);
			request.addHeader(USER_AGENT,PLATFORM_VALUES[1]);
			request.addHeader(RIM,NO);
			boolean bogus = getInterceptor().preHandle(request,response,null);
			assertTrue("This test will never fail.",bogus);
		} catch(Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Exception thrown in code being tested.");
		}
	}

	public static MockServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(MockServletContext servletContext) {
		NativeCookieInterceptorTest.servletContext = servletContext;
	}

	public NativeCookieInterceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(NativeCookieInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
