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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
	<title>Publishing :: Edit News Source</title>
</head>
<body>
	<h2>Edit News Source</h2>
	<form:form action="${pageContext.request.contextPath}/publishing/news/edit" commandName="source" data-ajax="false" method="post">
		<form:hidden path="id"/>
	    <form:hidden path="versionNumber"/>
	    <form:hidden path="order"/>
		<fieldset>
			<table>
				<tr>
					<th><label for="name">Name</label></th>
					<td><form:input path="name" size="80" /></td>
					<td><form:errors path="name" /></td>
				</tr>
				<tr>
					<th><label for="url">Url</label></th>
					<td><form:input path="url" size="80" /></td>
					<td><form:errors path="url" /></td>
				</tr>
				<tr>
					<th><label for="active">Active</label></th>
					<td><form:checkbox path="active" id="active"/></td>
				</tr>
			</table>
		</fieldset>
		<a href="${pageContext.request.contextPath}/publishing/news">Cancel</a>
		<input type="submit" value="Save" />
	</form:form>
</body>
</html>
