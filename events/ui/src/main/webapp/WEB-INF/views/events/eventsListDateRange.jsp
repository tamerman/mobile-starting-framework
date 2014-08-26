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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="events.title.byRange" var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="events" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/home">
	<kme:content>
		<kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">
			
			<c:forEach items="${eventDateFromTo}" var ="edft" varStatus="status">
				<div id="dateHeading">
					<!--							 Date								-->	
					<h3><c:out value="${edft.key}" /></h3>
				</div>				
				<c:forEach items="${edft.value}" var ="gbc" varStatus="status">
					<!--						 Category Header						-->
					<kme:listItem dataRole="list-divider">
						<c:out value="${gbc.key.title}" />
					</kme:listItem>	

					<c:forEach items="${gbc.value}" var ="gbcV" varStatus="status">
						<!--					 Event Information						-->
						<kme:listItem>
							<c:url var="url" value="/events/viewEvent">
								<c:param name="categoryId" value="${gbc.key.categoryId}"></c:param>
								<c:param name="campus" value="${campus}"></c:param>
								<c:param name="eventId" value="${gbcV.eventId}"></c:param>
							</c:url>
							<a href="${url}">
								<h3 class="wrap"><c:out value="${gbcV.title}" /></h3>
								<p style="white-space:pre-wrap"><c:out value="${gbcV.displayStartTime}" /> - <c:out value="${gbcV.displayEndTime}" /> | <c:out value="${gbcV.location}" /></p>
							</a>
						</kme:listItem>
					 </c:forEach>
				</c:forEach>
			</c:forEach>
		</kme:listView>
		<div data-role="navbar" data-position="fixed">
			<ul>
				<li><a href="${pageContext.request.contextPath}/events/byCategory"><spring:message code="events.byCategory" /></a></li>
				<li><a href="${pageContext.request.contextPath}/events"><spring:message code="events.byDate" /></a></li>
				<li><a href="${pageContext.request.contextPath}/events/byDateRange"><spring:message code="events.byRange" /></a></li>
			</ul>
	   </div><!-- /navbar -->
	</kme:content>	
</kme:page>

