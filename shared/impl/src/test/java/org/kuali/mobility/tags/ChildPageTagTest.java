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

package org.kuali.mobility.tags;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockJspWriter;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class ChildPageTagTest {
	private static final String NL = System.getProperty("line.separator").toString();
	private static final Logger LOG = LoggerFactory.getLogger(ChildPageTagTest.class);

	private static MockServletContext mockServletContext;
	private PageContext mockPageContext;
	private ChildPageTag tag;

	private static final String TITLE = "TEST_TITLE";
	private static final String ID = "TEST_ID";
	private static final String APPCACHE = "false";
	private static final String JQM_HIDE = "hide";
	private static final String JQM_FIXED = "fixed";
	private static final String PHONEGAP_141 = "1.4.1";
	private static final String PHONEGAP_170 = "1.7.0";
	private static final String PHONEGAP_220 = "2.2.0";
	private static final String PHONEGAP_230 = "2.3.0";
	private static final String PHONEGAP_240 = "2.4.0";
	private static final String PHONEGAP_250 = "2.5.0";
	private static final String PHONEGAP_260 = "2.6.0";
	private static final String PHONEGAP_270 = "2.7.0";
	private static final String PHONEGAP_281 = "2.8.1";
	private static final String TEST_ATTRIBUTE = "BOGUS_TEXT";


	@BeforeClass
	public static void init() {
		mockServletContext = new MockServletContext();
		String configLocations = "classpath:TagSpringBeans.xml";
		mockServletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocations);
		ContextLoader loader = new ContextLoader();
		loader.initWebApplicationContext(mockServletContext);
	}

	@Before
	public void setup() throws Exception {
		mockPageContext = new MockPageContext(mockServletContext);
		tag = new ChildPageTag();
		tag.setJspContext(mockPageContext);
		// Required attributes of the PageTag are: id, title so set them here.
		tag.setId(ID);
		tag.setTitle(TITLE);

		JspFragment jspBodyFragment = mock(JspFragment.class);
		doAnswer(new FakeJspBodyAnswerer()).when(jspBodyFragment)
			.invoke((Writer) anyObject());
		tag.setJspBody(jspBodyFragment);
	}

	@Test
	public void testChildPageTagWithDefaults() {
		try {
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_DEFAULTS));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithJQMHeaderHide() {
		try {
			tag.setJqmHeader(JQM_HIDE);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_JQM_HEADER_HIDE));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithJQMHeaderFixed() {
		try {
			tag.setJqmHeader(JQM_FIXED);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_JQM_HEADER_FIXED));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithLoginButton() {
		try {
			tag.setLoginButton(true);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_LOGIN));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithLogoutButton() {
		try {
			User user = new UserImpl();
			user.setLoginName(TEST_ATTRIBUTE);
			mockPageContext.getSession().setAttribute(AuthenticationConstants.KME_USER_KEY,user);
			tag.setLoginButton(true);
			tag.setLogoutButtonURL(TEST_ATTRIBUTE);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_LOGOUT));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithBackButton() {
		try {
			tag.setBackButton(true);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_BACK_BUTTON));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithBackButton2() {
		try {
			tag.setBackButton(true);
			tag.setBackButtonURL(TEST_ATTRIBUTE);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_BACK_BUTTON2));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithPreferencesButton() {
		try {
			tag.setPreferencesButton(true);
			tag.setPreferencesButtonURL(TEST_ATTRIBUTE);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_PREFERENCES_BUTTON));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithPreferencesButton2() {
		try {
			tag.setPreferencesButton(true);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_PREFERENCES_BUTTON2));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	@Test
	public void testChildPageTagWithHomeButton() {
		try {
			tag.setHomeButton(true);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(CHILD_PAGE_TAG_WITH_HOME_BUTTON));
		} catch (JspException je) {
			LOG.error(je.getLocalizedMessage(), je);
			fail("JspException found testing tag.");
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
			fail("IOException found testing tag.");
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			fail("NullPointerException found testing tag.");
		}
	}

	private static final String CHILD_PAGE_TAG_WITH_DEFAULTS =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_JQM_HEADER_HIDE =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\" style=\"display:none\">" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_JQM_HEADER_FIXED =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\" data-position=\"fixed\">" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_LOGIN =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<a href=\"/login\" data-role=\"button\" data-icon=\"lock\">Login</a>" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_LOGOUT =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<a href=\"BOGUS_TEXT\" data-role=\"button\" data-icon=\"unlock\">Logout</a>" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_BACK_BUTTON =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<a href=\"javascript:history.back()\" class=\"ui-btn-left\" data-icon=\"back\" data-iconpos=\"notext\" data-transition=\"pop\">Back</a>" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_BACK_BUTTON2 =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<a href=\"BOGUS_TEXT\" class=\"ui-btn-left\" data-icon=\"back\" data-iconpos=\"notext\" data-transition=\"pop\">Back</a>" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_PREFERENCES_BUTTON =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"<a href=\"BOGUS_TEXT\" class=\"ui-btn-right\" data-icon=\"gear\" data-iconpos=\"notext\">Preferences</a>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_PREFERENCES_BUTTON2 =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"<a href=\"/preferences\" class=\"ui-btn-right\" data-icon=\"gear\" data-iconpos=\"notext\">Preferences</a>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static final String CHILD_PAGE_TAG_WITH_HOME_BUTTON =
		"<div data-role=\"page\" id=\"TEST_ID\">" + NL + 
			"<div data-role=\"header\">" + NL + 
			"<h1>TEST_TITLE</h1>" + NL + 
			"<a href=\"/home\" class=\"ui-btn-right\" data-icon=\"home\" data-iconpos=\"notext\">Home</a>" + NL + 
			"</div>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>" + NL;

	private static class FakeJspBodyAnswerer implements Answer {
		public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
			MockJspWriter writer = (MockJspWriter)invocationOnMock.getArguments()[0];
			writer.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			return null;
		}
	}

}
