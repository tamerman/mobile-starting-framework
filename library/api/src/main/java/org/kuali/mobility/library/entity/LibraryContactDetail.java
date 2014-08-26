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

package org.kuali.mobility.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A class representing the contact details for a library.
 * Each library can have one instance of a <code>LibraryContactDetail</code>.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@NamedQueries({
		// Gets the contact details for a library
		@NamedQuery(
				name = "Library.getContactDetail",
				query = "SELECT a FROM LibraryContactDetail a where a.id = :libraryContactDetailId")
})
@Entity
@Table(name = "LIBRARY_CONTACT_DETAIL")
public class LibraryContactDetail {

	/**
	 * Id of the Library
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private long id;

	/**
	 * Telephone nr for the library
	 */
	@Column(name = "TELEPHONE", length = 25)
	private String telephone;

	/**
	 * Fax no
	 */
	@Column(name = "FAX", length = 25)
	private String fax;

	/**
	 * General desk information
	 */
	@Column(name = "GENERAL_INFO_DESK")
	private String generalInfoDesk = "";

	/**
	 * Email address for the library
	 */
	@Column(name = "EMAIL", length = 300)
	private String email;

	/**
	 * Postal address for the library
	 */
	@Column(name = "POSTAL_ADDR")
	private String postalAddress;

	/**
	 * Physical address for the library
	 */
	@Column(name = "PHYSICAL_ADDR")
	private String physicalAddress;

	/**
	 * Latitude for the library
	 */
	@Column(name = "LATITUDE", length = 25)
	private String latitude;

	/**
	 * Longitude for the library
	 */
	@Column(name = "LONGITUDE", length = 25)
	private String longitude;

	/**
	 * Version
	 */
	@Version
	@Column(name = "VERSION")
	private long version;

	/**
	 * Gets the id for this <code>LibraryContactDetail</code>.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id for this <code>LibraryContactDetail</code>.
	 *
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the telephone for this <code>LibraryContactDetail</code>.
	 *
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Sets the telephone for this <code>LibraryContactDetail</code>.
	 *
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Gets the fax for this <code>LibraryContactDetail</code>.
	 *
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax for this <code>LibraryContactDetail</code>.
	 *
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Gets the generalInfoDesk for this <code>LibraryContactDetail</code>.
	 *
	 * @return the generalInfoDesk
	 */
	public String getGeneralInfoDesk() {
		return generalInfoDesk;
	}

	/**
	 * Sets the generalInfoDesk for this <code>LibraryContactDetail</code>.
	 *
	 * @param generalInfoDesk the generalInfoDesk to set
	 */
	public void setGeneralInfoDesk(String generalInfoDesk) {
		this.generalInfoDesk = generalInfoDesk;
	}

	/**
	 * Gets the email for this <code>LibraryContactDetail</code>.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email for this <code>LibraryContactDetail</code>.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the postalAddress for this <code>LibraryContactDetail</code>.
	 *
	 * @return the postalAddress
	 */
	public String getPostalAddress() {
		return postalAddress;
	}

	/**
	 * Sets the postalAddress for this <code>LibraryContactDetail</code>.
	 *
	 * @param postalAddress the postalAddress to set
	 */
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * Gets the physicalAddress for this <code>LibraryContactDetail</code>.
	 *
	 * @return the physicalAddress
	 */
	public String getPhysicalAddress() {
		return physicalAddress;
	}

	/**
	 * Sets the physicalAddress for this <code>LibraryContactDetail</code>.
	 *
	 * @param physicalAddress the physicalAddress to set
	 */
	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	/**
	 * Gets the latitude for this <code>LibraryContactDetail</code>.
	 *
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude for this <code>LibraryContactDetail</code>.
	 *
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude for this <code>LibraryContactDetail</code>.
	 *
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude for this <code>LibraryContactDetail</code>.
	 *
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the version for this <code>LibraryContactDetail</code>.
	 *
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * Sets the version for this <code>LibraryContactDetail</code>.
	 *
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}


}
