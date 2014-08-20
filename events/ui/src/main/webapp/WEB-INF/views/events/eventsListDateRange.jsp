<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="events.title.byRange" var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="events" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/home">
	<kme:content>
		<kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">
			
			<c:forEach items="${eventDateFromTo}" var ="edft" varStatus="status">
				<div id="dateHeading">
					<!--							 Date								-->	
					<h3><c:out value="${edft.key}" /></h3>
				</div>				
				<c:forEach items="${edft.value}" var ="gbc" varStatus="status">
					<!--						 Category Header						-->
					<kme:listItem dataRole="list-divider">
						<c:out value="${gbc.key.title}" />
					</kme:listItem>	

					<c:forEach items="${gbc.value}" var ="gbcV" varStatus="status">
						<!--					 Event Information						-->
						<kme:listItem>
							<c:url var="url" value="/events/viewEvent">
								<c:param name="categoryId" value="${gbc.key.categoryId}"></c:param>
								<c:param name="campus" value="${campus}"></c:param>
								<c:param name="eventId" value="${gbcV.eventId}"></c:param>
							</c:url>
							<a href="${url}">
								<h3 class="wrap"><c:out value="${gbcV.title}" /></h3>
								<p style="white-space:pre-wrap"><c:out value="${gbcV.displayStartTime}" /> - <c:out value="${gbcV.displayEndTime}" /> | <c:out value="${gbcV.location}" /></p>
							</a>
						</kme:listItem>
					 </c:forEach>
				</c:forEach>
			</c:forEach>
		</kme:listView>
		<div data-role="navbar" data-position="fixed">
			<ul>
				<li><a href="${pageContext.request.contextPath}/events/byCategory"><spring:message code="events.byCategory" /></a></li>
				<li><a href="${pageContext.request.contextPath}/events"><spring:message code="events.byDate" /></a></li>
				<li><a href="${pageContext.request.contextPath}/events/byDateRange"><spring:message code="events.byRange" /></a></li>
			</ul>
	   </div><!-- /navbar -->
	</kme:content>	
</kme:page>

