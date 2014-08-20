<%--
Copyright 2014 The Kuali Foundation Licensed under the Educational
Community License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License
at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>

<spring:message code="reporting.baseDetails" var="msgCat_BaseDetails"/>
<spring:message code="reporting.summary" var="msgCat_Summary"/>
<spring:message code="reporting.userDetail" var="msgCat_UserDetail"/>
<spring:message code="reporting.incident.email" var="msgCat_Email"/>
<spring:message code="reporting.incident.contactMe" var="msgCat_ContactMe"/>
<spring:message code="reporting.postDate" var="msgCat_PostDate"/>
<spring:message code="reporting.submissionType" var="msgCat_SubmissionType"/>
<spring:message code="reporting.ipAddress" var="msgCat_ipAddress"/>
<spring:message code="reporting.userAgent" var="msgCat_UserAgent"/>
<spring:message code="reporting.networkId" var="msgCat_NetworkID"/>
<spring:message code="reporting.revisionNumber" var="msgCat_RevisionNumber"/>
<spring:message code="reporting.revisionUser" var="msgCat_RevisionUser"/>
<spring:message code="reporting.active" var="msgCat_Active"/>
<spring:message code="reporting.archivedDate" var="msgCat_ArchivedDate"/>
<spring:message code="reporting.noComments" var="msgCat_NoComments"/>
<spring:message code="reporting.noAttachments" var="msgCat_NoAttachments"/>
<spring:message code="reporting.attachments" var="msgCat_Attachments"/>
<spring:message code="reporting.comments" var="msgCat_Comments"/>
<spring:message code="reporting.adminFunctions" var="msgCat_AdminFunctions"/>
<spring:message code="reporting.revisionDetails" var="msgCat_RevisionDetails"/>
<spring:message code="reporting.edit" var="msgCat_Edit"/>
<spring:message code="reporting.revisionHistory" var="msgCat_RevisionHistory"/>
<spring:message code="reporting.affiliations" var="msgCat_Affiliations"/>
<spring:message code="reporting.id" var="msgCat_ID"/>

<div ng-init="init()" class="inset-content-10px">
	<form>
		<div class="list-group">
			<div class="list-group-item list-header">${msgCat_BaseDetails}:</div>
			<div class="list-group-item">
				<label for="subId">${msgCat_ID}:</label><p id="subId">{{Submission.submission.id}}</p>
				<label for="subPostDate">${msgCat_PostDate}:</label><p id="subPostDate">{{Submission.submission.postDate.nanos | date:'MM/dd/yyyy @ h:mma'}}</p>
				<label for="subType">${msgCat_SubmissionType}:</label><p id="subType">{{Submission.submission.type}}</p>
			</div>
			<div class="list-group-item list-header">${msgCat_Summary}</div>
			<div class="list-group-item">{{summary}}</div>
			<div class="list-group-item list-header">${msgCat_Affiliations}</div>
			<div class="list-group-item"><label for="student">Student: </label><p id="student">{{student}}</p></div>
			<div class="list-group-item"><label for="student">Faculty: </label><p id="faculty">{{faculty}}</p></div>
			<div class="list-group-item"><label for="student">Staff: </label><p id="staff">{{staff}}</p></div>
			<div class="list-group-item"><label for="student">Other: </label><p id="other">{{other}}</p></div>
			<div class="list-group-item list-header">${msgCat_UserDetail}</div>
			<div class="list-group-item">{{email}}</div>
			<div class="list-group-item"><label for="contact">${msgCat_ContactMe}:</label><p id="contact"></p>{{contactMe}}</div>
			<button class="btn btn-kme" ng-click="editSubmission()">${msgCat_Edit}</button><button class="btn btn-kme" ng-click="revisionSubmission(Submission.submission.id)">${msgCat_RevisionHistory}</button>
		</div>
	</form>
</div>