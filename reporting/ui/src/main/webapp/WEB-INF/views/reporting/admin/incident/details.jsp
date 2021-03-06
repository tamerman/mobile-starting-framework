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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:message code="reporting.reportingAdmin" var="msgCat_ReportingAdmin"/>
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


<kme:page title="${msgCat_ReportingAdmin}" id="reporting" backButton="true" homeButton="true" cssFilename="reporting">
    <kme:content>
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
        		${summary}
        	</kme:listItem>
        	<c:if test="${affiliations}">
	        	<kme:listItem dataRole="list-divider">
	        		${msgCat_Affiliations}
	        	</kme:listItem>
	        	<c:if test="${not empty affiliationStudent}">
	        		<kme:listItem>
	        			${affiliationStudent}
	        		</kme:listItem>
	        	</c:if>
	        	<c:if test="${not empty affiliationFaculty}">
	        		<kme:listItem>
	        			${affiliationFaculty}
	        		</kme:listItem>
	        	</c:if>
	        	<c:if test="${not empty affiliationStaff}">
	        		<kme:listItem>
	        			${affiliationStaff}
	        		</kme:listItem>
	        	</c:if>
	        	<c:if test="${not empty affiliationOther}">
	        		<kme:listItem>
	        			${affiliationOther}
	        		</kme:listItem>
	        	</c:if>
	        </c:if>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_UserDetail}
        	</kme:listItem>
        	<kme:listItem>
        		<kme:labeledRow fieldLabel="${msgCat_Email}" fieldValue="${email}" />
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
        	<c:if test="${empty comments}">
        		<kme:listItem>${msgCat_NoComments}</kme:listItem>
        	</c:if>
        	<c:forEach items="${comments}" var="comment" varStatus="status">
	        	<kme:listItem>
					${comment.valueLargeText}
				</kme:listItem>        	
        	</c:forEach>
        	<kme:listItem dataRole="list-divider">
        		${msgCat_Attachments}
        	</kme:listItem>
        	<c:if test="${empty attachments}">
        		<kme:listItem>${msgCat_NoAttachments}</kme:listItem>
        	</c:if>
        	<c:forEach items="${attachments}" var="attachment" varStatus="status">
	        	<kme:listItem>
					${attachment.fileName}
				</kme:listItem>        	
        	</c:forEach>
        	<c:if test="${submission.active eq 1}">
        	<kme:listItem dataRole="list-divider">
        		${msgCat_AdminFunctions}
        	</kme:listItem>
        	<kme:listItem>
        		<a href="../edit/${submission.id}">${msgCat_Edit}</a>
        	</kme:listItem>
        	<kme:listItem>
        		<a href="../revisions/${submission.id}">${msgCat_RevisionHistory}</a>
        	</kme:listItem>
        	</c:if>
        </kme:listView>    
	</kme:content>
</kme:page>
