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

package org.kuali.mobility.xsl.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.xsl.entity.Xsl;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.orm.jpa.JpaUnitils;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@JpaEntityManagerFactory(persistenceUnit="mdot")
public class XslDaoImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(XslDaoImplTest.class);

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private XslDao dao;

	private static final String[] TEST_CODE = {"AA","BB","CC","DD"};
	private static final String[] TEST_VALUE = {"TEST_VALUE_A","TEST_VALUE_B","TEST_VALUE_C",null};

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		setDao(new XslDaoImpl());
		JpaUnitils.injectEntityManagerInto(getDao());
	}

	@Test
	public void testXslDao() {
		List<Xsl> xsls = new ArrayList<Xsl>();

		Long badId = getDao().saveXsl(null);
		assertTrue("Saved a null object. How is that possible?",badId==null);

		int i = 0;
		for( String s : TEST_CODE) {
			Xsl xsl = new Xsl();
			xsl.setCode(s);
			xsl.setValue(TEST_VALUE[i]);
			assertTrue("XSL ID not null and should have been.", xsl.getXslId() == null);
			Long id = getDao().saveXsl(xsl);
			assertTrue("XSL ID is null and should not have been, failed to save.", id != null );
			i++;
			List<Xsl> crossCheckList = getDao().findAllXsl();
			assertTrue("Could not find Xsl in database and should have.", crossCheckList != null && crossCheckList.size() > 0);
			assertTrue("Failed to find expected number of results in DB. "+crossCheckList.size()+" != "+i,crossCheckList.size() == i);
			xsls.add(xsl);
		}

		Xsl lookup = getDao().findXslByCode(xsls.get(1).getCode());
		assertTrue("Failed to find object by code",lookup != null);
		assertTrue("Failed to find correct object by code",lookup.getCode().equals(xsls.get(1).getCode()));

		lookup = null;
		lookup = getDao().findXslById(xsls.get(2).getXslId());

		assertTrue("Failed to find object by id",lookup != null);
		assertTrue("Failed to find correct object by code",lookup.getXslId().equals(xsls.get(2).getXslId()));

		for( Xsl xsl : xsls ) {
			getDao().deleteXslById(xsl.getXslId());
		}

		List<Xsl> crossCheckList = getDao().findAllXsl();
		assertTrue("Objects remain in the db when there should be none.",crossCheckList==null || crossCheckList.isEmpty());
	}


	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public XslDao getDao() {
		return dao;
	}

	public void setDao(XslDao dao) {
		this.dao = dao;
	}
}
