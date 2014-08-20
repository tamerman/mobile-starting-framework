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