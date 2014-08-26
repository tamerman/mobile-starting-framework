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

package org.kuali.mobility.feedback.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "KME_FDBCK_T")
public class Feedback implements Serializable {

	private static final long serialVersionUID = 7273789153652061359L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long feedbackId;

	@Column(name = "PST_TS")
	private Timestamp postedTimestamp;

	@Column(name = "TXT")
	private String noteText;

	@Column(name = "CMPS")
	private String campus;

	@Column(name = "AFFL")
	private String affiliation;

	@Column(name = "DVC_TYP")
	private String deviceType;

	@Column(name = "UA")
	private String userAgent;

	@Column(name = "SRVC")
	private String service;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "USR_ID")
	private String userId;

	@Version
	@Column(name = "VER_NBR")
	protected Long versionNumber;

	public Feedback() {
	}

	public String getNoteTextShort() {
		if (this.getNoteText() != null) {
			return this.getNoteText().length() > 50 ? this.getNoteText().substring(0, 50) + "..." : this.getNoteText();
		}
		return "";
	}

	public String getNoteFormatted() {
		return this.getNoteText().replaceAll("\\n", "<br/>");
	}

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Timestamp getPostedTimestamp() {
		return postedTimestamp;
	}

	public void setPostedTimestamp(Timestamp postedTimestamp) {
		this.postedTimestamp = postedTimestamp;
	}

	public String getNoteText() {
		if (noteText != null) {
			return noteText;
		}
		return "";
	}

	public String getNoteTextExport() {
		if (noteText != null) {
			String text = new String(noteText);
			text = text.replaceAll("\t", " ");
			text = text.replaceAll("\r", " ");
			text = text.replaceAll("\n", " ");
			return text;
		}
		return "";
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		String newline = "\r\n";

		StringBuilder sb = new StringBuilder();
		sb.append("User Agent: ");
		sb.append(this.getUserAgent());
		sb.append(newline);
		sb.append("Timestamp: ");
		sb.append(this.getPostedTimestamp());
		sb.append(newline);
		sb.append(newline);
		if (null != this.getCampus() && !this.getCampus().trim().isEmpty()) {
			sb.append("Campus: ");
			sb.append(this.getCampus().trim());
			sb.append(newline);
		}

		if (null != this.getAffiliation() && !this.getAffiliation().trim().isEmpty()) {
			sb.append("Affiliation: ");
			sb.append(this.getAffiliation().trim());
			sb.append(newline);
		}

		if (null != this.getUserId() && !this.getUserId().trim().isEmpty()) {
			sb.append("User Id: ");
			sb.append(this.getUserId().trim());
			sb.append(newline);
		}

		sb.append("Device: ");
		sb.append(this.getDeviceType());
		sb.append(newline);
		sb.append(newline);

		sb.append("Service: ");
		sb.append(this.getService());
		sb.append(newline);

		sb.append("Email: ");
		sb.append(this.getEmail());
		sb.append(newline);
		sb.append(newline);
		sb.append(this.getNoteTextExport());

		return sb.toString();
	}
}
