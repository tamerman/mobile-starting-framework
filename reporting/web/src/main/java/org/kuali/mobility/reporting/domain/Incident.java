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

package org.kuali.mobility.reporting.domain;

import java.io.Serializable;
import java.util.List;

import org.kuali.mobility.reporting.entity.SubmissionAttribute;
import org.springframework.web.multipart.MultipartFile;

public class Incident implements Serializable {

	private static final long serialVersionUID = 1844528404920336947L;

	private Long id;

	private String newComment;

	private String userAgent;

	private String summary;

	private String email;

	private String affiliationStudent;

	private String affiliationFaculty;

	private String affiliationStaff;

	private String affiliationOther;

	private boolean contactMe;

	private MultipartFile file;

	private List<SubmissionAttribute> attachments;

	private List<SubmissionAttribute> comments;

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAffiliationStudent() {
		return affiliationStudent;
	}

	public void setAffiliationStudent(String affiliationStudent) {
		this.affiliationStudent = affiliationStudent;
	}

	public String getAffiliationFaculty() {
		return affiliationFaculty;
	}

	public void setAffiliationFaculty(String affiliationFaculty) {
		this.affiliationFaculty = affiliationFaculty;
	}

	public String getAffiliationStaff() {
		return affiliationStaff;
	}

	public void setAffiliationStaff(String affiliationStaff) {
		this.affiliationStaff = affiliationStaff;
	}

	public String getAffiliationOther() {
		return affiliationOther;
	}

	public void setAffiliationOther(String affiliationOther) {
		this.affiliationOther = affiliationOther;
	}

	public boolean isContactMe() {
		return contactMe;
	}

	public void setContactMe(boolean contactMe) {
		this.contactMe = contactMe;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SubmissionAttribute> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SubmissionAttribute> attachments) {
		this.attachments = attachments;
	}

	public List<SubmissionAttribute> getComments() {
		return comments;
	}

	public void setComments(List<SubmissionAttribute> comments) {
		this.comments = comments;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

}
