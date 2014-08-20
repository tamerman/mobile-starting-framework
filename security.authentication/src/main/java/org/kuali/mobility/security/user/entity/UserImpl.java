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

package org.kuali.mobility.security.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserAttribute;

@NamedQueries({
	@NamedQuery(
		name = "User.lookupById",
		query = "select u from User u where u.id = :id"
	),
	@NamedQuery(
		name = "User.lookupByLoginName",
		query = "select u from User u where u.loginName = :loginName"
	),
	@NamedQuery(
		name = "User.lookupAllUsers",
		query = "select u from User u"
	)
})
/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Entity(name="User")
@Table(name="KME_USER_T")
public class UserImpl implements User {

	public static final String EMPTY = "";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long id;

	@Column(name="LOGIN_NM",unique = true)
	private String loginName;

	@Column(name="PASSWORD_X")
	private String password;

	@Column(name="DISPLAY_NM")
	private String displayName;

	@Column(name="FIRST_NM")
	private String firstName;

	@Column(name="LAST_NM")
	private String lastName;

	@Column(name="URL")
	private String requestURL;

	@Column(name="CAMPUS_ID")
	private String viewCampus;

	@Column(name="EMAIL_ADDR_X")
	private String email;

	/**
	 * This is not the language the UI is running with, this
	 * is the language the user wishes to use for external communication,
	 * for example, emails, and push notifications.
	 */
	@Column(name="PREF_LANG", length = 10)
	private String preferredLanguage;

	// Currently managing this manually in the dao.
	@Transient
	private List<Group> groups;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<UserAttribute> attributes;

	public UserImpl() {
		this.setAttributes(new ArrayList<UserAttribute>());
		this.setGroups(new ArrayList<Group>());
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String principalName) {
		this.loginName = principalName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getViewCampus() {
		return viewCampus;
	}

	public void setViewCampus(String viewCampus) {
		this.viewCampus = viewCampus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void invalidateUser() {
		this.setLoginName(null);
		this.setDisplayName(null);
		this.setId(null);
		this.setEmail(null);
		this.setFirstName(null);
		this.setLoginName(null);
		this.setGroups(new ArrayList<Group>());
		this.setAttributes(new ArrayList<UserAttribute>());
		this.setPassword(null);
	}

	@Override
	public boolean isPublicUser() {
		if( this.getLoginName() == null
			|| EMPTY.equalsIgnoreCase(this.getLoginName())
			|| this.getLoginName().startsWith(AuthenticationConstants.PUBLIC_USER) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean isMember(String groupName) {
		boolean isMember = false;
		if( getGroups() != null ) {
			for( Group g : getGroups() ) {
				if( groupName.equalsIgnoreCase(g.getName()) ) {
					isMember = true;
					break;
				}
			}
		}
		return isMember;
	}

	@Override
	public void addGroup(Group group) {
		getGroups().add(group);
	}

	@Override
	public boolean removeGroup(Group group) {
		boolean didRemove = false;
		if(getGroups().contains(group)) {
			getGroups().remove(group);
		}
		return didRemove;
	}

	/*
	Methods for managing user attributes.
	 */
	@Override
	public boolean attributeExists(String key, String value) {
		boolean attributeExists = false;
		if( !getAttributes().isEmpty() ) {
			for( UserAttribute ua : getAttributes() ) {
				if( key.equals(ua.getAttributeName())
					&& value.equals(ua.getAttributeValue())) {
					attributeExists = true;
				}
			}
		}
		return attributeExists;
	}

	@Override
	public void addAttribute(String key, String value) {
		key = key.toUpperCase();

		if( !attributeExists(key,value) ) {
			UserAttribute attribute = new UserAttribute();
			attribute.setAttributeName(key);
			attribute.setAttributeValue(value);
			attribute.setUser(this);
			this.attributes.add(attribute);
		}
	}

	@Override
	public void clearAttribute(String key) {
		key = key.toUpperCase();
		for( UserAttribute ua : getAttributes() ) {
			if( key.equals(ua.getAttributeName()) ) {
				ua.setAttributeValue(null);
			}
		}
	}

	@Override
	public List<UserAttribute> getAttribute(String name) {
		name = name.toUpperCase();
		List<UserAttribute> attributesForName = new ArrayList<UserAttribute>();
		for( UserAttribute ua : getAttributes() ) {
			if( name.equals(ua.getAttributeName()) ) {
				attributesForName.add(ua);
			}
		}
		return attributesForName;
	}

	@Override
	public List<String> getAttributeNames() {
		List<String> attributeNames = new ArrayList<String>();
		for( UserAttribute ua : getAttributes() ) {
			if( attributeNames.contains(ua.getAttributeName()) ) {
				continue;
			} else {
				attributeNames.add(ua.getAttributeName());
			}
		}
		return attributeNames;
	}

	@Override
	public List<UserAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<UserAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void clearAttributes() {
		attributes.clear();
	}

	@Override
	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	@Override
	public void setPreferredLanguage(String language) {
		this.preferredLanguage = language;
	}

	@Override
	public boolean removeAttribute(String name, String value) {
		boolean success = false;
		if( name != null ) {
			name = name.toUpperCase();
			List<UserAttribute> attributesToRemove = new ArrayList<UserAttribute>();
			for(UserAttribute ua : getAttributes()) {
				if(name.equalsIgnoreCase(ua.getAttributeName())) {
					if(null==value) {
						attributesToRemove.add(ua);
					} else if (value.equalsIgnoreCase(ua.getAttributeValue())) {
						attributesToRemove.add(ua);
					}
				}
			}
			if(!attributesToRemove.isEmpty()) {
				success = attributes.removeAll(attributesToRemove);
			}
		}
		return success;
	}

	@Override
	public boolean removeAttribute(String name) {
		return removeAttribute(name,null);
	}

//	@Override
//	public Set<String> replaceAttribute(String key, Set<String> value) {
//		key = key.toUpperCase();
//		return attributes.put(key, value);
//	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
