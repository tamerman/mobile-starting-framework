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
