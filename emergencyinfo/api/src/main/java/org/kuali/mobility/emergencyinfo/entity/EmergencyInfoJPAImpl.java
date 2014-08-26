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

package org.kuali.mobility.emergencyinfo.entity;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
		@NamedQuery(
				name = "EmergencyInfo.findAll",
				query = "select ei from EmergencyInfo ei order by ei.order"
		),
		@NamedQuery(
				name = "EmergencyInfo.findById",
				query = "select ei from EmergencyInfo ei where ei.emergencyInfoId = :id"
		),
		@NamedQuery(
				name = "EmergencyInfo.findByCampus",
				query = "select ei from EmergencyInfo ei where ei.campus like :campus order by ei.order"
		),
		@NamedQuery(
				name = "EmergencyInfo.deleteById",
				query = "delete from EmergencyInfo ei where ei.emergencyInfoId = :id"
		)
})

@Entity(name = "EmergencyInfo")
@Table(name = "KME_EM_INFO_T")
public class EmergencyInfoJPAImpl implements Serializable, EmergencyInfo {

	private static final long serialVersionUID = 8753764116073085733L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long emergencyInfoId;

	@Column(name = "TYP")
	private String type;

	@Column(name = "TTL")
	private String title;

	@Column(name = "LNK")
	private String link;

	@Column(name = "CMPS")
	private String campus;

	@Column(name = "ORDR")
	private int order;

	@Version
	@Column(name = "VER_NBR")
	protected Long versionNumber;

	public EmergencyInfoJPAImpl() {
	}

	@Override
	public Long getVersionNumber() {
		return versionNumber;
	}

	@Override
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Override
	public Long getEmergencyInfoId() {
		return emergencyInfoId;
	}

	@Override
	public void setEmergencyInfoId(Long emergencyInfoId) {
		this.emergencyInfoId = emergencyInfoId;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getLink() {
		return link;
	}

	@Override
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String getCampus() {
		return campus;
	}

	@Override
	public void setCampus(String campus) {
		this.campus = campus;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public void setOrder(int order) {
		this.order = order;
	}

}
