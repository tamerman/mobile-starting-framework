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