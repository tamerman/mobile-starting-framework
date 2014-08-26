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

package org.kuali.mobility.campus.service;

import org.kuali.mobility.campus.entity.Campus;

import java.util.List;

/**
 * Interface for a contract for interacting with Campus objects
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 1.0.0
 */
public interface CampusService {

	/**
	 * @return a list of all available Campus objects
	 */
	public List<Campus> findCampusesByTool(String toolName);

	/**
	 * @return a boolean if this tool needs a different campus selected.  If a tool has no campuses, true is always returned.
	 */
	public boolean needToSelectDifferentCampusForTool(String tool, String campus);

	/**
	 * Sets the list of campuses
	 *
	 * @param campuses Campuses
	 */
	public void setCampuses(List<Campus> campuses);

	/**
	 * Gets all the campuses
	 *
	 * @return
	 */
	public List<Campus> getCampuses();

	/**
	 * Gets the campus with the specified campus code.
	 * Returns null if the campus is not found.
	 *
	 * @param campusCode Campus code
	 * @return The campus.
	 */
	public Campus getCampusByCode(String campusCode);
}
