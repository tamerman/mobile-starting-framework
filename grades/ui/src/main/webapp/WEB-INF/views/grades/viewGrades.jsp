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
