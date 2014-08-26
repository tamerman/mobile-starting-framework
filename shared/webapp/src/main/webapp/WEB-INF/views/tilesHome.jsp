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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="toolContainer">
  <c:forEach items="${tools}" var="homeTool" varStatus="status">
	<a class="tool" href="${homeTool.tool.url}" id="${status.index}">
	<div class="toolDetail">
		<img src="${pageContext.request.contextPath}/images/pixel.png" style="background-image:url('${homeTool.tool.iconUrl}')" /> 
		<span><spring:message code="${homeTool.tool.title}" /></span>
	</div>
	<c:if test="${not empty homeTool.tool.badgeCount}">
		<div class="toolBadge">
		  <span class="countBadge ui-btn-up-c ui-btn-corner-all">${homeTool.tool.badgeCount}</span>
		</div>
	</c:if>
	</a>
	<%-- Sum the badge counts. --%>
	<c:set var="bCount" value="${bCount + homeTool.tool.badgeCount}" />
  </c:forEach>

	<%-- Show the about if required --%>
	<c:if test="${showAbout == true}">
		<a class="tool" href="about">
			<div class="toolDetail">
				<img src="${pageContext.request.contextPath}/images/pixel.png" style="background-image:url('images/service-icons/srvc-package.png')" /> 
				<span><spring:message code="mdot.aboutLabel" /></span>
			</div>
		</a>
	</c:if>
</div>
