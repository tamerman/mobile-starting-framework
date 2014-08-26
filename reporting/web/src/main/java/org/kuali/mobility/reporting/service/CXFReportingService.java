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

package org.kuali.mobility.reporting.service;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.kuali.mobility.reporting.dao.ReportingDao;
import org.kuali.mobility.reporting.entity.Submission;
import org.kuali.mobility.reporting.entity.SubmissionAttribute;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CXFReportingService {
	/**
	 * A reference to a logger for this service
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CXFReportingService.class);

	public static final String INCIDENT_TYPE = "INCIDENT";
	public static final String INCIDENT_GROUP = "INCIDENT_GROUP";
	public static final String SUMMARY = "SUMMARY";
	public static final String EMAIL = "EMAIL";
	public static final String AFFILIATION_STUDENT = "AFFILIATION_STUDENT";
	public static final String AFFILIATION_FACULTY = "AFFILIATION_FACULTY";
	public static final String AFFILIATION_STAFF = "AFFILIATION_STAFF";
	public static final String AFFILIATION_OTHER = "AFFILIATION_OTHER";
	public static final String CONTACT_ME = "CONTACT_ME";
	public static final String ATTACHMENT = "ATTACHMENT";
	public static final String COMMENT = "COMMENT";

	private ReportingDao dao;

	/**
	 * A reference to the {@code MessageContext}
	 */
	@Context
	private MessageContext messageContext;

	@POST
	@Path("editSubmission")
	public void editSubmission(@RequestBody String data) {
		JSONObject queryParams;
		try {
			if (data != null) {
				Submission submission = new Submission();
				queryParams = (JSONObject) JSONSerializer.toJSON(data);
				submission.setId(queryParams.getLong("id"));
				submission.setType(INCIDENT_TYPE);
				submission.setActive(1);
				submission.setGroup(INCIDENT_GROUP);
				submission.setPostDate(new Timestamp(queryParams.getLong("postDate")));
				submission.setRevisionNumber(queryParams.getLong("revisionNumber") + 1L);
				submission.setUserId(queryParams.getString("userId"));

				SubmissionAttribute submissionAttributeSummary = new SubmissionAttribute();
				submissionAttributeSummary.setKey(SUMMARY);
				submissionAttributeSummary.setValueLargeText(queryParams.getString("summary"));
				submissionAttributeSummary.setSubmissionId(submission.getId());
				submissionAttributeSummary.setSubmission(submission);

				SubmissionAttribute submissionAttributeContactMe = new SubmissionAttribute();
				submissionAttributeContactMe.setKey(CONTACT_ME);
				submissionAttributeContactMe.setValueNumber(queryParams.getString("contactMe").equalsIgnoreCase("true") ? 1L : 0L);
				submissionAttributeContactMe.setSubmissionId(submission.getId());
				submissionAttributeContactMe.setSubmission(submission);

				SubmissionAttribute submissionAttributeEmail = new SubmissionAttribute();
				submissionAttributeEmail.setKey(EMAIL);
				submissionAttributeEmail.setValueText(queryParams.getString("email"));
				submissionAttributeEmail.setSubmissionId(submission.getId());
				submissionAttributeEmail.setSubmission(submission);

				SubmissionAttribute submissionAttributeAffiliationStudent = new SubmissionAttribute();
				submissionAttributeAffiliationStudent.setKey(AFFILIATION_STUDENT);
				submissionAttributeAffiliationStudent.setValueText(queryParams.getString("studentAffiliation"));
				submissionAttributeAffiliationStudent.setSubmissionId(submission.getId());
				submissionAttributeAffiliationStudent.setSubmission(submission);

				SubmissionAttribute submissionAttributeAffiliationFaculty = new SubmissionAttribute();
				submissionAttributeAffiliationFaculty.setKey(AFFILIATION_FACULTY);
				submissionAttributeAffiliationFaculty.setValueText(queryParams.getString("facultyAffiliation"));
				submissionAttributeAffiliationFaculty.setSubmissionId(submission.getId());
				submissionAttributeAffiliationFaculty.setSubmission(submission);

				SubmissionAttribute submissionAttributeAffiliationStaff = new SubmissionAttribute();
				submissionAttributeAffiliationStaff.setKey(AFFILIATION_STAFF);
				submissionAttributeAffiliationStaff.setValueText(queryParams.getString("staffAffiliation"));
				submissionAttributeAffiliationStaff.setSubmissionId(submission.getId());
				submissionAttributeAffiliationStaff.setSubmission(submission);

				SubmissionAttribute submissionAttributeAffiliationOther = new SubmissionAttribute();
				submissionAttributeAffiliationOther.setKey(AFFILIATION_OTHER);
				submissionAttributeAffiliationOther.setValueText(queryParams.getString("otherAffiliation"));
				submissionAttributeAffiliationOther.setSubmissionId(submission.getId());
				submissionAttributeAffiliationOther.setSubmission(submission);

				ArrayList<SubmissionAttribute> attributes = new ArrayList<SubmissionAttribute>();
				attributes.add(submissionAttributeSummary);
				attributes.add(submissionAttributeEmail);
				attributes.add(submissionAttributeContactMe);
				attributes.add(submissionAttributeAffiliationStudent);
				attributes.add(submissionAttributeAffiliationFaculty);
				attributes.add(submissionAttributeAffiliationStaff);
				attributes.add(submissionAttributeAffiliationOther);

				submission.setAttributes(attributes);

				getDao().saveSubmission(submission);
			}
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
		}
	}

	@POST
	@Path("submitIncident")
	public Boolean submitIncident(@RequestBody String data) {
		if (data == null) {
			return false; //and sadness
		}
		JSONObject queryParams;
		try {
			HttpServletRequest request = (HttpServletRequest) this.getMessageContext().getHttpServletRequest();
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			Submission submission = new Submission();
			submission.setType(INCIDENT_TYPE);
			submission.setActive(1);
			submission.setGroup(INCIDENT_GROUP);
			submission.setPostDate(new Timestamp(new Date().getTime()));
			submission.setRevisionNumber(0L);
			submission.setUserId(user.getLoginName());

			Long pk = getDao().saveSubmission(submission);

			SubmissionAttribute submissionAttributeSummary = new SubmissionAttribute();
			submissionAttributeSummary.setKey(SUMMARY);
			submissionAttributeSummary.setValueLargeText(queryParams.getString("summary"));
			submissionAttributeSummary.setSubmissionId(pk);
			submissionAttributeSummary.setSubmission(submission);

			SubmissionAttribute submissionAttributeContactMe = new SubmissionAttribute();
			submissionAttributeContactMe.setKey(CONTACT_ME);
			submissionAttributeContactMe.setValueNumber(queryParams.getString("contactMe").equalsIgnoreCase("true") ? 1L : 0L);
			submissionAttributeContactMe.setSubmissionId(pk);
			submissionAttributeContactMe.setSubmission(submission);

			SubmissionAttribute submissionAttributeEmail = new SubmissionAttribute();
			submissionAttributeEmail.setKey(EMAIL);
			submissionAttributeEmail.setValueText(queryParams.getString("email"));
			submissionAttributeEmail.setSubmissionId(pk);
			submissionAttributeEmail.setSubmission(submission);

			SubmissionAttribute submissionAttributeAffiliationStudent = new SubmissionAttribute();
			submissionAttributeAffiliationStudent.setKey(AFFILIATION_STUDENT);
			submissionAttributeAffiliationStudent.setValueText(queryParams.getString("studentAffiliation"));
			submissionAttributeAffiliationStudent.setSubmissionId(pk);
			submissionAttributeAffiliationStudent.setSubmission(submission);

			SubmissionAttribute submissionAttributeAffiliationFaculty = new SubmissionAttribute();
			submissionAttributeAffiliationFaculty.setKey(AFFILIATION_FACULTY);
			submissionAttributeAffiliationFaculty.setValueText(queryParams.getString("facultyAffiliation"));
			submissionAttributeAffiliationFaculty.setSubmissionId(pk);
			submissionAttributeAffiliationFaculty.setSubmission(submission);

			SubmissionAttribute submissionAttributeAffiliationStaff = new SubmissionAttribute();
			submissionAttributeAffiliationStaff.setKey(AFFILIATION_STAFF);
			submissionAttributeAffiliationStaff.setValueText(queryParams.getString("staffAffiliation"));
			submissionAttributeAffiliationStaff.setSubmissionId(pk);
			submissionAttributeAffiliationStaff.setSubmission(submission);

			SubmissionAttribute submissionAttributeAffiliationOther = new SubmissionAttribute();
			submissionAttributeAffiliationOther.setKey(AFFILIATION_OTHER);
			submissionAttributeAffiliationOther.setValueText(queryParams.getString("otherAffiliation"));
			submissionAttributeAffiliationOther.setSubmissionId(pk);
			submissionAttributeAffiliationOther.setSubmission(submission);

			ArrayList<SubmissionAttribute> attributes = new ArrayList<SubmissionAttribute>();
			attributes.add(submissionAttributeSummary);
			attributes.add(submissionAttributeEmail);
			attributes.add(submissionAttributeContactMe);
			attributes.add(submissionAttributeAffiliationStudent);
			attributes.add(submissionAttributeAffiliationFaculty);
			attributes.add(submissionAttributeAffiliationStaff);
			attributes.add(submissionAttributeAffiliationOther);

			submission.setAttributes(attributes);

			getDao().saveSubmission(submission);
			return true;
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return false; //and more sadness
		}
	}

	@POST
	@Path("getRevisionsById")
	public List<Submission> getRevisionsById(@RequestBody String data) {
		if (data == null) {
			return null;
		}
		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			//test
			return testSubmissions();
			//end test
			//return getDao().findAllSubmissionsByParentId(queryParams.getLong("id"));
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return null; //and more sadness
		}
	}

	@POST
	@Path("getSubmissionById")
	public Submission getSubmissionById(@RequestBody String data) {
		if (data == null) {
			return null; //and sadness
		}
		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			//test
			HttpServletRequest request = (HttpServletRequest) this.getMessageContext().getHttpServletRequest();
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			Submission testSubmission = new Submission();
			testSubmission.setId(0L);
			testSubmission.setUserId(user.getLoginName());
			java.util.Date date = new java.util.Date();
			testSubmission.setRevisionNumber(0L);
			Timestamp timestamp = new Timestamp(date.getTime());
			testSubmission.setPostDate(timestamp);
			testSubmission.setType("test");

			SubmissionAttribute submissionAttributeSummary = new SubmissionAttribute();
			submissionAttributeSummary.setKey(SUMMARY);
			submissionAttributeSummary.setValueLargeText("test summary");
			submissionAttributeSummary.setSubmissionId(0L);

			SubmissionAttribute submissionAttributeContactMe = new SubmissionAttribute();
			submissionAttributeContactMe.setKey(CONTACT_ME);
			submissionAttributeContactMe.setValueNumber(1L);
			submissionAttributeContactMe.setSubmissionId(0L);

			SubmissionAttribute submissionAttributeEmail = new SubmissionAttribute();
			submissionAttributeEmail.setKey(EMAIL);
			submissionAttributeEmail.setValueText("test@test.com");
			submissionAttributeEmail.setSubmissionId(0L);

			SubmissionAttribute submissionAttributeAffiliationStudent = new SubmissionAttribute();
			submissionAttributeAffiliationStudent.setKey(AFFILIATION_STUDENT);
			submissionAttributeAffiliationStudent.setValueText("true");
			submissionAttributeAffiliationStudent.setSubmissionId(0L);

			SubmissionAttribute submissionAttributeAffiliationFaculty = new SubmissionAttribute();
			submissionAttributeAffiliationFaculty.setKey(AFFILIATION_FACULTY);
			submissionAttributeAffiliationFaculty.setValueText("false");
			submissionAttributeAffiliationFaculty.setSubmissionId(0L);

			SubmissionAttribute submissionAttributeAffiliationStaff = new SubmissionAttribute();
			submissionAttributeAffiliationStaff.setKey(AFFILIATION_STAFF);
			submissionAttributeAffiliationStaff.setValueText("false");
			submissionAttributeAffiliationStaff.setSubmissionId(0L);

			SubmissionAttribute submissionAttributeAffiliationOther = new SubmissionAttribute();
			submissionAttributeAffiliationOther.setKey(AFFILIATION_OTHER);
			submissionAttributeAffiliationOther.setValueText("true");
			submissionAttributeAffiliationOther.setSubmissionId(0L);

			ArrayList<SubmissionAttribute> attributes = new ArrayList<SubmissionAttribute>();
			attributes.add(submissionAttributeSummary);
			attributes.add(submissionAttributeEmail);
			attributes.add(submissionAttributeContactMe);
			attributes.add(submissionAttributeAffiliationStudent);
			attributes.add(submissionAttributeAffiliationFaculty);
			attributes.add(submissionAttributeAffiliationStaff);
			attributes.add(submissionAttributeAffiliationOther);

			testSubmission.setAttributes(attributes);

			return testSubmission;
			//end test
			//return getDao().findSubmissionById(queryParams.getLong("id"));
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return null; //and more sadness
		}
	}

	@GET
	@Path("/getAllSubmissions")
	public List<Submission> getAllSubmissions() {
		//test
		return testSubmissions();
		//end test
		//return getDao().findAllSubmissions();
	}

	//test
	private List<Submission> testSubmissions() {
		Submission testSubmission = new Submission();
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		testSubmission.setId(0L);
		testSubmission.setPostDate(timestamp);
		testSubmission.setType("test");
		testSubmission.setArchivedDate(timestamp);
		testSubmission.setRevisionNumber(0L);

		Submission testSubmission2 = new Submission();
		testSubmission2.setId(1L);
		Timestamp timestamp2 = new Timestamp(date.getTime());
		testSubmission2.setPostDate(timestamp2);
		testSubmission2.setType("test 2");
		testSubmission2.setRevisionNumber(1L);
		testSubmission2.setArchivedDate(timestamp2);
		List<Submission> submissions = new ArrayList<Submission>();
		submissions.add(testSubmission);
		submissions.add(testSubmission2);
		return submissions;
	}
	//end test

	public ReportingDao getDao() {
		return dao;
	}

	public void setDao(ReportingDao dao) {
		this.dao = dao;
	}

	public MessageContext getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}
}
