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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="events.title.byRange" var="msgCat_ToolTitle"/>
<spring:message code="events.selectDateRange" var="selectDateRangeTitle"/>
<spring:message code="events.search" var="searchLabel"/>
<spring:message code="events.from" var="fromLabel"/>
<spring:message code="events.to" var="toLabel"/>
<kme:page title="${msgCat_ToolTitle}" id="events" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/home">
	<kme:content>
		<kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">
		<kme:listItem dataRole="list-divider">${selectDateRangeTitle}</kme:listItem>
		<div id="dateHeading">
			<!--							 From Date							   -->
			<h3>${fromLabel}: </h3>
			<input name="todate" id="todate" var="todate" value="" type="date"/>

			<!--							 From Date							   -->
			<h3>${toLabel}:</h3>
			<input name="dynstart" id="dynstart" type="date" id="dynstart" onclick="pageinit();" />

		</div>
		<c:url var="uffrl" value="/events/viewEventsByDateFromTo">
			<c:param name="dateFrom" value="${todate.value}"></c:param>
			<c:param name="dateTo" value="${dynstart.value}"></c:param>
		</c:url>
			<a onclick="url()" data-role="button">${searchLabel}</a>
		</kme:listView>
	</kme:content>
	<c:if test="${showCategoryTab == true || showDateRangeTab == true}">
	<kme:tabBar id="tabNav" showIcons="false" footer="true">
		<c:if test="${showCategoryTab == true}">
		<kme:tabBarItem url="${pageContext.request.contextPath}/events/byCategory"><spring:message code="events.byCategory" /></kme:tabBarItem>
		</c:if>
		<kme:tabBarItem url="${pageContext.request.contextPath}/events"><spring:message code="events.byDate" /></kme:tabBarItem>
		<c:if test="${showDateRangeTab == true}">
		<kme:tabBarItem url="${pageContext.request.contextPath}/events/byDateRange" selected="true"><spring:message code="events.byRange" /></kme:tabBarItem>
		</c:if>
	</kme:tabBar>
	</c:if>
<script type="text/javascript">
$('#dynstart').bind('click', function(event) {
		var defaultPickerValue = startDate().split("-", 10);
		var defaultPickerValue = [defaultPickerValue[0], defaultPickerValue[1]-1, defaultPickerValue[2]];
		var presetDate = new Date(defaultPickerValue[0], defaultPickerValue[1], defaultPickerValue[2], 0, 0, 0, 0);
		var todaysDate = new Date();
		var lengthOfDay = 24 * 60 * 60 * 1000;
		var diff = parseInt((((presetDate.getTime() - todaysDate.getTime()) / lengthOfDay))*-1,10);
		$('#dynstart').data('datebox').options.defaultValue = defaultPickerValue;
		$('#dynstart').data('datebox').options.minDays = diff;
});
function startDate(){
	return $('input#todate').val();
}
function url() {
	document.location.href = '${pageContext.request.contextPath}/events/viewEventsByDateFromTo?dateFrom='+$('input#todate').val()+'&dateTo='+$('input#dynstart').val();
}
</script>
</kme:page>
