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

package org.kuali.mobility.conference.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attendee")
public class Attendee implements Serializable, Comparable<Attendee> {

	private static final long serialVersionUID = -2826816981140315473L;

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String workPhone;
	private String cellPhone;
	private String institution;
	private String campus;
	private String title;
	private String workAddress1;
	private String workAddress2;
	private String workCity;
	private String workState;
	private String workZip;
	private String country;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getCampus() {
		return campus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getWorkAddress1() {
		return workAddress1;
	}

	public void setWorkAddress1(String workAddress1) {
		this.workAddress1 = workAddress1;
	}

	public String getWorkAddress2() {
		return workAddress2;
	}

	public void setWorkAddress2(String workAddress2) {
		this.workAddress2 = workAddress2;
	}

	public String getWorkCity() {
		return workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
	}

	public String getWorkZip() {
		return workZip;
	}

	public void setWorkZip(String workZip) {
		this.workZip = workZip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int compareTo(Attendee that) {
		if (that == null) {
			return -1;
		}

		if (this.getLastName() == null && that.getLastName() == null) {
			return -1;
		}

		if (this.getLastName() != null && that.getLastName() == null) {
			return -1;
		}

		if (this.getLastName() == null && that.getLastName() != null) {
			return 1;
		}

		int lastNameCompare = this.getLastName().compareTo(that.getLastName());
		if (lastNameCompare == 0) {

			if (this.getFirstName() == null && that.getFirstName() == null) {
				return -1;
			}

			if (this.getFirstName() != null && that.getFirstName() == null) {
				return -1;
			}

			if (this.getFirstName() == null && that.getFirstName() != null) {
				return 1;
			}

			return this.getFirstName().compareTo(that.getFirstName());
		}

		return lastNameCompare;
	}

}
