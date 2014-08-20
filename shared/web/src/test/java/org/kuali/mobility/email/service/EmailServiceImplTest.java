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

package org.kuali.mobility.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class EmailServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImplTest.class);

    private static ApplicationContext applicationContext;

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/EmailSpringBeans.xml" };
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        EmailServiceImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSendEmail() {
        EmailService service = (EmailService)getApplicationContext().getBean("emailService");
        boolean response = service.sendEmail(null, null, null, null);
        assertTrue( "Email not yet implemented.", !response);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        EmailServiceImplTest.applicationContext = applicationContext;
    }
}
