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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<kme:page title="${route.name}" id="bus-webapp" backButton="true" homeButton="true" cssFilename="bus">
	<kme:content>
		<kme:listView id="busRoute" dataTheme="c" dataDividerTheme="b" filter="false">
		<c:choose>
			<c:when test="${not empty route.stops}">
			<c:forEach items="${route.stops}" var="stop">
				<kme:listItem>
					<c:url var="url" value="/bus/viewStop">
                        <c:param name="routeId" value="${route.id}"></c:param>
						<c:param name="stopId" value="${stop.id}"></c:param>
						<c:param name="campus" value="${campus}"></c:param>
					</c:url>
					<a href="${url}">
						<h3>
							<c:out value="${stop.name}" />
						</h3></a>
				</kme:listItem>
			</c:forEach>
			</c:when>
			<c:otherwise>
    		<kme:listItem>
			   There are no buses running at this time.
			</kme:listItem>			
			</c:otherwise>
			</c:choose>
		</kme:listView>

	</kme:content>
</kme:page>
