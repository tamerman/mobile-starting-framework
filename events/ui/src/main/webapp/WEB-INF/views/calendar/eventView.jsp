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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>


<kme:page title="Event" id="Calendar-Events" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/events">


	<kme:content>
			<ul data-role="listview" data-theme="g">
				<li>
					<h3 class="wrap">
						<c:out value="${event.title}" />
					</h3>
					<p class="wrap">
						<c:out value="${event.location}" />
					</p>
					<p class="wrap">
						<c:out value="${event.displayDate}" />
					</p></li>
				<li>
					<h3>Alert</h3>
					<p class="wrap">
						<c:choose>
							<c:when test="${not empty event.reminder}">
								<c:out value="${event.reminder}" /> before event.
                        </c:when>
							<c:otherwise>No Alert</c:otherwise>
						</c:choose>
					</p></li>
				<li>
					<h3>Availability</h3>
					<p class="wrap">
						<c:out value="${event.showAs}" />
					</p></li>
				<c:if test="${not empty event.category}">
					<li>
						<h3>Category</h3>
						<p class="wrap">
							<c:out value="${event.category}" />
						</p></li>
				</c:if>
				<c:if test="${not empty event.recurrenceMessage}">
					<li>
						<h3>Recurrence</h3>
						<p class="wrap">
							<c:out value="${event.recurrenceMessage}" />
						</p></li>
				</c:if>
				<li>
					<h3>Description</h3>
					<p class="wrap">
						<c:out value="${event.description}" />
					</p></li>
			</ul>
		</div>
		<div data-role="footer" data-id="events-footer" data-position="fixed" role="contentinfo" data-theme="b" class="ui-bar">

			<c:choose>
				<c:when test="${not empty event.seriesId}">
					<div data-role="controlgroup" data-type="horizontal">
						<c:if test="${event.meeting}">
							<a href="${pageContext.request.contextPath}/calendar/invite?eventId=${event.eventId}&seriesId=${event.seriesId}&date=${event.date}" data-role="button" data-ajax="false">invites</a>
						</c:if>
						<c:if test="${event.writeAccess}">
							<a href="${pageContext.request.contextPath}/calendar/editEvent?eventId=${event.eventId}&seriesId=${event.seriesId}&date=${event.date}" data-role="button" data-ajax="false">edit</a>
						</c:if>
						<a href="${pageContext.request.contextPath}/calendar/event?eventId=${event.seriesId}&occurrenceId=${event.eventId}&date=${event.date}" data-role="button" data-ajax="false">series</a>
						<c:if test="${event.writeAccess}">
							<a href="${pageContext.request.contextPath}/calendar/deleteEvent?eventId=${event.eventId}&seriesId=${event.seriesId}&date=${event.date}" data-role="button" data-ajax="false">delete</a>
						</c:if>
						<c:if test="${not empty event.oncourseSiteId}">
							<a href="${pageContext.request.contextPath}/myclasses/${event.oncourseSiteId}" data-role="button" data-ajax="false">course details</a>
						</c:if>
					</div>
				</c:when>
				<c:otherwise>
					<div data-role="controlgroup" data-type="horizontal">
						<c:if test="${event.meeting}">
							<a href="${pageContext.request.contextPath}/calendar/invite?eventId=${event.eventId}&occurrenceId=${occurrenceId}&occurrenceDate=${occurrenceDate}" data-role="button" data-ajax="false">invites</a>
						</c:if>
						<c:if test="${event.writeAccess}">
							<a href="${pageContext.request.contextPath}/calendar/editEvent?eventId=${event.eventId}" data-role="button" data-ajax="false">edit</a>
						</c:if>
						<c:if test="${not empty occurrenceId}">
							<a href="${pageContext.request.contextPath}/calendar/event?eventId=${event.eventId}&date=${occurrenceDate}" data-role="button" data-ajax="false">occurrence</a>
						</c:if>
						<c:if test="${event.writeAccess}">
							<a href="${pageContext.request.contextPath}/calendar/deleteEvent?eventId=${event.eventId}" data-role="button" data-ajax="false">delete</a>
						</c:if>
						<c:if test="${not empty event.oncourseSiteId}">
							<a href="${pageContext.request.contextPath}/myclasses/${event.oncourseSiteId}" data-role="button" data-ajax="false">course details</a>
						</c:if>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	 </kme:content></kme:page>