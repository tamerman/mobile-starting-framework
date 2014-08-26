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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<kme:tabBar id="tabNav" showIcons="true" footer="true">
	<c:forEach items="${taborder}" var="tabName">
		<c:set var="isSelected" value="false"/>
		<c:if test="${pageName == tabName}">
			<c:set var="isSelected" value="true"/>
		</c:if>
		<c:choose>
			<c:when test="${tabName == 'map'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${mapLabel}</kme:tabBarItem>
			</c:when>
			<c:when test="${tabName == 'stops'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${stopsLabel}</kme:tabBarItem>
			</c:when>
			<c:when test="${tabName == 'favorites'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${favoritesLabel}</kme:tabBarItem>
			</c:when>
			<c:when test="${tabName == 'nearbystops'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${nearbystopsLabel}</kme:tabBarItem>
			</c:when>
		</c:choose>
	</c:forEach>
</kme:tabBar>
