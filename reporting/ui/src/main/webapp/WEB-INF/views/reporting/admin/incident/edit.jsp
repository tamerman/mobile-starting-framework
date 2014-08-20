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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:message code="reporting.reportingAdmin" var="msgCat_ReportingAdmin"/>
<spring:message code="reporting.baseDetails" var="msgCat_BaseDetails"/>
<spring:message code="reporting.summary" var="msgCat_Summary"/>
<spring:message code="reporting.postDate" var="msgCat_PostDate"/>
<spring:message code="reporting.submissionType" var="msgCat_SubmissionType"/>
<spring:message code="reporting.affiliations" var="msgCat_Affiliations"/>
<spring:message code="reporting.affiliation.student" var="msgCat_Student"/>
<spring:message code="reporting.affiliation.faculty" var="msgCat_Faculty"/>
<spring:message code="reporting.affiliation.staff" var="msgCat_Staff"/>
<spring:message code="reporting.affiliation.other" var="msgCat_Other"/>
<spring:message code="reporting.incident.email" var="msgCat_Email"/>
<spring:message code="reporting.incident.anonymous" var="msgCat_Anonymous" />
<spring:message code="reporting.incident.contactMe" var="msgCat_ContactMe"/>
<spring:message code="reporting.ipAddress" var="msgCat_ipAddress"/>
<spring:message code="reporting.userAgent" var="msgCat_UserAgent"/>
<spring:message code="reporting.networkId" var="msgCat_NetworkID"/>
<spring:message code="reporting.revisionDetails" var="msgCat_RevisionDetails"/>
<spring:message code="reporting.userDetail" var="msgCat_UserDetail"/>
<spring:message code="reporting.revisionNumber" var="msgCat_RevisionNumber"/>
<spring:message code="reporting.revisionUser" var="msgCat_RevisionUser"/>
<spring:message code="reporting.active" var="msgCat_Active"/>
<spring:message code="reporting.archivedDate" var="msgCat_ArchivedDate"/>
<spring:message code="reporting.comments" var="msgCat_Comments"/>
<spring:message code="reporting.attachments" var="msgCat_Attachments"/>
<spring:message code="reporting.addAttachment" var="msgCat_AddAttachment"/>
<spring:message code="reporting.submitEdit" var="msgCat_SubmitEdit"/>
<spring:message code="reporting.id" var="msgCat_ID"/>


<kme:page title="${msgCat_ReportingAdmin}" id="reporting" backButton="true" homeButton="true" cssFilename="reporting">
    <kme:content>
    	<form:form action="${pageContext.request.contextPath}/reporting/admin/incident/save" enctype="multipart/form-data" commandName="incident" data-ajax="false" method="post"> 
		<form:hidden path="userAgent" value="${header['User-Agent']}"/>
		<form:hidden path="id" value="${submission.id}"/>
        <kme:listView id="submissionDetails" filter="false">
        	<kme:listItem dataRole="list-divider">
        		${msgCat_BaseDetails}
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_ID}" fieldValue="${submission.id}" />        		
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_PostDate}" fieldValue="${submission.postDate}" />
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_SubmissionType}" fieldValue="${submission.type}" />
        	</kme:listItem>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_Summary}
        	</kme:listItem>
        	<kme:listItem>
        		<form:textarea path="summary" cols="40" rows="8" class="required" />
        	</kme:listItem>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_Affiliations}
        	</kme:listItem>
        	<kme:listItem>
        		<fieldset data-role="controlgroup">
		            <form:checkbox data-theme="c" path="affiliationStudent" value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Student}" />
		            <form:checkbox data-theme="c" path="affiliationFaculty" value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Faculty}" />
		            <form:checkbox data-theme="c" path="affiliationStaff"   value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Staff}" />
		            <form:checkbox data-theme="c" path="affiliationOther"   value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Other}" />
	            </fieldset>
            </kme:listItem>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_UserDetail}
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_Email}">
        			<form:input path="email" type="text" value="${email}" placeholder="${msgCat_Anonymous}" class="email" />
        		</kme:labeledRow>
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_ContactMe}" fieldValue="${contactMeText}" />
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_ipAddress}" fieldValue="${submission.ipAddress}" />
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_UserAgent}" fieldValue="${submission.userAgent}" />		
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_NetworkID}" fieldValue="${submission.userId}" />		
        	</kme:listItem>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_RevisionDetails}
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_RevisionNumber}" fieldValue="${submission.revisionNumber}" />
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_RevisionUser}" fieldValue="${submission.revisionUserId}" />
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_Active}" fieldValue="${activeText}" />
        	</kme:listItem>
        	<c:if test="${activeText eq 'No'}">
	        	<kme:listItem>
	        		<kme:labeledRow fieldLabel="${msgCat_ArchivedDate}" fieldValue="${submission.archivedDate}" />
	        	</kme:listItem>
        	</c:if>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_Comments}
        	</kme:listItem>
        	<c:forEach items="${incident.comments}" var="comment" varStatus="status">
	        	<kme:listItem>
					${comment.valueLargeText}
				</kme:listItem>        	
        	</c:forEach>
        	<kme:listItem>
        		<form:textarea path="newComment" cols="40" rows="8" />
        	</kme:listItem>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_Attachments}
        	</kme:listItem>
        	<kme:listItem>
        	<c:forEach items="${incident.attachments}" var="attachment" varStatus="status">
	        	<kme:listItem>
					${attachment.fileName}
				</kme:listItem>        	
        	</c:forEach>
        	</kme:listItem>
        	<kme:listItem>
	        	${msgCat_AddAttachment}<input type="file" name="file" />
	        </kme:listItem>
	        <kme:listItem>
	        	<div data-inline="true">
        			<input data-theme="a" class="submit" type="submit" value="${msgCat_SubmitEdit}" />
        		</div>
	        </kme:listItem>
        </kme:listView>
        
        </form:form>    
	</kme:content>
</kme:page>
