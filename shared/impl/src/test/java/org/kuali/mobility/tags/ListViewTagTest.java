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

package org.kuali.mobility.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class ListViewTagTest {
	private static Logger LOG = LoggerFactory.getLogger(ListViewTagTest.class);
	private static final String NL = System.getProperty("line.separator").toString();
	private static MockServletContext mockServletContext;
	private PageContext mockPageContext;
	private ListViewTag tag;

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
		tag = new ListViewTag();
		tag.setJspContext(mockPageContext);

		JspFragment jspBodyFragment = mock(JspFragment.class);
		doAnswer(new FakeJspBodyAnswerer()).when(jspBodyFragment)
				.invoke((Writer) anyObject());
		tag.setJspBody(jspBodyFragment);
	}

	@Test
	public void testListViewTagWithNoArgs() {
		String expectedResult = "<ul data-role=\"listview\" data-inset=\"false\" data-filter=\"false\">" + NL +
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</ul>" + NL;

		try {
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Expected  [" + expectedResult + "]");
			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(expectedResult));
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
	public void testListViewTagWithArgs() {
		String expectedResult = "<ul data-role=\"listview\" id=\"TEST_ID\" class=\"TEST_STYLE\" data-theme=\"A\" data-inset=\"false\" data-filter=\"false\">" + NL +
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit.</ul>" + NL;

		try {
			tag.setDataTheme("A");
			tag.setCssClass("TEST_STYLE");
			tag.setId("TEST_ID");
			tag.doTag();
			String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();

			LOG.debug("Expected  [" + expectedResult + "]");
			LOG.debug("Output is [" + output + "]");

			assertTrue("Tag failed to provide expected output.", output.equals(expectedResult));
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

	private static class FakeJspBodyAnswerer implements Answer {
		public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
			MockJspWriter writer = (MockJspWriter) invocationOnMock.getArguments()[0];
			writer.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			return null;
		}
	}
}
