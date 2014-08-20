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

package org.kuali.mobility.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class LoginServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImplTest.class);

	private LoginService service;

	@Before
	public void preTest() {
		setService(new LoginServiceImpl());
	}

	@Test
	public void testIsValidUser() {
		boolean response = getService().isValidUser("mitch");
		assertTrue("Failed to find valid user in LoginService.",response);
		response = getService().isValidUser("yoda");
		assertFalse("Found valid user when we should not have.",response);
	}

	@Test
	public void testIsValidLogin() {
		boolean response = getService().isValidLogin("mitch","fae53351b9effc708e764e871bef3119");
		assertTrue("Failed to validate login in LoginService.",response);
		response = getService().isValidLogin("yoda","abcdefghijklmnopqrstuvwxyz");
		assertFalse("Found valid login for invalid user when we should not have.",response);
		response = getService().isValidLogin("nate","abcdefghijklmnopqrstuvwxyz");
		assertFalse("Found valid login for valid user with bad password when we should not have.",response);
	}

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}
}
