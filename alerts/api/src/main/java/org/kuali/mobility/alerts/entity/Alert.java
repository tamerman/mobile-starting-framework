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

package org.kuali.mobility.alerts.entity;

import org.kuali.mobility.xml.DateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * An object representing an alert. Alerts include events such as a crisis,
 * emergency, or warning on campus.
 *
 * @author Kuali Mobility Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "alert")
public class Alert implements Serializable, Comparable<Alert> {

	private static final long serialVersionUID = 3298337944905192830L;

	private static final String NORMAL_TYPE = "Normal";
	private static final String INFO_TYPE = "Information";
	private static final String WARNING_TYPE = "Caution";
	private static final String DANGER_TYPE = "Emergency";

	private String campus;
	private String type;
	private String title;
	private String priority;
	private String mobileText;
	private String url;
	private int key;

	@XmlElement(name = "timeIssued", required = true)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date timeIssued;
	@XmlElement(name = "timeExpires", required = false)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date timeExpires;

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getMobileText() {
		return mobileText;
	}

	public void setMobileText(String mobileText) {
		this.mobileText = mobileText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null) {
			Alert alert = (Alert) object;
			if ((((this.getCampus() == null || this.getCampus().equals("")) && (alert.getCampus() == null || alert.getCampus().equals(""))) || (this.getCampus() != null && this.getCampus().equals(alert.getCampus()))) &&
					(((this.getMobileText() == null || this.getMobileText().equals("")) && (alert.getMobileText() == null || alert.getMobileText().equals(""))) || (this.getMobileText() != null && this.getMobileText().equals(alert.getMobileText()))) &&
					(((this.getPriority() == null || this.getPriority().equals("")) && (alert.getPriority() == null || alert.getPriority().equals(""))) || (this.getPriority() != null && this.getPriority().equals(alert.getPriority()))) &&
					(((this.getTitle() == null || this.getTitle().equals("")) && (alert.getTitle() == null || alert.getTitle().equals(""))) || (this.getTitle() != null && this.getTitle().equals(alert.getTitle()))) &&
					(((this.getType() == null || this.getType().equals("")) && (alert.getType() == null || alert.getType().equals(""))) || (this.getType() != null && this.getType().equals(alert.getType()))) &&
					(((this.getUrl() == null || this.getUrl().equals("")) && (alert.getUrl() == null || alert.getUrl().equals(""))) || (this.getUrl() != null && this.getUrl().equals(alert.getUrl())))) {
				return true;
			}
		}
		return false;
	}

	public int compareTo(Alert that) {
		if (this.getCampus() == null || that == null || that.getCampus() == null) {
			return -1;
		}
		return this.getCampus().compareTo(that.getCampus());
	}

	public Date getTimeIssued() {
		return timeIssued;
	}

	public void setTimeIssued(Date timeIssued) {
		this.timeIssued = timeIssued;
	}

	public Date getTimeExpires() {
		return timeExpires;
	}

	public void setTimeExpires(Date timeExpires) {
		this.timeExpires = timeExpires;
	}
}
