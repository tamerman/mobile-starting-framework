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

package org.kuali.mobility.push.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @deprecated This class is moved to an external project "push-service"
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Deprecated
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class CXFDeviceServiceTest {

	private static final String OK_RESULTS = "{\"status\":\"OK\"}";
	private CXFDeviceService cds;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		cds = new CXFDeviceService();
	}

	@Test
	public void testNothing(){
		assertTrue("Nothing is true.", true);
		assertFalse("Nothing is false.", false);
	}
	
	@Test
	public void testPingGet(){
		CXFDeviceService cds = new CXFDeviceService();
		String returned = cds.pingGet();
		assertTrue("PingGet returned improper results.", OK_RESULTS.equals(returned));
	}

	@Test
	public void testPingPost(){
		CXFDeviceService cds = new CXFDeviceService();
		String returned = cds.pingPost();
		assertTrue("PingPost returned improper results.", OK_RESULTS.equals(returned));
	}

	@Test
	public void testDevicesByKeyword_NoDevices(){
//		CXFDeviceService cds = new CXFDeviceService();
//		String returned = cds.devicesByKeyword("nameD :i");
//		assertTrue("Should have returned \"No Devices Found\".", "{\"total\":\"No Devices Found\"}".equals(returned));		
	}
	
}
