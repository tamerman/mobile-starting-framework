<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:message code="reporting.reportingAdmin" var="msgCat_ReportingAdmin"/>
<spring:message code="reporting.revision" var="msgCat_Revision"/>
<spring:message code="reporting.revisions" var="msgCat_Revisions"/>


<div ng-init="init()" class="inset-content-10px">
	<div class="form-group list-header">${msgCat_Revisions}</div>
	<div ng-click="submissionClick(submission.id)" class="list-group-item" ng-repeat="submission in submissions">
		<h3>${msgCat_Revision} : : {{submission.revisionNumber}} - {{submission.id}}</h3>
		<p>{{submission.type}} - {{submission.archivedDate.nanos | date:'MM/dd/yyyy @ h:mma'}}</p>
	</div>
</div>