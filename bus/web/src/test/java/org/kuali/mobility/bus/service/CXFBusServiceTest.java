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

package org.kuali.mobility.bus.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Implementation of the CXF Device Service
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class CXFBusServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger( CXFBusServiceTest.class );
	
    @Mock
    private BusService busService;
	
    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @Before
    public void preTest() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetBusLocations(){
    	
    }
    
    @Test
    public void testGetBusRoutes(){
    	
    }

    @Test
    public void testGetBusStops(){
    	
    }

    @Test
    public void testGetBusAlerts(){
//    	CXFBusService cbs = new CXFBusService();
//    	List<BusAlert> alerts = new ArrayList<BusAlert>();    	
//    	when(this.busService.getDao().getBusAlerts()).thenReturn(alerts);

    
    }

    
	@Test
	public void testNothing(){
		assertTrue("Nothing is true.", true);
		assertFalse("Nothing is false.", false);
	}
    
	public BusService getBusService() {
		return busService;
	}

	public void setBusService(BusService busService) {
		this.busService = busService;
	}	
}
