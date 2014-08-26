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
@Table(name = "KME_FDBCK_SBJCT_T")
public class FeedbackSubject implements Serializable {

	private static final long serialVersionUID = 7273789153652061369L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long feedbackSubjectId;

	@Column(name = "SUB_KEY")
	private String subjectKey;

	@Column(name = "SUB_VAL")
	private String subjectValue;

	@Version
	@Column(name = "VER_NBR")
	protected Long versionNumber;

	public FeedbackSubject() {
	}


	/**
	 * @return the feedbackSubjectId
	 */
	public Long getFeedbackSubjectId() {
		return feedbackSubjectId;
	}


	/**
	 * @param feedbackSubjectId the feedbackSubjectId to set
	 */
	public void setFeedbackSubjectId(Long feedbackSubjectId) {
		this.feedbackSubjectId = feedbackSubjectId;
	}

	/**
	 * @return the subjectKey
	 */
	public String getSubjectKey() {
		return subjectKey;
	}


	/**
	 * @param subjectKey the subjectKey to set
	 */
	public void setSubjectKey(String subjectKey) {
		this.subjectKey = subjectKey;
	}


	/**
	 * @return the subjectValue
	 */
	public String getSubjectValue() {
		return subjectValue;
	}


	/**
	 * @param subjectValue the subjectValue to set
	 */
	public void setSubjectValue(String subjectValue) {
		this.subjectValue = subjectValue;
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
		sb.append(this.getSubjectKey());
		sb.append(newline);
		sb.append("Subject Value: ");
		sb.append(this.getSubjectValue());
		sb.append(newline);
		sb.append(newline);
		return sb.toString();
	}
}
