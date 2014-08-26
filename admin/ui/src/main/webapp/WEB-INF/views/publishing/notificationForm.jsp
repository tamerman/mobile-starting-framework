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

<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
	<title>Publishing :: Notification</title>
</head>
<body>
<h2>Notification</h2>
<form:form action="${pageContext.request.contextPath}/publishing/notificationSubmit" commandName="notification" data-ajax="false" method="post">
    <form:hidden path="notificationId"/>
    <form:hidden path="versionNumber"/>
	<fieldset>
	<label for="title">Title:</label>
	<form:input path="title" /><br/>
	<form:errors path="title" />
	</fieldset>
	<fieldset>
	<label for="message">Message:</label>
	<form:input path="message" /><br/>
	<form:errors path="message" />
	</fieldset>
	<fieldset>
	<label for="primaryCampus">Campus:</label>
	<form:input path="primaryCampus" /><br/>
	<form:errors path="primaryCampus" />
	</fieldset>
	<fieldset>
	<label for="startDate">Start date:</label>
   	<form:input path="startDate" /><br/>
	<form:errors path="startDate" />
	</fieldset>
	<fieldset>
	<label for="endDate">End date:</label>
	<form:input path="endDate" /><br/>
	<form:errors path="endDate" />
	</fieldset>
	<fieldset>
	<label for="notificationType">Type:</label>
	<form:input path="notificationType" /><br/>
	<form:errors path="notificationType" />
	</fieldset>
	<a href="${pageContext.request.contextPath}/publishing/notifications">Cancel</a>
	<input type="submit" value="Submit" />
</form:form>
</body>
</html>
