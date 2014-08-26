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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div ng-controller="NoOpCtrl">
<c:forEach var="hourSet" items="${hourSets}">
	<div class="panel panel-primary">
		<div class="panel-heading"><spring:message code="${hourSet.period.label}"/></div>
		<div class="panel-body">
		<c:forEach var="hour" items="${hourSet.hours}">
			<div class="row">
				<div class="col-xs-8 col-md-4"><spring:message code="${hour.displayLabel}" /></div>
				<div class="col-xs-4 col-md-8">
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
		</c:forEach>
	</div>
    </div>
</c:forEach>

<c:if test="${not empty libraryContactDetails and not empty libraryContactDetails.generalInfoDesk}">
   	<div class="alert alert-info">
        <strong><spring:message code="library.hours.more.info" /></strong>
        <a class="itemValue" href="tel:${libraryContactDetails.generalInfoDesk}">${libraryContactDetails.generalInfoDesk}</a>
    </div>
</c:if>
    <c:if test="${isAdmin}">
    <div class="alert alert-info">
        <a href="#/editHours/${library.id}" class="btn btn-primary"><spring:message code="library.edit" /></a>
    </div>
    </c:if>
</div>