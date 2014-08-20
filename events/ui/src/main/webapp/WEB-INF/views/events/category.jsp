<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="events.title.byCategory" var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="events" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/home">
	<kme:content>
		<kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">
<%-- 			<kme:listItem>
				<c:url var="calendarUrl" value="/calendar">
				</c:url>
				<a href="${calendarUrl}">
					<h3>
						My Calendar
					</h3> </a>
			</kme:listItem>
--%>

		<c:forEach items="${categories}" var="category" varStatus="status">
			<kme:listItem>
				<c:url var="url" value="/events/viewEvents">
					<c:param name="categoryId" value="${category.categoryId}"></c:param>
					<c:param name="campus" value="${campus}"></c:param>
				</c:url>
				<a href="${url}">
					<h3><c:out value="${category.title}" />	</h3>
				</a>
			</kme:listItem>
		</c:forEach>
		</kme:listView>
	</kme:content>
	<c:if test="${showCategoryTab == true || showDateRangeTab == true}">
	<kme:tabBar id="tabNav" showIcons="false" footer="true">
		<c:if test="${showCategoryTab == true}">
		<kme:tabBarItem url="${pageContext.request.contextPath}/events/byCategory" selected="true"><spring:message code="events.byCategory" /></kme:tabBarItem>
		</c:if>
		<kme:tabBarItem url="${pageContext.request.contextPath}/events"><spring:message code="events.byDate" /></kme:tabBarItem>
		<c:if test="${showDateRangeTab == true}">
		<kme:tabBarItem url="${pageContext.request.contextPath}/events/byDateRange"><spring:message code="events.byRange" /></kme:tabBarItem>
		</c:if>
	</kme:tabBar>
	</c:if>
</kme:page>
