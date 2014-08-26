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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.people.service.util.GroupTransform;
import org.kuali.mobility.people.service.util.PersonTransform;

/**
 * An implementation of a group
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
//@SuppressWarnings("serial")
@XmlRootElement(name = "group")
public class GroupImpl implements Serializable, Group {

	/**
	 * UUID for this class
	 */
	private static final long serialVersionUID = 5993041300536395824L;

	/**
	 * Distinguished Name
	 */
	private String distinguishedName;

	/**
	 * Display Name
	 */
	private String displayName;

	/**
	 * Descriptions
	 */
	private List<String> descriptions;

	/**
	 * Email
	 */
	private String email;

	/**
	 * Telephone Number
	 */
	private String telephoneNumber;

	/**
	 * Facsimile Telephone Number
	 */
	private String facsimileTelephoneNumber;

	/**
	 * Members
	 */
	//    @XmlElementWrapper
	//    @XmlElement(name = "member")
	private List<PersonImpl> members;

	/**
	 * Owners
	 */
	//    @XmlElementWrapper
	//    @XmlElement(name = "owner")
	private List<PersonImpl> owners;

	/**
	 * Subgroups
	 */
	//    @XmlElementWrapper
	//    @XmlElement(name = "subGroup")
	private List<GroupImpl> subGroups;

	/**
	 * Creates a new instance of a <code>GroupImpl</code>
	 */
	public GroupImpl() {
		descriptions = new ArrayList<String>();
		members = new ArrayList<PersonImpl>();
		owners = new ArrayList<PersonImpl>();
		subGroups = new ArrayList<GroupImpl>();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getHashedDN()
	 */
	@Override
	public String getHashedDN() {
		return Integer.toString(Math.abs(distinguishedName.hashCode()));
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getDN()
	 */
	@Override
	public String getDN() {
		return distinguishedName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getDescriptions()
	 */
	@Override
	public List<String> getDescriptions() {
		return descriptions;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setDN(java.lang.String)
	 */
	@Override
	public void setDN(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setDisplayName(java.lang.String)
	 */
	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setDescriptions(java.util.List)
	 */
	@Override
	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}


	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getTelephoneNumber()
	 */
	@Override
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setTelephoneNumber(java.lang.String)
	 */
	@Override
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getFacsimileTelephoneNumber()
	 */
	public String getFacsimileTelephoneNumber() {
		return facsimileTelephoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setFacsimileTelephoneNumber(java.lang.String)
	 */
	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber) {
		this.facsimileTelephoneNumber = facsimileTelephoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getMembers()
	 */
	@XmlElement
	public List<PersonImpl> getMembers() {
		return members;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setMembers(java.util.List)
	 */
	public void setMembers(List<? extends Person> members) {
		CollectionUtils.collect(members, new PersonTransform(), this.members);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getOwners()
	 */
	@XmlElement
	public List<PersonImpl> getOwners() {
		return owners;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setOwners(java.util.List)
	 */
	public void setOwners(List<? extends Person> owners) {
		CollectionUtils.collect(owners, new PersonTransform(), this.owners);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#getSubGroups()
	 */
	@XmlElement
	public List<GroupImpl> getSubGroups() {
		return subGroups;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.Group#setSubGroups(java.util.List)
	 */
	public void setSubGroups(List<? extends Group> subGroups) {
		CollectionUtils.collect(subGroups, new GroupTransform(), this.subGroups);
	}

}
