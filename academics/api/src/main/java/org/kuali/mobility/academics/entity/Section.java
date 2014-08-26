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

package org.kuali.mobility.academics.entity;

import org.kuali.mobility.shared.EncodingTypes;
import org.kuali.mobility.util.DigestConstants;
import org.kuali.mobility.util.DigestUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@XmlRootElement(name = "section")
@XmlAccessorType(XmlAccessType.FIELD)
public class Section implements Serializable {

	/* Mapping from PeopleSoft's SSRENROLLMENT service
     SUBJECT = PGMGMT
     CATALOG_NBR = 400
     CLASS_SECTION = 001
     SSR_COMPONENT_LOVDescr = Lecture (not LEC)
     CLASS_DESCRIPTION = Test Course - Student Reg
     SSR_MTG_SCHED_LONG = TBA
     SSR_MTG_LOC_LONG = TBA
     */
	private String number;
	private String type;
	private String typeDescription;
	private String sessionDescription;
	//    private String componentShort;    // comment out as not be mapped anywhere
	private String courseTitle;
	private String courseDescription;
	//    private String componentDescription;  // comment out as not be mapped anywhere
	//LSA more course description
	private String additionalInfo;
	private String classTopic;
	private String enrollmentStatus;
	private String enrollmentTotal;
	private String enrollmentCapacity;
	private String availableSeats;
	private String waitTotal;
	private String waitCapacity;
	private String creditHours;
	private String startDate;
	private String endDate;
	private String combinedSectionId;
	@XmlElement(name = "meeting")
	private List<SectionMeeting> meetings;
	private String catalogNumber;
	private String subjectId;
	private String termId;
	private String sectionUID;
	private String repeatCode;
	private String grade;
	private String gradeStatus;


	public Section() {
		meetings = new ArrayList<SectionMeeting>();
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeDescription
	 */
	public String getTypeDescription() {
		return typeDescription;
	}

	/**
	 * @param typeDescription the typeDescription to set
	 */
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	/**
	 * @return the sessionDescription
	 */
	public String getSessionDescription() {
		return sessionDescription;
	}

	/**
	 * @param sessionDescription the sessionDescription to set
	 */
	public void setSessionDescription(String sessionDescription) {
		this.sessionDescription = sessionDescription;
	}

	//    public String getComponentShort() {
//        return componentShort;
//    }
//
//    public void setComponentShort(String componentShort) {
//        this.componentShort = componentShort;
//    }
	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	//    public String getComponentDescription() {
//        return componentDescription;
//    }
//
//    public void setComponentDescription(String componentDescription) {
//        this.componentDescription = componentDescription;
//    }
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/**
	 * @return the classTopic
	 */
	public String getClassTopic() {
		return classTopic;
	}

	/**
	 * @param classTopic the classTopic to set
	 */
	public void setClassTopic(String classTopic) {
		this.classTopic = classTopic;
	}

	/**
	 * @return the enrollmentStatus
	 */
	public String getEnrollmentStatus() {
		return enrollmentStatus;
	}

	/**
	 * @param enrollmentStatus the enrollmentStatus to set
	 */
	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	/**
	 * @return the enrollmentTotal
	 */
	public String getEnrollmentTotal() {
		return enrollmentTotal;
	}

	/**
	 * @param enrollmentTotal the enrollmentTotal to set
	 */
	public void setEnrollmentTotal(String enrollmentTotal) {
		this.enrollmentTotal = enrollmentTotal;
	}

	/**
	 * @return the enrollmentCapacity
	 */
	public String getEnrollmentCapacity() {
		return enrollmentCapacity;
	}

	/**
	 * @param enrollmentCapacity the enrollmentCapacity to set
	 */
	public void setEnrollmentCapacity(String enrollmentCapacity) {
		this.enrollmentCapacity = enrollmentCapacity;
	}

	/**
	 * @return the availableSeats
	 */
	public String getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * @param availableSeats the availableSeats to set
	 */
	public void setAvailableSeats(String availableSeats) {
		this.availableSeats = availableSeats;
	}

	/**
	 * @return the waitTotal
	 */
	public String getWaitTotal() {
		return waitTotal;
	}

	/**
	 * @param waitTotal the waitTotal to set
	 */
	public void setWaitTotal(String waitTotal) {
		this.waitTotal = waitTotal;
	}

	/**
	 * @return the waitCapacity
	 */
	public String getWaitCapacity() {
		return waitCapacity;
	}

	/**
	 * @param waitCapacity the waitCapacity to set
	 */
	public void setWaitCapacity(String waitCapacity) {
		this.waitCapacity = waitCapacity;
	}

	/**
	 * @return the creditHours
	 */
	public String getCreditHours() {
		return creditHours;
	}

	/**
	 * @param creditHours the creditHours to set
	 */
	public void setCreditHours(String creditHours) {
		this.creditHours = creditHours;
	}

	/**
	 * @return the combinedSectionId
	 */
	public String getCombinedSectionId() {
		return combinedSectionId;
	}

	/**
	 * @param combinedSectionId the combinedSectionId to set
	 */
	public void setCombinedSectionId(String combinedSectionId) {
		this.combinedSectionId = combinedSectionId;
	}

	/**
	 * @return the meetings
	 */
	public List<SectionMeeting> getMeetings() {
		return meetings;
	}

	/**
	 * @param meetings the meetings to set
	 */
	public void setMeetings(List<SectionMeeting> meetings) {
		this.meetings = meetings;
	}

	/**
	 * @return the catalogNumber
	 */
	public String getCatalogNumber() {
		return catalogNumber;
	}

	/**
	 * @param catalogNumber the catalogNumber to set
	 */
	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getSectionUID() {
		if (null == sectionUID) {
			this.setSectionUID(null);
		}
		return sectionUID;
	}

	public void setSectionUID(String uid) {
		sectionUID = DigestUtil.getDigest(getTermId() + getSubjectId() + getCatalogNumber() + getNumber(), DigestConstants.SHA1, EncodingTypes.HEX);
	}

	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the repeatCode
	 */
	public String getRepeatCode() {
		return repeatCode;
	}

	/**
	 * @param repeatCode the repeatCode to set
	 */
	public void setRepeatCode(String repeatCode) {
		this.repeatCode = repeatCode;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return the gradeStatus
	 */
	public String getGradeStatus() {
		return gradeStatus;
	}

	/**
	 * @param gradeStatus the gradeStatus to set
	 */
	public void setGradeStatus(String gradeStatus) {
		this.gradeStatus = gradeStatus;
	}
}
