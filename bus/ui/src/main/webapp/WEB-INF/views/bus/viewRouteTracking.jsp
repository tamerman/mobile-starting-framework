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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="bus.title.map" var="mapTitle"/>
<spring:message code="bus.label.map" var="mapLabel"/>
<spring:message code="bus.label.routes" var="routesLabel"/>
<spring:message code="bus.label.favorites" var="favoritesLabel"/>
<spring:message code="bus.label.nearbystops" var="nearbystopsLabel"/>

<kme:page title="${mapTitle}" id="bus-webapp" backButton="true" homeButton="true" cssFilename="bus">
	<kme:content>
		<kme:listView id="busRouteList"  filter="false">
	        <c:choose>
				<c:when test="${not empty routes}">
					<c:if test="${fn:length(routes) > 1}">
						<kme:listItem>
							<c:url var="url" value="/bus/viewBusTracking">
								<c:param name="routeId" value="ALL"></c:param>
							</c:url>
							<a href="${url}"><img class="ui-li-icon" src="${imageKey}"/>
					 			<h3>
					     			<c:out value="All Routes" />
					 			</h3>
							</a>
						</kme:listItem>
					</c:if>
					<c:forEach items="${routes}" var="route">
						<kme:listItem>
							<c:url var="url" value="/bus/viewBusTracking">
								<c:param name="routeId" value="${route.id}"></c:param>
							</c:url>
							<c:set var="imageKey"><c:out value="${pageContext.request.contextPath}/images/bus-icons/bus-${fn:toUpperCase(route.color)}-36x36.png" /></c:set>
							<a href="${url}"><img class="ui-li-icon" src="${imageKey}"/>
					 			<h3>
					     			<c:out value="${route.name}" />
					 			</h3>
							</a>
						</kme:listItem>
					</c:forEach>
				</c:when>
				<c:otherwise>
	    		    <kme:listItem>
						No results found.
					</kme:listItem>
				</c:otherwise>
			</c:choose>
		</kme:listView>
	</kme:content>
	<c:set var="fileName" value="map"/>
	<%@ include file="footer.jsp" %>
</kme:page>