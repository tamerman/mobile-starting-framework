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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme:listView id="homeserviceslist" filter="false">
	<c:forEach items="${tools}" var="homeTool" varStatus="status">
		<kme:listItem hideDataIcon="true">
			<a href="${homeTool.tool.url}" style="background-image: url('${homeTool.tool.iconUrl}');">
				<h3><spring:message code="${homeTool.tool.title}" /></h3>
				<p class="wrap"><spring:message code="${homeTool.tool.description}" /></p> 
				<c:if test="${not empty homeTool.tool.badgeCount}">
					<span class="countBadge ui-btn-up-c ui-btn-corner-all">${homeTool.tool.badgeCount}</span>
				</c:if> 
				<c:if test="${not empty homeTool.tool.badgeText}">
					<span class="countBadge ui-btn-up-c ui-btn-corner-all">${homeTool.tool.badgeText}</span>
				</c:if>
			</a>
		</kme:listItem>
		<!-- Sum the badge counts. -->
		<c:set var="bCount" value="${bCount + homeTool.tool.badgeCount}" />
	</c:forEach>
	<c:choose>
		<c:when test="${showAbout == true}">	
		    <kme:listItem hideDataIcon="true" id="about">
   	    		<a href="about" class="about">
   	    			<h3>
		   	    		<spring:message code="mdot.aboutLabel" />
		   	    	</h3>
		   	    </a>		
		    </kme:listItem>
		</c:when>
	</c:choose>
</kme:listView>