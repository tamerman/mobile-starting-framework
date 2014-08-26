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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="library.title" 			var="msgCat_Title" />
<kme:page  title="${msgCat_Title}"
	id="library-ui" 
	backButton="true" 
	homeButton="true"
	cssFilename="library">
	<kme:content>
		<kme:listView id="hours">
			<kme:listItem dataRole="list-divider">
			${library.name}
			<c:if test="${isAdmin}">
				(<a href="${pageContext.request.contextPath}/library/editHours/${library.id}">Edit</a>)
			</c:if>
			</kme:listItem>
			<c:forEach var="hourSet" items="${hourSets}">
				<spring:message code="${hourSet.period.label}" var="msgCat_Period" />
				<kme:listItem dataRole="list-divider" dataTheme="b">${msgCat_Period}</kme:listItem>
				<c:forEach var="hour" items="${hourSet.hours}">
						<kme:listItem>
						<div class="ui-grid-a">
						<%-- The label for the hour --%>
						<spring:message code="${hour.displayLabel}" var="msgCat_HourLabel" />
						<div class="ui-block-a">${msgCat_HourLabel}</div>
						<div class="ui-block-b">
						<%-- The value for the hour --%>
						<c:choose>
							<c:when test="${empty hour.fromTime or empty hour.toTime}">
								<c:choose>
									<c:when test="${hour.dayOfWeek == 8}">
										<spring:message code="library.hours.public.closed" />
									</c:when>
									<c:otherwise>
										<spring:message code="library.hours.closed" />
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<fmt:formatDate pattern="H:mm" value="${hour.fromTime}" /> - <fmt:formatDate pattern="H:mm" value="${hour.toTime}" />
							</c:otherwise>
						</c:choose>
						</div>
						</div>
						</kme:listItem>
				</c:forEach>
			</c:forEach>
		</kme:listView>
	
		<c:if test="${not empty libraryContactDetails and not empty libraryContactDetails.generalInfoDesk}">
			<div data-role="content">
				<label class="itemLabel"><spring:message code="library.hours.more.info" /></label>
				<a class="itemValue" href="tel:${libraryContactDetails.generalInfoDesk}">${libraryContactDetails.generalInfoDesk}</a>
			</div>
		</c:if>
	</kme:content>
</kme:page>