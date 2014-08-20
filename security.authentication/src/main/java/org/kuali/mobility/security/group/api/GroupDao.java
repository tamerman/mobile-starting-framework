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

package org.kuali.mobility.security.group.api;

import java.util.List;
import java.util.Map;

import org.kuali.mobility.security.user.api.User;

/**
 * Created with IntelliJ IDEA.
 * User: swansje
 * Date: 9/20/13
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GroupDao {
	// This method can be used to refresh the cached group data.
	List<Group> getGroups();

	Group getGroup(Long id);

	Group getGroup(String name);

	Long saveGroup(Group group);

	Map<String, Group> getGroupMap();

	void setGroupMap(Map<String, Group> groupMap);

	void removeGroup(Group group);
	
	void removeFromGroup(List<User> users, Long groupId);
	
	void addToGroup(List<User> users, Long userId);
}
