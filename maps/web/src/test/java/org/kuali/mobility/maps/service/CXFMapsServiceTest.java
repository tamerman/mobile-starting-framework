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

package org.kuali.mobility.maps.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.maps.entity.Location;
import org.kuali.mobility.maps.entity.MapsGroup;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Implementation of the CXF Device Service
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class CXFMapsServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(CXFMapsServiceTest.class);
	
	private CXFMapsService cxfService;
	
	@Mock
	private MapsService service;
	@Mock
	private ConfigParamService configParamService;
	
	@Before
    public void setUpClass() throws Exception {
		setCxfService(new CXFMapsService());
		getCxfService().setService(getService());
		getCxfService().setConfigParamService(getConfigParamService());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
    public void testGetBuildings() {
    	MapsGroup testGroup = new MapsGroup();
    	testGroup.setName("testGroup");
    	when(service.getMapsGroupById(any(String.class))).thenReturn(null).thenReturn(testGroup);
    	Object object = getCxfService().getBuildings(null);
    	Object test = new ResponseEntity<String>(HttpStatus.NOT_FOUND).toString();
    	assertTrue("Should have returned 404", object.toString().equals(test.toString()));
    	object = getCxfService().getBuildings("1");
		assertTrue("returned object does not match test object", object.equals(testGroup.toJson()));
    }
    
    @Test
    public void testGet() {
    	Location location = new Location();
		location.setId("1");
		when(service.getLocationById(any(String.class))).thenReturn(null).thenReturn(location);
		Object viewObject = getCxfService().get("0");
		assertTrue("returned class does not match", viewObject.getClass().equals(new ResponseEntity<String>(HttpStatus.NOT_FOUND).getClass()));
		viewObject = getCxfService().get("1");
		assertTrue("returned object does not match test object", viewObject.equals(location.toJson()));
    }
    
    @Test
    public void testGetFoursuareData() {
    	
    }
    
    @Test
    public void testGetFoursquareDataId() {
    	
    }
    
    @Test
	public void testNothing(){
		assertTrue("Nothing is true.", true);
		assertFalse("Nothing is false.", false);
	}

	public MapsService getService() {
		return service;
	}

	public void setService(MapsService service) {
		this.service = service;
	}

	public CXFMapsService getCxfService() {
		return cxfService;
	}

	public void setCxfService(CXFMapsService cxfService) {
		this.cxfService = cxfService;
	}

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}
}
