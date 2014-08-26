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
