<%--

    The MIT License
    Copyright (c) 2011 Kuali Mobility Team

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

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
<spring:message code="shared.no" var="msgCat_No"/>

<div ng-init="init()">
	<a class="list-group-item" href="tel:911">
	   	<h3>${msgCat_Call} 911</h3>
	   	<p class="wrap">${msgCat_TrueEmergency}</p>
	</a>
	<div class="list-group-item" ng-show="submitProblem">
		<h3>There was a problem!</h3>
		<p>Please try resubmitting the Incident</p>
	</div>
	<div class="list-group-item" ng-show="incidentDiv">
		<div class="form-group list-header">${msgCat_ReportIncident}</div>
		<div class="form-group">
			<form>
				<label for="summary">${msgCat_Summary}:</label>
				<textarea class="form-control" id="summary" name="summary" rows="3" ng-model="summary" required></textarea>
				<form:errors path="summary"/>
					
				<label for="file">${msgCat_File}:</label>
				<input class="form-control" ng-model="file"type="file" size="40"> 
				
				<label for="emailAddress">${msgCat_Email}:</label>
		        <input class="form-control" type="text" value="" ng-model="emailAddress" placeholder="${msgCat_Anonymous}">
				
				<label for="affiliationHolder">${msgCat_Affiliation}:</label>
				<div ng-model="affiliationHolder" class="form-group">
					<input type="checkbox" value="false" ng-model="studentAffiliation"><label for="studentAffiliation">${msgCat_Student}</label>
					<span class="hiddenSeparator"></span>
					<input type="checkbox" value="false" ng-model="facultyAffiliation"><label for="facultyAffiliation">${msgCat_Faculty}</label>
					<span class="hiddenSeparator"></span>
					<input type="checkbox" value="false" ng-model="staffAffiliation"><label for="staffAffiliation">${msgCat_Staff}</label>
					<span class="hiddenSeparator"></span>
					<input type="checkbox" value="false" ng-model="otherAffiliation"><label for="otherAffiliation">${msgCat_Other}</label>
				</div>
				<label for="contactMe">${msgCat_ContactMe}:</label>
			    <div ng-model="contactMe">
			        <input ng-model="contactMe" type="radio" value="true">${msgCat_FollowUp }<br/>
			    	<input ng-model="contactMe" type="radio" value="false">${msgCat_No}<br/>
				</div>
				<button type="submit" class="btn btn-kme" ng-click="submitForm()">${msgCat_Submit}</button>
			</form>
		</div>
	</div>
	<div class="list-group-item" ng-show="!incidentDiv">
		<h3>Incident submitted!</h3>
		<p>Thank you for submmitting this incident.</p>
	</div>
</div>