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

package org.kuali.mobility.admin.dao;

import java.util.List;

import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.Tool;

/**
 * Interface for a contract for actions on a data store for administrative tasks
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public interface AdminDao {
	/**
	 * @return all defined home screens
	 */
	public List<HomeScreen> getAllHomeScreens();
	/**
	 * @param homeScreenId the id of the home screen to retrieve
	 * @return the HomeScreen object matching the id
	 */
	public HomeScreen getHomeScreenById(long homeScreenId);
	/**
	 * @param alias the alias of the home screen
	 * @return the HomeScreen object matching the alias
	 */
	public HomeScreen getHomeScreenByAlias(String alias);
	/**
	 * @param homeScreen the HomeScreen to save
	 * @return the id of the home screen
	 */
	public Long saveHomeScreen(HomeScreen homeScreen);
	/**
	 * @param homeScreenId the id of the home screen to delete
	 */
	public void deleteHomeScreenById(long homeScreenId);
	
	/**
	 * @return all defined Tools
	 */
	public List<Tool> getAllTools();
	/**
	 * @param tool the Tool to save
	 * @return the id of the saved Tool
	 */
	public Long saveTool(Tool tool);
	/**
	 * @param toolId the id of the tool to retrieve
	 * @return the Tool object matching the id
	 */
	public Tool getToolById(long toolId);
	/**
	 * @param toolId the id of the tool to delete
	 */
	public void deleteToolById(Long toolId);
	
}
