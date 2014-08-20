<%--
Copyright 2014 The Kuali Foundation Licensed under the Educational
Community License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License
at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
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