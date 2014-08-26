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

package org.kuali.mobility.maps.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.maps.entity.Location;
import org.kuali.mobility.maps.entity.MapsGroup;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:KMESpringBeans.xml")
public class MapsServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(MapsServiceImplTest.class);

	@Resource(name = "mapsService")
	private MapsService service;

	@Before
	public void setUpClass() throws Exception {
	}

	@Test
	public void testGetMapsGroupById() {
		MapsGroup mapGroup = getService().getMapsGroupById("ZZZZ");
		assertTrue("Should not find a map group with ID ZZZZ", mapGroup == null);
		mapGroup = getService().getMapsGroupById("UA");
		assertTrue("Should find a map group with ID UA", mapGroup != null);
	}

	@Test
	public void testGetLocationById() {
		Location location = getService().getLocationById("ZZZZ");
		assertTrue("Should not find a location with ID ZZZZ", location == null);
		location = getService().getLocationById("SB");
		assertTrue("Should find a location with ID SB", location != null);
	}

	public MapsService getService() {
		return service;
	}

	public void setService(MapsService service) {
		this.service = service;
	}
}
