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

<!DOCTYPE html>
<html>
<head>
	<title>Publishing :: News</title>
</head>
<body>
	<h2>News</h2>
	<a href="${pageContext.request.contextPath}/publishing/news/add">add news feed</a><br /><br />
	<table>
		<tr>
			<th>Name</th><th>URL</th><th>Active</th><th>Actions</th>
		</tr>
		<c:forEach items="${sources}" var="feed" varStatus="status">
			<tr>
				<td>
					<c:out value="${feed.name}"/>
				</td>
				<td>
					<c:out value="${feed.url}"/>
				</td>
				<td>
					<c:out value="${feed.active}"/>
				</td>
				<td>
					<a href="${pageContext.request.contextPath}/publishing/news/edit/${feed.id}">edit</a>
					<a href="${pageContext.request.contextPath}/publishing/news/delete/${feed.id}">delete</a>
					<a href="${pageContext.request.contextPath}/publishing/news/up/${feed.id}">up</a>
					<a href="${pageContext.request.contextPath}/publishing/news/down/${feed.id}">down</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
