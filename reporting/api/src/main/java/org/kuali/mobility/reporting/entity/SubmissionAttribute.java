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

package org.kuali.mobility.reporting.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attribute")
@Entity
@Table(name = "KME_SUBMISSION_ATTR_T")
public class SubmissionAttribute implements Serializable {

	private static final long serialVersionUID = 8851390314197309082L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "PRNT_ID")
	private Long parentId;

	@Column(name = "SUBMISSION_ID", nullable = false, insertable = false, updatable = false)
	private Long submissionId;

	@Column(name = "KY")
	private String key;

	// Start of denormalized values - could be refactored into normalized tables later

	@Column(name = "VAL_TXT")
	private String valueText;

	@Column(name = "VAL_LG_TXT")
	private String valueLargeText;

	@Column(name = "VAL_NBR")
	private Long valueNumber;

	@Column(name = "VAL_DT")
	private Timestamp valueDate;

	@Lob
	@Column(name = "VAL_BIN")
	private byte[] valueBinary;

	@Column(name = "CNTNT_TYP")
	private String contentType;

	@Column(name = "FILE_NM")
	private String fileName;

	// End of denormalized values

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBMISSION_ID")
	private Submission submission;

	@Version
	@Column(name = "VER_NBR")
	protected Long versionNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public String getValueLargeText() {
		return valueLargeText;
	}

	public void setValueLargeText(String valueLargeText) {
		this.valueLargeText = valueLargeText;
	}

	public Long getValueNumber() {
		return valueNumber;
	}

	public void setValueNumber(Long valueNumber) {
		this.valueNumber = valueNumber;
	}

	public Timestamp getValueDate() {
		return valueDate;
	}

	public void setValueDate(Timestamp valueDate) {
		this.valueDate = valueDate;
	}

	public byte[] getValueBinary() {
		return valueBinary;
	}

	public void setValueBinary(byte[] valueBinary) {
		this.valueBinary = valueBinary;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
