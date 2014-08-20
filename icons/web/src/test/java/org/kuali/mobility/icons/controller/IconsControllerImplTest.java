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

package org.kuali.mobility.icons.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.icons.controllers.WebIconsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for the IconsController
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:/IconsSpringBeans.xml")
public class IconsControllerImplTest {
    private static final String TEST1_NAME = "Tool1Book";
    private static final String TEST1_THEME = "campus1";


    @Autowired
    private WebIconsController webIconsController;

    /**
     * Test that getting an icon does:
     * - Not throw an exception
     * - Response has content size set
     * - Response has last modified set
     */
    @Test
    @DirtiesContext
    public void testDownloadFileResponse() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        webIconsController.getImageThemeSize(TEST1_NAME, TEST1_THEME, 50, request, response);
        assertEquals("Response code was not successfull", HttpStatus.OK.value(), response.getStatus());
        assertTrue("Expected to have content size set, but did not", response.getContentLength() > 0);
        assertTrue("Last-Modified header expected to be set, but is not", response.getHeader("Last-Modified") != null);
    }

    /**
     * Test that when sending a matching Last-Modified header return a 304 response code
     * @throws IOException
     */
    @Test
    @DirtiesContext
    public void testLastModified() throws IOException {

        MockHttpServletResponse response = new MockHttpServletResponse();

        webIconsController.getImageThemeSize(TEST1_NAME, TEST1_THEME, 50, new MockHttpServletRequest(), response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("If-Modified-Since", response.getHeaderValue("Last-Modified"));
        response = new MockHttpServletResponse();

        webIconsController.getImageThemeSize(TEST1_NAME, TEST1_THEME, 50, request, response);
        assertEquals("Response code was not 304", HttpStatus.NOT_MODIFIED.value(), response.getStatus());
    }
}
