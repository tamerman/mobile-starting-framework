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
