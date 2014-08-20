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

package org.kuali.mobility.writer.entity;

/**
 * A class definning the permission required by Wapad.
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
public class WriterGroups {

	/**
	 * Users in this group may not place comments
	 */
	public static final String SPAMMER = "writer.spammer";
	
	
	/**
	 * Permission required to be a journalist on the wapad tool.
	 */
	public static final String JOURNALIST = "writer.journalist";
	
	/**
	 * Permission required to be an editor on the wapad tool.
	 */
	public static final String EDITOR		= "writer.editor";
	
	/**
	 * 
	 */
	public static final String PUBLISH		= "writer.publish";
	
	/**
	 * User with admin rights on wapad tool
	 */
	public static final String ADMIN		= "writer.admin";
	
	/**
	 * Gets a group name with an instance appended
	 * @param group
	 * @param instance
	 */
	public static String getGroupName(String group, String instance){
		if (instance != null){
			return group+"."+instance;
		}
		return group;
	}
}
