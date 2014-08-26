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

package org.kuali.mobility.security.user.api;

import org.kuali.mobility.security.group.api.Group;

import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface User {
	boolean isPublicUser();

	Long getId();

	void setLoginName(String loginName);

	String getLoginName();

	void setPassword(String password);

	String getPassword();

	String getDisplayName();

	void setDisplayName(String displayName);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	void invalidateUser();

	void setRequestURL(String url);

	String getRequestURL();

	boolean isMember(String groupName);

	String getViewCampus();

	void setViewCampus(String viewCampus);

	String getEmail();

	void setEmail(String email);

	List<Group> getGroups();

	void setGroups(List<Group> groups);

	void addGroup(Group group);

	boolean removeGroup(Group group);

	boolean attributeExists(String key, String value);

	void addAttribute(String name, String value);

	void clearAttribute(String name);

	List<UserAttribute> getAttribute(String name);

	List<String> getAttributeNames();

	List<UserAttribute> getAttributes();

	void setAttributes(List<UserAttribute> attributes);

	boolean removeAttribute(String name, String value);

	boolean removeAttribute(String name);

	void clearAttributes();

	/**
	 * Gets the user's preferred language.
	 * This is not the language the UI is running with, this
	 * is the language the user wishes to use for external communication,
	 * for example, emails, and push notifications.
	 */
	String getPreferredLanguage();

	/**
	 * Sets the user's preferred language.
	 * This is not the language the UI is running with, this
	 * is the language the user wishes to use for external communication,
	 * for example, emails, and push notifications.
	 *
	 * @param language The language to use.
	 */
	void setPreferredLanguage(String language);
}
