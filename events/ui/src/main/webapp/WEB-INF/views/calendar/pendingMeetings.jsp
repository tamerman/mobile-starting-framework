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
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Calendar</title>
<link href="${pageContext.request.contextPath}/css/jquery.mobile-1.0b1.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/events.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mobile-1.0b1.min.js"></script>
</head>

<body>
	<script type="text/javascript">
		calendarSelectedDate = ${selectedDate};
		calendarSelectedMonthYear = ${monthYear};
	</script>
	<div data-role="page" id="Calendar-Events">
  <div data-role="header"><a href="${pageContext.request.contextPath}/calendar/options" data-icon="back" data-iconpos="notext">options</a>
    <h1>Pending Meetings</h1>
    <a href="${pageContext.request.contextPath}/home" data-icon="home" data-iconpos="notext">home</a>
  </div>
		<!-- /header -->
		<div data-role="content">
			<ul data-role="listview" data-theme="g">
				<c:forEach var="meeting" items="${pendingMeetings}">
					<li><c:choose>
							<c:when test="${not empty meeting.seriesId}">
								<a href="${pageContext.request.contextPath}/calendar/invite?eventId=${meeting.eventId}&referer=pending&seriesId=${meeting.seriesId}&date=${meeting.date}">
									<h3 style="white-space: normal">
										<c:out value="${meeting.status}" />: <c:out value="${meeting.title}" />
									</h3>
									<p style="white-space: normal">
										From <c:out value="${meeting.sentBy}" /> sent on <c:out value="${meeting.sentOn}" />
									</p>
								 </a>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/calendar/invite?eventId=${meeting.eventId}&referer=pending">
									<h3 style="white-space: normal">
										<c:out value="${meeting.status}" />: <c:out value="${meeting.title}" />
									</h3>
									<p style="white-space: normal">
										From <c:out value="${meeting.sentBy}" /> sent on <c:out value="${meeting.sentOn}" />
									</p>
								</a>
							</c:otherwise>
						</c:choose>
					</li>
				</c:forEach>
				<c:if test="${empty pendingMeetings}">
					<li>
						<h3>You have no pending meetings.</h3>
					</li>
				</c:if>
			</ul>
		</div>
		<div data-role="footer" data-id="events-footer" data-position="fixed" role="contentinfo" data-theme="b"></div>

	</div>
	<!-- /stc -->

	<!-- /page -->

</body>
</html>