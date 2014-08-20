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

package org.kuali.mobility.library.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * A class representing a Library.
 * The campus code of the library should match a campus code available in <code>CampusService</code>
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@NamedQueries({
	// Gets all the libraries ordered by campus code and order
	@NamedQuery(
		name="Library.getLibraries",
		query="SELECT a FROM Library a ORDER BY a.campusCode, a.order ASC "),
	// Gets all the libraries in a list of IDs
	@NamedQuery(
		name="Library.getLibrariesByIds",
		query="SELECT a FROM Library a where a.id IN (:libraryIds) ORDER BY a.campusCode, a.order ASC "),
	// Gets all the campus codes that has libraries
	@NamedQuery(
		name="Library.getCampusWithLibraries",
		query="SELECT DISTINCT l.campusCode FROM Library l ORDER BY l.campusCode ASC "),
	// Gets all the libraries on a campus
	@NamedQuery(
		name="Library.getLibariesForCampus",
		query="SELECT l FROM Library l WHERE l.campusCode = :campusCode ORDER BY l.order ASC ")
	
	
})
@Entity
@Table(name="LIBRARY")
public class Library {
	
	/** Id of the Library */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long id;
	
	/**
	 * Name of the Library
	 */
	@Column(name="NAME" , nullable=false)
	private String name;
	
	/**
	 * Order of the library at a campus level list
	 */
	@Column(name="ORDR")
	private int order;
	
	/**
	 * Campus on which this library resides
	 */
	@Column(name="CAMPUS_CODE")
	private String campusCode;
	
	/** 
	 * Contact details for this library 
	 */
	@OneToOne(optional=false,cascade=CascadeType.ALL)
	@JoinColumn(name="LIBRARY_CONTACT_ID")
	private LibraryContactDetail libraryContactDetail;

	/**
	 * Flag if this library is active
	 */
	@Column(name="ACTIVE")
	private boolean active;

	/**
	 * Gets the id for this <code>Library</code>.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id for this <code>Library</code>.
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name for this <code>Library</code>.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for this <code>Library</code>.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the order for this <code>Library</code>.
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Sets the order for this <code>Library</code>.
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Gets the campusCode for this <code>Library</code>.
	 * @return the campusCode
	 */
	public String getCampusCode() {
		return campusCode;
	}

	/**
	 * Sets the campusCode for this <code>Library</code>.
	 * @param campusCode the campusCode to set
	 */
	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}

	/**
	 * Gets the libraryContactDetail for this <code>Library</code>.
	 * @return the libraryContactDetail
	 */
	public LibraryContactDetail getLibraryContactDetail() {
		return libraryContactDetail;
	}

	/**
	 * Sets the libraryContactDetail for this <code>Library</code>.
	 * @param libraryContactDetail the libraryContactDetail to set
	 */
	public void setLibraryContactDetail(LibraryContactDetail libraryContactDetail) {
		this.libraryContactDetail = libraryContactDetail;
	}

	/**
	 * Gets the active for this <code>Library</code>.
	 * @return the active.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active for this <code>Library</code>.
	 * @param active the active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	


}
