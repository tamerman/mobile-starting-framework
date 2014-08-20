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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.push.entity.Device;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
public class MultiPageTagTest {
	private static final Logger LOG = LoggerFactory.getLogger(PageTagTest.class);
	private static final String NL = System.getProperty("line.separator").toString();
	private static MockServletContext mockServletContext;
	private PageContext mockPageContext;
	private MultiPageTag tag;

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
		tag = new MultiPageTag();
		tag.setJspContext(mockPageContext);
		// Required attributes of the PageTag are: id, title so set them here.
		tag.setId(ID);
		tag.setTitle(TITLE);
		// Disable appcache and google analytics for simplicity.
		tag.setAppcacheEnabled(APPCACHE);
		tag.setDisableGoogleAnalytics(true);

		JspFragment jspBodyFragment = mock(JspFragment.class);
		doAnswer(new FakeJspBodyAnswerer()).when(jspBodyFragment)
			.invoke((Writer) anyObject());
		tag.setJspBody(jspBodyFragment);
	}

	@Test
	public void testMultiPageTagWithDefaults() {
		try {
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_DEFAULTS));
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
	public void testMultiPageTagWithiOSPhoneGap141() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_141);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_141));
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
	public void testMultiPageTagWithiOSPhoneGap170() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_170);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_170));
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
	public void testMultiPageTagWithiOSPhoneGap220() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_220);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_220));
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
	public void testMultiPageTagWithiOSPhoneGap230() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_230);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_230));
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
	public void testMultiPageTagWithiOSPhoneGap240() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_240);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_240));
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
	public void testMultiPageTagWithiOSPhoneGap250() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_250);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_250));
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
	public void testMultiPageTagWithiOSPhoneGap260() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_260);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_260));
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
	public void testMultiPageTagWithiOSPhoneGap270() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_270);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_270));
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
	public void testMultiPageTagWithiOSPhoneGap281() {
		try {
			tag.setPlatform(Device.TYPE_IOS);
			tag.setPhonegap(PHONEGAP_281);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_IOS_PG_281));
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
	public void testMultiPageTagWithAndroidPhoneGapDefault() {
		try {
			tag.setPlatform(Device.TYPE_ANDROID);
			tag.setPhonegap(PHONEGAP_141);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_ANDROID_PG_DEFAULT));
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
	public void testMultiPageTagWithAndroidPhoneGap220() {
		try {
			tag.setPlatform(Device.TYPE_ANDROID);
			tag.setPhonegap(PHONEGAP_220);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_ANDROID_PG_220));
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
	public void testMultiPageTagWithBlackberryPhoneGapDefault() {
		try {
			tag.setPlatform(Device.TYPE_BLACKBERRY);
			tag.setPhonegap(PHONEGAP_141);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_BLACKBERRY_PG_DEFAULT));
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
	public void testMultiPageTagWithBlackberryPhoneGap220() {
		try {
			tag.setPlatform(Device.TYPE_BLACKBERRY);
			tag.setPhonegap(PHONEGAP_220);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_BLACKBERRY_PG_220));
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
	public void testMultiPageTagWithWindowsPhoneGapDefault() {
		try {
			tag.setPlatform(Device.TYPE_WINDOWS);
			tag.setPhonegap(PHONEGAP_141);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_WINDOWS_PG_DEFAULT));
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
	public void testMultiPageTagWithWindowsPhoneGap220() {
		try {
			tag.setPlatform(Device.TYPE_WINDOWS);
			tag.setPhonegap(PHONEGAP_220);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_WINDOWS_PG_220));
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
	public void testMultiPageTagWithJavaScriptFiles() {
		try {
			tag.setJsFilename(TEST_ATTRIBUTE);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_JAVASCRIPT));
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
	public void testMultiPageTagWithOnBodyLoad() {
		try {
			tag.setOnBodyLoad(TEST_ATTRIBUTE);
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(PAGE_TAG_WITH_ON_BODY_LOAD));
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

	private static final String PAGE_TAG_WITH_DEFAULTS =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_141 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/phonegap-1.4.1.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/ChildBrowser.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/barcodescanner.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/PushHandler.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/Badge.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/applicationPreferences.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_170 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/cordova-1.7.0.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/ChildBrowser.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/barcodescanner.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/ActionSheet.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/Badge.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/LocalNotifications.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/Notifications.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/PrintPlugin.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/1.7.0/applicationPreferences.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_220 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.2.0/cordova-2.2.0.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.2.0/ActionSheet.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.2.0/applicationPreferences.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.2.0/AudioStreamer.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.2.0/Badge.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.2.0/barcodescanner.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.2.0/ChildBrowser.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/PushHandler.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_230 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.3.0/cordova-2.3.0.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_240 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.4.0/cordova-2.4.0.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_250 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.5.0/cordova-2.5.0.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_260 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.6.0/cordova-2.6.0.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_270 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.7.0/cordova-2.7.0.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_IOS_PG_281 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/iOS/2.8.1/cordova.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_ANDROID_PG_DEFAULT =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/phonegap-1.4.1.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/childbrowser.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/barcodescanner.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/statusbarnotification.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/C2DMPlugin.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/PG_C2DM_script.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/PushHandler.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_ANDROID_PG_220 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/cordova-2.2.0.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/childbrowser.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/barcodescanner.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/statusbarnotification.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/datePickerPlugin.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/applicationPreferences.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/AudioStreamer.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/GCMPlugin.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/2.2.0/CORDOVA_GCM_script.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/android/PushHandler.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_BLACKBERRY_PG_DEFAULT =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_BLACKBERRY_PG_220 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/blackberry/2.2.0/cordova-2.2.0.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/blackberry/2.2.0/kme-application.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_WINDOWS_PG_DEFAULT =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_WINDOWS_PG_220 =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/PushConfig.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/windowsMobile/2.2.0/cordova-2.2.0.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_JAVASCRIPT =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BOGUS_TEXT.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static final String PAGE_TAG_WITH_ON_BODY_LOAD =
		"<!DOCTYPE html>" + NL + 
			"<html>" + NL + 
			"<head>" + NL + 
			"<title>TEST_TITLE</title>" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"icon\" />" + NL + 
			"<link href=\"http://www.kuali.org/favicon.ico\" rel=\"shortcut icon\" />" + NL + 
			"<link rel=\"apple-touch-icon\" href=\"/touch-icon-iphone.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/touch-icon-ipad.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/touch-icon-iphone-retina.png\"/>" + NL + 
			"<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/touch-icon-ipad-retina.png\"/>" + NL + 
			"<link href=\"/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<link href=\"/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/custom.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.mobile.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.tmpl.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.validate.ready.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.templates.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.transit.min.js\"></script>" + NL + 
//			"<script type=\"text/javascript\" src=\"/js/deviceDetector.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/BrowserDetect.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/ServerDetails.js\"></script>" + NL + 
			"<script type=\"text/javascript\" src=\"/js/jquery.autoellipsis-1.0.3.min.js\"></script>" + NL + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" + NL + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + NL + 
			"</head>" + NL + 
			"<body onload='BOGUS_TEXT'>" + NL + 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</body>" + NL + 
			"</html>" + NL;

	private static class FakeJspBodyAnswerer implements Answer {
		public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
			MockJspWriter writer = (MockJspWriter)invocationOnMock.getArguments()[0];
			writer.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			return null;
		}
	}

}
