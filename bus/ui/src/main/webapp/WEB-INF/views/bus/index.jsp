<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="bus.title.routes" var="routesTitle"/>
<spring:message code="bus.label.map" var="mapLabel"/>
<spring:message code="bus.label.routes" var="routesLabel"/>
<spring:message code="bus.label.favorites" var="favoritesLabel"/>
<spring:message code="bus.label.nearbystops" var="nearbystopsLabel"/>

<kme:page title="${routesTitle}" id="bus-webapp" backButton="true" homeButton="true" cssFilename="bus">
	<kme:content>
	        <kme:listView id="busRouteList"  filter="false">
	        <c:choose>
			<c:when test="${not empty routes}">
			<c:forEach items="${routes}" var="route">
				<kme:listItem>
					<c:url var="url" value="/bus/viewRoute">
						<c:param name="routeId" value="${route.id}"></c:param>
						<c:param name="campus" value="${campus}"></c:param>
					</c:url>
					<c:set var="color"><c:out value="${route.color}" /></c:set>
					<c:set var="imageKey"><c:out value="images/bus-icons/bus-${fn:toUpperCase(route.color)}-36x36.png" /></c:set>
					<a href="${url}"><img class="ui-li-icon" src="${imageKey}"/>
					 <h3>
					     <c:out value="${route.name}" />
					 </h3>
					</a>
				</kme:listItem>
			</c:forEach>
			</c:when>
			<c:otherwise>
    		         <kme:listItem>No results found.</kme:listItem>
			</c:otherwise>
			</c:choose>
		</kme:listView>
	</kme:content>
	<c:set var="pageName" value="routes"/>
	<%@ include file="footer.jsp" %>
</kme:page>
