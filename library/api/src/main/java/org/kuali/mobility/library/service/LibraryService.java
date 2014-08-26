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

import java.util.List;
import java.util.Map;

import org.kuali.mobility.library.dao.LibraryDao;
import org.kuali.mobility.library.entity.Library;
import org.kuali.mobility.library.entity.LibraryHourPeriod;
import org.kuali.mobility.library.entity.LibraryHourSet;

/**
 * Service
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface LibraryService {

	/**
	 * Sets the reference to the <code>LibraryDao</code>.
	 *
	 * @param dao The reference to the <code>LibraryDao</code>.
	 */
	public void setDao(LibraryDao dao);

	/**
	 * Gets the reference to the <code>LibraryDao</code>.
	 *
	 * @return The reference to the <code>LibraryDao</code>.
	 */
	public LibraryDao getDao();

	/**
	 * Gets all the active libraries
	 *
	 * @return
	 */
	public List<Library> getLibraries();

	/**
	 * Gets a library for the specified id
	 *
	 * @param libraryId Id of the library to retrieve.
	 * @return The library instance
	 */
	public Library getLibrary(long libraryId);

	/**
	 * Returns a list of campus codes of campuses that have libraries
	 *
	 * @return
	 */
	public List<String> getCampusWithLibraries();

	/**
	 * Gets the hour sets for a library
	 *
	 * @param libraryId
	 * @return
	 */
	public List<LibraryHourSet> getLibraryHourSets(long libraryId);

	/**
	 * Persists the state of a library
	 *
	 * @param library The library to persist.
	 * @return A updated version of the library as persisted.
	 */
	public Library saveLibrary(Library library);


	/**
	 * Saves a library hour period
	 *
	 * @param libraryHourPeriod
	 * @return
	 */
	public LibraryHourPeriod saveLibraryHourPeriod(LibraryHourPeriod libraryHourPeriod);


	/**
	 * Takes an hourset and generates a new hourset which can be displayed on the ui
	 */
	public LibraryHourSet getDisplayableHoursSet(LibraryHourSet hourSet);

	/**
	 * Gets the libraries in a map per campus sorted alphabetically on campus code
	 *
	 * @return
	 */
	public Map<String, List<Library>> getLibrariesByCampus();

	/**
	 * Saves a LibraryHourSet
	 *
	 * @param lhs
	 * @return
	 */
	public LibraryHourSet saveLibraryHourSets(LibraryHourSet lhs);
}
