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

package org.kuali.mobility.computerlabs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.LabGroup;
import org.kuali.mobility.computerlabs.entity.Location;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:ComputerLabsSpringBeans.xml")
public class ComputerLabsDaoImplTest {

	private static final Logger LOG = LoggerFactory.getLogger( ComputerLabsDaoImplTest.class );

    @Resource(name="computerLabInitBean")
	private ComputerLabsInitBean initBean;
    @Resource(name="computerLabDao")
	private ComputerLabsDao dao;

    @Before
    public void setUpTest() {
        getInitBean().loadData();
    }

	@Test
	public void testGetLabGroups() {
		List<? extends LabGroup> labGroups = dao.getLabGroups();
		assertTrue("No lab groups found.", labGroups != null);
		for( LabGroup lg : labGroups ) {
			LOG.debug( "Loaded lab group for "+lg.getName() );
			LOG.debug( lg.getName()+" has "+lg.getLocations().size()+" locations." );
		}
		assertTrue("Lab groups count is not 7.", labGroups.size() == 7 );
		
		dao.setLabGroups(null);
		List<? extends LabGroup> nullLabGroups = dao.getLabGroups();
		assertTrue("Lab groups count is not 0.", nullLabGroups.size() == 0 );
		
	}

	@Test
	public void testGetLabGroup() {
		LabGroup labGroup = dao.getLabGroup("BL");
		assertTrue( "Lab group not loaded for key BL.", labGroup != null );		
	}

	@Test
	public void testGetLocations() {
		List<? extends Location> locations = dao.getLocations("BL");
		assertTrue( "No locations found for group id of BL", locations != null && locations.size() > 0 );
		
		List<? extends Location> nullLocations = dao.getLocations(null);
		assertTrue( "Locations group should not be NULL for group id NULL", nullLocations != null && nullLocations.size() > 0 );
	}

	@Test
	public void testGetLabs() {
		List<? extends Location> locations = dao.getLocations("BL");
		Location testLocation = locations.get(0);
		List<? extends Lab> labs = dao.getLabs( testLocation.getName(), "IN088" );
		assertTrue( "Labs were not loaded for location IN, building code IN088", labs != null && labs.size() > 0 );
	}

	@Test
	public void testGetLab() {
		List<? extends Location> locations = dao.getLocations("BL");
		Location testLocation = locations.get(0);
//		List<? extends Lab> labs = dao.getLabs( testLocation.getName(), "IN088" );
//		for( Lab lab : labs ) {
//			LOG.debug( "Lab UID = "+lab.getLabUID() );
//		}
		Lab lab = dao.getLab("328ebdda457861828eba6e9136f7d2867524b5e0");
		assertTrue( "Lab not loaded for UID XXXX", lab != null && lab.getLab() != null && !lab.getLab().isEmpty() );
		
		Lab fakeLab = dao.getLab("fakeLab");
		assertTrue( "Lab should not be loaded for UID XXXX", fakeLab == null);
	}
	
	@Test
	public void testGetDaoContext() {
		assertTrue("Failed to get dao context",initBean.getDao() == getDao());
	}

	/**
	 * @return the initBean
	 */
	public ComputerLabsInitBean getInitBean() {
		return initBean;
	}

	/**
	 * @param initBean the initBean to set
	 */
	public void setInitBean(ComputerLabsInitBean initBean) {
		this.initBean = initBean;
	}

	/**
	 * @return the dao
	 */
	public ComputerLabsDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(ComputerLabsDao dao) {
		this.dao = dao;
	}
}
