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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:message code="reporting.incident.title" var="msgCat_ToolTitle"/>
<spring:message code="reporting.incident.call" var="msgCat_Call"/>
<spring:message code="reporting.incident.trueEmergency" var="msgCat_TrueEmergency"/>
<spring:message code="reporting.incident.camera" var="msgCat_Camera"/>
<spring:message code="reporting.incident.album" var="msgCat_Album"/>
<spring:message code="reporting.incident.summary" var="msgCat_Summary"/>
<spring:message code="reporting.incident.file" var="msgCat_File"/>
<spring:message code="reporting.incident.email" var="msgCat_Email"/>
<spring:message code="reporting.incident.affiliation" var="msgCat_Affiliation"/>
<spring:message code="reporting.incident.contactMe" var="msgCat_ContactMe"/>
<spring:message code="reporting.incident.followUp" var="msgCat_FollowUp"/>
<spring:message code="reporting.affiliation.student" var="msgCat_Student"/>
<spring:message code="reporting.affiliation.faculty" var="msgCat_Faculty"/>
<spring:message code="reporting.affiliation.staff" var="msgCat_Staff"/>
<spring:message code="reporting.affiliation.other" var="msgCat_Other"/>
<spring:message code="reporting.incident.anonymous" var="msgCat_Anonymous" />
<spring:message code="reporting.incident.reportIncident" var="msgCat_ReportIncident" />
<spring:message code="reporting.incident.submit" var="msgCat_Submit"/>
<spring:message code="reporting.submitEdit" var="msgCat_SubmitEdit"/>
<spring:message code="shared.no" var="msgCat_No"/>

<div ng-init="init()" class="inset-content-10px">
	<form>
		<label for="summary">${msgCat_Summary}:</label>
		<textarea class="form-control" id="summary" name="summary" rows="3" ng-model="summary" required>{{summary}}</textarea>
		
		<label for="emailAddress">${msgCat_Email}:</label>
		<input class="form-control" type="text" ng-model="emailAddress" placeholder="${msgCat_Anonymous}">
		
		<label for="affiliationHolder">${msgCat_Affiliation}:</label>
		<div ng-model="affiliationHolder" class="form-group">
			<input type="checkbox" ng-model="studentAffiliation"><label for="studentAffiliation">${msgCat_Student}</label>
			<span class="hiddenSeparator"></span>
			<input type="checkbox" ng-model="facultyAffiliation"><label for="facultyAffiliation">${msgCat_Faculty}</label>
			<span class="hiddenSeparator"></span>
			<input type="checkbox" ng-model="staffAffiliation"><label for="staffAffiliation">${msgCat_Staff}</label>
			<span class="hiddenSeparator"></span>
			<input type="checkbox" ng-model="otherAffiliation"><label for="otherAffiliation">${msgCat_Other}</label>
		</div>
		<label for="contactMe">${msgCat_ContactMe}:</label>
		<div ng-model="contactMe">
			<input ng-model="contactMe" type="radio" value=0>${msgCat_FollowUp }<br/>
			<input ng-model="contactMe" type="radio" value=1>${msgCat_No}<br/>
		</div>
		<button class="btn btn-kme" ng-click="saveEdit()">${msgCat_SubmitEdit}</button>
	</form>
</div>