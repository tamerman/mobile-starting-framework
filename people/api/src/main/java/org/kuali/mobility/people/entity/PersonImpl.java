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


package org.kuali.mobility.people.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class representing a person.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@XmlRootElement(name = "person")
public class PersonImpl implements Serializable, Person {

	private static final long serialVersionUID = -2125754188712894101L;

	/**
	 * Firstname for this <code>Person</code>.
	 */
	private String firstName;

	/**
	 * Lastname for this <code>Person</code>.
	 */
	private String lastName;

	/**
	 * Display name for this <code>Person</code>.
	 */
	private String displayName;

	/**
	 * User name for this <code>Person</code>.
	 */
	private String userName;

	/**
	 * Locations for this <code>Person</code>.
	 */
	private List<String> locations;

	/**
	 * Affiliations for this <code>Person</code>.
	 */
	private List<String> affiliations;

	/**
	 * Departments this <code>Person</code> belongs to.
	 */
	private List<String> departments;

	/**
	 * Email address for this <code>Person</code>.
	 */
	private String email;

	/**
	 * Phone number for this <code>Person</code>.
	 */
	private String phone;

	/**
	 * Address for this <code>Person</code>.
	 */
	private String address;


	/**
	 * Creates a new instance of a <code>PersonImpl</code>
	 */
	public PersonImpl() {
		locations = new ArrayList<String>();
		affiliations = new ArrayList<String>();
		departments = new ArrayList<String>();
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getHashedUserName()
	 */
	@Override
	public String getHashedUserName() {
		return Integer.toString(Math.abs(userName.hashCode()));
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getLastName()
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getUserName()
	 */
	@Override
	public String getUserName() {
		return userName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setDisplayName(java.lang.String)
	 */
	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getPhone()
	 */
	@Override
	public String getPhone() {
		return phone;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setPhone(java.lang.String)
	 */
	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getAddress()
	 */
	@Override
	public String getAddress() {
		return address;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setAddress(java.lang.String)
	 */
	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getLocations()
	 */
	@Override
	public List<String> getLocations() {
		return locations;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setLocations(java.util.List)
	 */
	@Override
	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getAffiliations()
	 */
	@Override
	public List<String> getAffiliations() {
		return affiliations;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setAffiliations(java.util.List)
	 */
	@Override
	public void setAffiliations(List<String> affiliations) {
		this.affiliations = affiliations;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#getDepartments()
	 */
	@Override
	public List<String> getDepartments() {
		return departments;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.People#setDepartments(java.util.List)
	 */
	@Override
	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}
}
