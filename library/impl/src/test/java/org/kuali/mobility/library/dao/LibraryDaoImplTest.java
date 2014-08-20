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

package org.kuali.mobility.library.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.library.entity.Library;
import org.kuali.mobility.library.entity.LibraryContactDetail;
import org.kuali.mobility.library.entity.LibraryHourPeriod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for the Library Data Access Object
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:/LibrarySpringBeans.xml")
public class LibraryDaoImplTest {

	/** A reference to a logger */
	private static final Logger LOG = LoggerFactory.getLogger(LibraryDaoImplTest.class);
	
	private static final String TEST_EMAIL 	= "library@mail.com";
	private static final String TEST_NAME 	= "Test library";
	private static final String CAMPUS		= "IU";

	/**
	 * A reference to the <code>LibraryDao</code>
	 */
	@Autowired
	@Qualifier("libraryDao")
	private LibraryDao dao;
	
	/**
	 * We first expect that we can save a library period
	 */
	@Test
	@DirtiesContext
	public void testSavePeriod(){
		LibraryHourPeriod lhp = new LibraryHourPeriod();
		lhp.setOrder(1);
		lhp.setLabel("library.recess");
		try{
			dao.saveLibraryHourPeriod(lhp);
		}catch(Exception e){
			fail("An exception occurred while saving the library hour : " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Test that the object has an ID when saved
	 */
	@Test
	@DirtiesContext
	public void testSavePeriod2(){
		LibraryHourPeriod lhp = new LibraryHourPeriod();
		lhp.setOrder(1);
		lhp.setLabel("library.recess");
		lhp = dao.saveLibraryHourPeriod(lhp);
		assertFalse("Expected the saved entity to have an ID", lhp.getId() == null);
	}

	/**
	 * When we save a library we expect the primary key to be set
	 */
	@Test
	@DirtiesContext
	public void testSaveLibrary(){
		Library lib = createLibrary();
		lib = dao.saveLibrary(lib);
		assertFalse("Expected the saved entity to have an ID", lib.getId() == null);
	}
	
	/**
	 * Test saving a library.
	 * We expect:
	 *  - the library to be retrievable
	 *  - the library to have the same name
	 *  - the contact details to still be in tact
	 *  - the contact details email address to still be the same
	 */
	@Test
	@DirtiesContext
	public void testSaveAndRetrieveLibrary(){
		Library lib = createLibrary();
		lib = dao.saveLibrary(lib);
		long libId = lib.getId();
		
		lib = dao.getLibrary(libId);
		assertNotNull("Expected to be able to retrieve a saved library", lib);
		assertEquals("Expected library name to be the same as when saved", TEST_NAME, lib.getName());
		LibraryContactDetail contactDetails = lib.getLibraryContactDetail();
		assertNotNull("Expected library contact details to still be in tact", contactDetails);
		assertEquals("Expected library contact email to be the same as when saved", TEST_EMAIL, contactDetails.getEmail());
	}
	
	/**
	 * Test saving libraries by campus code.
	 * We expect to receive the libraries we saved per campus code
	 */
	@Test
	@DirtiesContext
	public void testGetLibrariesPerCampus(){
		dao.saveLibrary(createLibrary());
		dao.saveLibrary(createLibrary());
		dao.saveLibrary(createLibrary());
		
		Library lib = createLibrary();
		lib.setCampusCode("random");
		dao.saveLibrary(lib);
		
		List<Library> libraries =  dao.getLibariesForCampus(CAMPUS);
		assertNotNull("Did not expect to receive a null object", libraries);
		assertEquals("Did not get the expected number of libraries for the campus", 3, libraries.size());
		
		// Now check that each library is realy from the expected campus
		for(Library library : libraries){
			assertEquals("Library campus is not what is expected", CAMPUS, library.getCampusCode());
		}
	}
	
	
	/**
	 * Test that we get the libraries in the order we expected
	 */
	@Test
	@DirtiesContext
	public void testLibraryOrder(){
		Library lib;
		final int COUNT = 5;
		for(int i = 0 ; i < COUNT ; i++){
			lib = createLibrary();
			lib.setOrder(i);
			dao.saveLibrary(lib);
		}
		
		List<Library> libraries =  dao.getLibariesForCampus(CAMPUS);
		assertEquals("Did not get the expected number of libraries for the campus", COUNT, libraries.size());
		for(int i = 0 ; i < COUNT ; i++){
			lib = libraries.get(i);
			assertEquals("Library order is not what expected", i , lib.getOrder());
		}
		
	}
	
	/**
	 * Create a test library
	 */
	private final Library createLibrary(){
		Library lib = new Library();
		lib.setLibraryContactDetail(createContactForLibrary());
		lib.setCampusCode(CAMPUS);
		lib.setName(TEST_NAME);
		lib.setOrder(1);
		return lib;
	}
	
	
	/**
	 * Create contact information
	 */
	private final LibraryContactDetail createContactForLibrary(){
		LibraryContactDetail contact = new LibraryContactDetail();
		contact.setEmail(TEST_EMAIL);
		contact.setFax("012 345 6879");
		contact.setGeneralInfoDesk("021 546 5555");
		contact.setPhysicalAddress("Exactly where you want us to be");
		contact.setPostalAddress("Exactly where you want to post to");
		contact.setTelephone("012 345 6879");
		return contact;
	}
}
