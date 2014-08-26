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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<spring:message code="library.title" var="msgCat_ToolTitle" />
<kme:page 
	title="${msgCat_ToolTitle}" 
	id="library-ui" 
	backButton="true" 
	homeButton="true" 
	jsFilename="Modernizr,editHoursScreen">
	<kme:content>

		<form action="${pageContext.request.contextPath}/library/editHours/${libraryId}" method="POST" id="libraryHoursForm" >
		<c:forEach var="libraryHours" items="${hourSets}" varStatus="hs_status">
			<spring:message code="${libraryHours.period.label}" var="msgCat_hourSetName"/>
			<h2>${msgCat_hourSetName}</h2>
			<div data-role="collapsible-set" data-theme="c" data-content-theme="d" data-inset="false">
			 	
				<c:forEach var="libraryHours" items="${libraryHours.hours}" varStatus="h_status">
					<spring:message code="library.hours.${libraryHours.dayOfWeek }" var="msgCat_dayName"/>
					<div data-role="collapsible"  data-inset="true">
						<h2>${msgCat_dayName}</h2>
						<c:set var="hourGroupStyle" value=""/>
						<c:set var="closedAttributes" value=""/>
						<c:if test="${libraryHours.closed}">
							<c:set var="hourGroupStyle" value="display: none"/>
							<c:set var="closedAttributes" value="checked=\"checked\""/>
						</c:if>
						<%-- From --%>
						<div data-role="fieldcontain" id="s${hs_status.count}h${h_status.count}fromTimeGroup" style="${hourGroupStyle}">
							
							<label for="s${hs_status.count}h${h_status.count}fromTime">
								<spring:message code="library.hours.from" />
							</label>
							<input type="time" 
								data-role="datebox" 
								data-options='{"mode": "timebox","focusMode": true, "numberInputEnhance": true}' 
								id="s${hs_status.count}h${h_status.count}fromTime"
								name="s${hs_status.count}h${h_status.count}fromTime"
								value="<fmt:formatDate pattern="HH:mm" value="${libraryHours.fromTime}" />">
						</div>

						<%-- To --%>
						<div data-role="fieldcontain" id="s${hs_status.count}h${h_status.count}toTimeGroup" style="${hourGroupStyle}">
							<label for="s${hs_status.count}h${h_status.count}toTime">
								<spring:message code="library.hours.to" />
							</label>
							<input type="time" 
								data-role="datebox" 
								data-options='{"mode": "timebox","focusMode": true, "numberInputEnhance": true}' 
								id="s${hs_status.count}h${h_status.count}toTime"
								name="s${hs_status.count}h${h_status.count}toTime"
								value="<fmt:formatDate pattern="HH:mm" value="${libraryHours.toTime}" />">
						</div>
						<div data-role="fieldcontain">
								<input type="checkbox"
									name="s${hs_status.count}h${h_status.count}closed"
									id="s${hs_status.count}h${h_status.count}closed"
									value="<spring:message code="library.hours.closed" /> ?" 
									${closedAttributes}/>
								<label for="s${hs_status.count}h${h_status.count}closed"><spring:message code="library.hours.closed" /></label>
						</div>
			        </div>
			    </c:forEach>
		    </div>
		</c:forEach>
		</form>

		<!-- Begin action buttons -->
		<ul data-role="listview" data-inset="true" data-theme="a" id="actionButtons">
			<li><a href="#" id="btnSave" ><spring:message code="library.hours.save" /></a></li>
			<li><a href="${pageContext.request.contextPath}/library/viewHours?libraryId=${libraryId}" id="btnCancel" ><spring:message code="library.hours.cancel" /></a></li>
		</ul>
			
		<!-- End action buttons -->
	</kme:content>
</kme:page>	
		