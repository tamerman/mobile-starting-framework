<%--
  Copyright 2011-2012 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="kme"		uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>

<spring:message code="grades.title" var="msgCat_ToolTitle" />
<kme:page 
	title="${msgCat_ToolTitle}" 
	id="grades-ui" 
	backButton="true"
	backButtonURL="${pageContext.request.contextPath}/grades"
	homeButton="true" 
	cssFilename="grades">
	
	<kme:content>
	
	<c:choose>
		<c:when test="${fn:length(results) gt 0}">
		<h4 class="resultsHeading"><spring:message code="grades.resultsHeading" arguments="${studentNum},${startDate},${endDate}" htmlEscape="false"/></h4>
		<!-- Start list of results -->
		<c:forEach var="result" items="${results}">
			<ul data-role="listview" data-inset="true" class="gradeGroup">
				<li data-role="list-divider" data-theme="a">${result.moduleName}</li>
				<li data-theme="c">
					<table class="resultsTable" >
						<tr>
							<th><spring:message code="grades.participationMark" /></th>
							<td>${result.participationMark}</td>
							<td>${result.participationMarkComment}</td>
						</tr>
						<tr>
							<th><spring:message code="grades.examMark" /></th>
							<td>${result.examMark}</td>
							<td>${result.examMarkComment}</td>
						</tr>
						<tr>
							<th><spring:message code="grades.finalMark" /></th>
							<td>${result.finalMark}</td>
							<td>${result.finalMarkComment}</td>
						</tr>
					</table>
				</li>
			</ul>
		</c:forEach>
		<!-- end list of results -->
		</c:when>
		<c:otherwise>
			<h4><spring:message code="grades.noResults" /></h4>
		</c:otherwise>
	</c:choose>
	</kme:content>
</kme:page>
