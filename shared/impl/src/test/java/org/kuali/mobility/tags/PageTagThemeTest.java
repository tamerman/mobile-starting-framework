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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;

import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.*;
/**
 * Created by charl on 2014/04/08.
 */
public class PageTagThemeTest {
    private static final Logger LOG = LoggerFactory.getLogger(PageTagThemeTest.class);
    private static MockServletContext mockServletContext;
    private PageContext mockPageContext;
    private PageTag tag;

    @BeforeClass
    public static void init() {
        mockServletContext = new MockServletContext();
        String configLocations = "classpath:ThemeTestSpringBeans.xml";
        mockServletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocations);
        ContextLoader loader = new ContextLoader();
        loader.initWebApplicationContext(mockServletContext);
    }

    @Before
    public void setup() throws Exception {
        mockPageContext = new MockPageContext(mockServletContext, new MockHttpServletRequest(), new MockHttpServletResponse());
        tag = new PageTag();
        tag.setAppcacheEnabled("false");
        tag.setJspContext(mockPageContext);
        tag.setJspBody(new JspFragment() {
            @Override public void invoke(Writer out) throws JspException, IOException {}
            @Override public JspContext getJspContext() {return null;}
        });
    }

    @Test
    public void testDefaultTheme() throws Exception{
        tag.doTag();
        String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertFalse("Output not expected to contain a theme", output.contains("css/theme-"));
    }

    @Test
    public void testThemeA() throws Exception{
        tag.setTheme("testa");
        tag.doTag();
        String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertTrue("Output expected to reference themea", output.contains("css/theme-testa.css"));
    }

    @Test
    public void testThemeZ() throws Exception{
        tag.setTheme("testz");
        tag.doTag();
        String output = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertFalse("Output NOT expected to reference themez", output.contains("css/theme-testz.css"));
    }



}
