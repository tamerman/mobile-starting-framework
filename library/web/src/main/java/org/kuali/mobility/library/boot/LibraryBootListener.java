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

package org.kuali.mobility.library.boot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.campus.entity.Campus;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.library.entity.Library;
import org.kuali.mobility.library.entity.LibraryContactDetail;
import org.kuali.mobility.library.entity.LibraryHour;
import org.kuali.mobility.library.entity.LibraryHourPeriod;
import org.kuali.mobility.library.entity.LibraryHourSet;
import org.kuali.mobility.library.service.LibraryService;
import org.kuali.mobility.shared.listeners.Bootables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Bootstrap listener for the Library Tool
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
public class LibraryBootListener implements ServletContextListener {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(LibraryBootListener.class);

	private LibraryHourPeriod periodSemester;
	private LibraryHourPeriod periodRecess;

	/**
	 * A reference to the Bootables
	 */
	@Autowired
	private Bootables bootables;

	private LibraryService libraryService;

	/**
	 *
	 */
	public void initialise() {
		bootables.registeredBootable(this);
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		Properties kmeProperties = (Properties) ctx.getBean("kmeProperties");

		if (kmeProperties == null || !"true".equals(kmeProperties.getProperty("library.bootstrap"))) {
			return;
		}

		// Get required services
		CampusService campusService = (CampusService) ctx.getBean("campusService");
		this.libraryService = (LibraryService) ctx.getBean("libraryService");
		//  MOBILITY -627 Creating default details through import.sql so commenting static data 
//		createHourPeriods();
//		// Get available campusses
//		List<Campus> campusses = campusService.getCampuses();
//		for(Campus campus : campusses){
//			createLibrariesForCampus(campus);
//		}

		LOG.debug("We should be doing some bootup stuff here!");
	}

	private void createHourPeriods() {
		LibraryHourPeriod hp = new LibraryHourPeriod();
		hp.setLabel("library.semester");
		hp.setOrder(1);
		this.periodSemester = this.libraryService.saveLibraryHourPeriod(hp);

		hp = new LibraryHourPeriod();
		hp.setLabel("library.recess");
		hp.setOrder(2);
		this.periodRecess = this.libraryService.saveLibraryHourPeriod(hp);

	}

	/**
	 * Creates 3 sample libraries for the specified campus
	 *
	 * @param campus
	 * @param libService
	 */
	private final void createLibrariesForCampus(Campus campus) {
		for (int i = 1; i <= 3; i++) {
			Library lib = new Library();
			lib.setOrder(i);
			lib.setActive(true);
			lib.setCampusCode(campus.getCode());
			lib.setLibraryContactDetail(createContactForLibrary(i));
			lib.setName("library" + i + " @ " + campus.getName());
			lib = this.libraryService.saveLibrary(lib);
			createHourSets(lib);
		}
	}

	/**
	 * Create contact information
	 *
	 * @param idx
	 * @return
	 */
	private final LibraryContactDetail createContactForLibrary(int idx) {
		LibraryContactDetail contact = new LibraryContactDetail();
		contact.setEmail("library" + idx + "@example.com");
		contact.setFax("012 345 6879");
		contact.setGeneralInfoDesk("021 546 5555");
		contact.setPhysicalAddress("Exactly where you want us to be");
		contact.setPostalAddress("Exactly where you want to post to");
		contact.setTelephone("012 345 6879");
		return contact;
	}

	private final void createHourSets(Library l) {
		LibraryHourSet hs = new LibraryHourSet();
		hs.setLibraryId(l.getId());
		hs.setPeriod(periodRecess);
		hs = this.libraryService.saveLibraryHourSets(hs);
		hs.setHours(getHours(hs));
		this.libraryService.saveLibraryHourSets(hs);

		hs = new LibraryHourSet();
		hs.setLibraryId(l.getId());
		hs.setPeriod(periodSemester);
		hs = this.libraryService.saveLibraryHourSets(hs);
		hs.setHours(getHours(hs));
		this.libraryService.saveLibraryHourSets(hs);
	}

	/**
	 * Get a list of library hours
	 *
	 * @return
	 */
	private final List<LibraryHour> getHours(LibraryHourSet hourset) {
		List<LibraryHour> hours = new ArrayList<LibraryHour>(8);
		LibraryHour hour;

		final Calendar openTime = Calendar.getInstance();
		openTime.set(0, 0, 0, 7, 0);

		final Calendar closeTime = Calendar.getInstance();
		closeTime.set(0, 0, 0, 17, 0);

		for (int dow = LibraryHour.DAY_MONDAY; dow <= LibraryHour.DAY_PUBLIC_HOLIDAY; dow++) {
			hour = new LibraryHour();
			hour.setDayOfWeek(dow);
			hour.setFromTime(openTime.getTime());
			hour.setToTime(closeTime.getTime());
			hour.setLibraryHourSet(hourset);
			hours.add(hour);
		}
		return hours;
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
