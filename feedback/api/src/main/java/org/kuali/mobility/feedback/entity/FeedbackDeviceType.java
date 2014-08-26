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
@Table(name = "KME_FDBCK_DEVICE_TYPE_T")
public class FeedbackDeviceType implements Serializable {

	private static final long serialVersionUID = 7273789153652961369L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long feedbackDeviceId;

	@Column(name = "SUB_DEV_KEY")
	private String subjectDeviceKey;

	@Column(name = "SUB_DEV_VAL")
	private String subjectDeviceValue;

	@Version
	@Column(name = "VER_NBR")
	protected Long versionNumber;

	public FeedbackDeviceType() {
	}

	/**
	 * @return the feedbackDeviceId
	 */
	public Long getFeedbackDeviceId() {
		return feedbackDeviceId;
	}

	/**
	 * @param feedbackDeviceId the feedbackDeviceId to set
	 */
	public void setFeedbackDeviceId(Long feedbackDeviceId) {
		this.feedbackDeviceId = feedbackDeviceId;
	}

	/**
	 * @return the subjectDeviceKey
	 */
	public String getSubjectDeviceKey() {
		return subjectDeviceKey;
	}

	/**
	 * @param subjectDeviceKey the subjectDeviceKey to set
	 */
	public void setSubjectDeviceKey(String subjectDeviceKey) {
		this.subjectDeviceKey = subjectDeviceKey;
	}

	/**
	 * @return the subjectDeviceValue
	 */
	public String getSubjectDeviceValue() {
		return subjectDeviceValue;
	}

	/**
	 * @param subjectDeviceValue the subjectDeviceValue to set
	 */
	public void setSubjectDeviceValue(String subjectDeviceValue) {
		this.subjectDeviceValue = subjectDeviceValue;
	}

	/**
	 * @return the versionNumber
	 */
	public Long getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber the versionNumber to set
	 */
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		String newline = "\r\n";

		StringBuilder sb = new StringBuilder();
		sb.append("Subject Key: ");
		sb.append(this.getSubjectDeviceKey());
		sb.append(newline);
		sb.append("Subject Value: ");
		sb.append(this.getSubjectDeviceValue());
		sb.append(newline);
		sb.append(newline);
		return sb.toString();
	}
}
