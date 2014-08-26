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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="events.eventDetail" var="msgCat_ToolTitle"/>

<c:url var="back" value="/events/viewEvents">
	<c:param name="categoryId" value="${categoryId}" />
	<c:param name="campus" value="${campus}" />
</c:url>

<kme:page title="${msgCat_ToolTitle}" id="events" backButton="true" homeButton="true" backButtonURL="${back}">
	<kme:content>
		<h3 class="wrap">
			<c:out value="${event.title}" />
		</h3>
		<c:if test="${not empty event.location}">
			<p class="wrap">
				<c:out value="${event.location}" />
			</p>
		</c:if>
		<p class="wrap">
			<c:out value="${event.displayStartDate}" />
			<c:out value="${event.displayStartTime}" />
			<c:if test="${not empty event.displayEndDate}">- <c:if test="${event.displayEndDate ne event.displayStartDate}">
					<c:out value="${event.displayEndDate}" />
				</c:if>
				<c:out value="${event.displayEndTime}" />
			</c:if>
		</p>
		<c:if test="${not empty event.description}">
			<br />
			<h3><spring:message code="events.description" /></h3>
			<c:forEach var="description" items="${event.description}" varStatus="status">
				<p class="wrap">
					<c:out value="${description}" />
				</p>
				<c:if test="${not status.last}">
					<br/>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${not empty event.category}">
			<br />
			<h3><spring:message code="events.category" /></h3>
			<p class="wrap">
				<c:out value="${event.category.title}" />
			</p>
		</c:if>
		<c:if test="${not empty event.cost}">
			<br />
			<h3><spring:message code="events.cost" /></h3>
			<p class="wrap">
				<c:out value="${event.cost}" />
			</p>
		</c:if>
		<c:if test="${not empty event.contact}">
			<br />
			<h3><spring:message code="events.contact" /></h3>
			<p class="wrap">
				<c:choose>
					<c:when test="${not empty event.contactEmail}">
						<c:url var="email" value="mailto:${event.contactEmail}" />
						<a href="${email}"><c:out value="${event.contact}" /> </a>
					</c:when>
					<c:otherwise>
						<c:out value="${event.contact}" />
					</c:otherwise>
				</c:choose>
			</p>
		</c:if>
		<c:if test="${not empty event.otherInfo}">
			<br />
			<h3><spring:message code="events.otherInfo" /></h3>
			<c:forEach var="otherInfo" items="${event.otherInfo}">
				<c:if test="${not empty otherInfo}">
					<c:forEach var="info" items="${otherInfo}">
						</p>
					</c:forEach>
					<br />
					<p class="wrap">
					<c:out value="${info}" />
			</c:if>
			</c:forEach>
		</c:if>
	</kme:content>
</kme:page>
