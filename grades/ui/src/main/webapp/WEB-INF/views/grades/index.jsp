<%--
  Copyright 2011-2012 The Kuali Foundation Licensed under the Educational Community
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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- Calendar labels messages --%>
<spring:message code="grades.calendar.select" 				var="msgCat_CalSelect"/>
<spring:message code="grades.calendar.today" 				var="msgCat_CalToday"/>
<spring:message code="grades.calendar.selectDate" 			var="msgCat_CalSelectDate"/>
<spring:message code="grades.calendar.selectTime" 			var="msgCat_CalSelectTime"/>
<spring:message code="grades.calendar.daysOfWeek" 			var="msgCat_CalDaysOfWeek"  htmlEscape="false"/>
<spring:message code="grades.calendar.daysOfWeekShort"	 	var="msgCat_CalDaysOfWeekShort" htmlEscape="false"/>
<spring:message code="grades.calendar.monthsOfYear" 		var="msgCat_CalMonthsOfYear" htmlEscape="false"/>
<spring:message code="grades.calendar.monthsOfYearShort" 	var="msgCat_CalMonthsOfYearShort" htmlEscape="false"/>
<spring:message code="grades.calendar.durationLabel" 		var="msgCat_CalDurationLabel"  htmlEscape="false"/>
<spring:message code="grades.calendar.durationDays" 		var="msgCat_CalDurationDays" htmlEscape="false"/>
<spring:message code="grades.calendar.tooltip" 				var="msgCat_CalTooltip"/>
<spring:message code="grades.calendar.nextMonth" 			var="msgCat_CalNextMonth"/>
<spring:message code="grades.calendar.prevMonth" 			var="msgCat_CalPrevMonth"/>
<spring:message code="grades.calendar.clearButton" 			var="msgCat_CalClearButton"/>
<spring:message code="grades.calendar.meridiem"				var="msgCat_CalMeridiem"  htmlEscape="false"/>

<%-- Validation messages --%>
<spring:message code="grades.endDateGreater"				var="msgCat_ValEndate"/>
<spring:message code="grades.isMandatory"					var="msgCat_IsMandatory"/>

<spring:message code="grades.title"							var="msgCat_ToolTitle"/>
<kme:page 
	title="${msgCat_ToolTitle}" 
	id="grades-ui" 
	backButton="true"
	backButtonURL="${pageContext.request.contextPath}"
	homeButton="true" 
	cssFilename="grades" 
	jsFilename="Modernizr,grades">

	<kme:content>
		<script type="text/javascript"> 
		Modernizr.load([
           {
             test : Modernizr.inputtypes.time,
             nope : ["${pageContext.request.contextPath}/js/jqm-datebox.comp.datebox.js", 
                     "${pageContext.request.contextPath}/css/jqm-datebox.css"],
             complete : function () {
             	if (!Modernizr.inputtypes.time){
             		$(document).ready(function(){
             			
             			// Dont use mobile box on fake JQM
            			jQuery.extend(jQuery.mobile.datebox.prototype.options.lang, {
            				'${pageContext.request.locale.language}': {
            					setDateButtonLabel: "${msgCat_CalSelect}",
            					setTimeButtonLabel: "${msgCat_CalSelect}",
            					setDurationButtonLabel: "${msgCat_CalSelect}",
            					calTodayButtonLabel: "${msgCat_CalToday}",
            					titleDateDialogLabel: "${msgCat_CalSelectDate}",
            					titleTimeDialogLabel: "${msgCat_CalSelectTime}",
            					daysOfWeek: [${msgCat_CalDaysOfWeek}],
            					daysOfWeekShort: [${msgCat_CalDaysOfWeekShort}],
            					monthsOfYear: [${msgCat_CalMonthsOfYear}],
            					monthsOfYearShort: [${msgCat_CalMonthsOfYearShort}],
            					durationLabel: [${msgCat_CalDurationLabel}],
            					durationDays: [${msgCat_CalDurationDays}],
            					tooltip: "${msgCat_CalTooltip}",
            					nextMonth: "${msgCat_CalNextMonth}",
            					prevMonth: "${msgCat_CalPrevMonth}",
            					timeFormat: 24,
            					headerFormat: '%A, %B %-d, %Y',
            					dateFieldOrder: ['y','m', 'd'],
            					timeFieldOrder: ['h', 'i', 'a'],
            					slideFieldOrder: ['y', 'm', 'd'],
            					dateFormat: "%Y-%m-%d",
            					useArabicIndic: false,
            					isRTL: false,
            					calStartDay: 0,
            					clearButton: "${msgCat_CalClearButton}",
            					durationOrder: ['d', 'h', 'i', 's'],
            					meridiem: [${msgCat_CalMeridiem}],
            					timeOutput: "%k:%M",
            					durationFormat: "%Dd %DA, %Dl:%DM:%DS"
            				}
            			});
            			
            			jQuery.extend(jQuery.mobile.datebox.prototype.options, {
            				useLang: '${pageContext.request.locale.language}',
            				disableManualInput: true
            			});
             			
             			
         				$("input[data-role=datebox]").each(function() {
         					if ( typeof($(this).data('datebox')) === "undefined" ) {
         						$(this).datebox();
         					}
         				});
         			});
             	}
             }
           }
         ]);
		
		
			var msgCat_ValEndate = "${msgCat_ValEndate}";
			var validation_messages = {
				// Error messages for invalid heading
				startDate: {
					required: "${msgCat_IsMandatory}."
				},
				// Error messages for invalid article content
				endDate: {
					required: "${msgCat_IsMandatory}"
				}
			};
		</script>
		<form action="${pageContext.request.contextPath}/grades/viewResults" method="post" id="inputForm"  >
			<div>
				<label for="startDate"><spring:message code="grades.startDate" /></label>
				<input name="startDate" id="startDate" type="date" data-role="datebox" data-options='{"mode": "datebox","focusMode": true, "numberInputEnhance": true}'>
			</div>
			<div>
				<label for="endDate"><spring:message code="grades.endDate" /></label>
				<input name="endDate" id="endDate" type="date" data-role="datebox" data-options='{"mode": "datebox","focusMode": true, "numberInputEnhance": true}'>
			</div>
		</form>
		<ul data-role="listview" data-inset="true" data-theme="a">
			<li data-icon="search"><a href="#" id="btnSearch"><spring:message code="grades.search" /></a></li>
		</ul>
	</kme:content>
</kme:page>
