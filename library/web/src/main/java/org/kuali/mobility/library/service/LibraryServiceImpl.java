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

package org.kuali.mobility.library.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.library.dao.LibraryDao;
import org.kuali.mobility.library.entity.Library;
import org.kuali.mobility.library.entity.LibraryHour;
import org.kuali.mobility.library.entity.LibraryHourPeriod;
import org.kuali.mobility.library.entity.LibraryHourSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation of the <code>LibraryService</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
public class LibraryServiceImpl implements LibraryService {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(LibraryServiceImpl.class);

	/**
	 * A reference to the <code>CacheManager</code>.
	 */
	//@Autowired
	//private CacheManager cacheManager;

	/**
	 * Data Access Object for Libraries.
	 */
	@Autowired
	@Qualifier("libraryDao")
	private LibraryDao dao;


	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#getLibraries()
	 */
	@Override
	//@Cacheable(value="libraryCache")
	public List<Library> getLibraries() {
		return dao.getLibraries();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#getLibrary(long)
	 */
	@Override
	//@Cacheable(value="libraryCache", key="#libraryId")  //TODO: Sort out this caching issue with optimistic locking.
	public Library getLibrary(long libraryId) {
		Library lib = dao.getLibrary(libraryId);
		// TODO sort the hoursets and hours
		return lib;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#saveLibrary(org.kuali.mobility.library.entity.Library)
	 */
	@Override
	public Library saveLibrary(Library library) {
		this.clearLibraryCache();
		return this.dao.saveLibrary(library);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#getDisplayableHoursSet(org.kuali.mobility.library.entity.LibraryHourSet)
	 */
	@Override
	//@Cacheable(value="libraryCache", key="#hourSet")
	public LibraryHourSet getDisplayableHoursSet(LibraryHourSet hourSet) {
		List<LibraryHour> returnHours = new ArrayList<LibraryHour>(); // LinkedHashSet for we need to order preserved
		List<LibraryHour> hours = new ArrayList<LibraryHour>(hourSet.getHours());
		LibraryHour lh;
		int weekSpan = dayOfWeekSpan(hours);
		int index = 0;
		if (weekSpan >= 4) { // At least till Thursday
			lh = new LibraryHour();
			lh.setDisplayLabel("library.hoursSpan." + weekSpan);
			lh.setFromTime(hours.get(0).getFromTime());
			lh.setToTime(hours.get(0).getToTime());
			lh.setDayOfWeek(hours.get(0).getDayOfWeek());
			returnHours.add(lh);
			index = weekSpan;
		} else {
			index = 0;
		}
		for (; index < hours.size(); index++) {
			lh = new LibraryHour();
			lh.setDisplayLabel("library.hours." + (index + 1));
			lh.setFromTime(hours.get(index).getFromTime());
			lh.setToTime(hours.get(index).getToTime());
			lh.setDayOfWeek(hours.get(index).getDayOfWeek());
			returnHours.add(lh);
		}
		LibraryHourSet lhs = new LibraryHourSet();
		lhs.setPeriod(hourSet.getPeriod());
		lhs.setHours(returnHours);
		return lhs;
	}

	/**
	 * Figures out whether the opening and closing time is the same spanning a
	 * few days so it does not have to happen in the jsp
	 * Just check Monday (0) to Friday (4) if the from and to are the same
	 * returning the range of how many it share the same times.
	 *
	 * @param libraryHour
	 * @return The index of the last day in the week - 6 = Sunday
	 */
	private final int dayOfWeekSpan(List<LibraryHour> libraryHours) {
		int count = 0;
		LibraryHour lh = new LibraryHour();
		if (libraryHours != null) {
			for (LibraryHour libHour : libraryHours) {
				if (count > 0) {
					if ((libHour.getFromTime() == null || libHour.getToTime() == null)) {
						return count;
					}
					if ((libHour.getFromTime() != null && libHour.getToTime() != null)) {
						if (lh.getFromTime() == null || lh.getToTime() == null) {
							return count;
						}
						if (lh.getFromTime().compareTo(libHour.getFromTime()) != 0
								|| (lh.getToTime().compareTo(libHour.getToTime()) != 0)) {
							return count;
						}
					}
				}
				count++;
				lh = libHour;
				if (count == 7) {
					break; // Don't go past Sunday
				}
			}
		}
		return count; // Day of week to index
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#getLibrariesByCampus()
	 */
	@Override
	//@Cacheable(value="libraryCache", key="#campusCode")
	public Map<String, List<Library>> getLibrariesByCampus() {

		Map<String, List<Library>> libraryMap = new HashMap<String, List<Library>>();

		// Get campuses with libraries
		List<String> campuses = this.dao.getCampusWithLibraries();
		if (campuses == null) {
			return libraryMap; // Map is still empty
		}

		// Loop through each campus and add their libraries to a map
		for (String campus : campuses) {
			// The list should not be null, since we queried for campuses with libraries
			List<Library> libraries = this.dao.getLibariesForCampus(campus);
			libraryMap.put(campus, libraries);
		}
		return libraryMap;
	}

	// evicts the cache is called from the save method.
	//@CacheEvict(value = "libraryCache", allEntries=true)
	public boolean clearLibraryCache() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#getLibraryHourSets(long)
	 */
	@Override
	public List<LibraryHourSet> getLibraryHourSets(long libraryId) {
		return this.dao.getLibraryHourSets(libraryId);
	}


	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#saveLibraryHourSets(org.kuali.mobility.library.entity.LibraryHourSet)
	 */
	public LibraryHourSet saveLibraryHourSets(LibraryHourSet lhs) {
		return this.dao.saveLibraryHourSets(lhs);
	}


	/**
	 * Sets the reference to the <code>LibraryDao</code>.
	 *
	 * @param dao The reference to the <code>LibraryDao</code>.
	 */
	public void setDao(LibraryDao dao) {
		this.dao = dao;
	}

	/**
	 * Gets the reference to the <code>LibraryDao</code>.
	 *
	 * @returns The reference to the <code>LibraryDao</code>.
	 */
	public LibraryDao getDao() {
		return dao;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#getCampusWithLibraries()
	 */
	@Override
	public List<String> getCampusWithLibraries() {
		return this.dao.getCampusWithLibraries();
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.library.service.LibraryService#saveLibraryHourPeriod(org.kuali.mobility.library.entity.LibraryHourPeriod)
	 */
	@Override
	public LibraryHourPeriod saveLibraryHourPeriod(LibraryHourPeriod libraryHourPeriod) {
		return this.dao.saveLibraryHourPeriod(libraryHourPeriod);
	}
}
