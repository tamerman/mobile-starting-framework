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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="weather.title" var="msgCat_ToolTitle"/>
<spring:message code="weather.forecastPage.label.currentConditions" var="msgCat_CurrentConditions"/>
<spring:message code="weather.forecastPage.label.currentTemperature" var="msgCat_CurrentTemperature"/>
<spring:message code="weather.forecastPage.label.humidity" var="msgCat_Humidity"/>
<spring:message code="weather.forecastPage.label.wind" var="msgCat_Wind"/>
<spring:message code="weather.forecastPage.label.pressure" var="msgCat_Pressure"/>
<spring:message code="weather.forecastPage.label.forecast" var="msgCat_Forecast" />
<spring:message code="weather.forecastPage.label.alerts" var="msgCat_Alerts"/>

<kme:page title="${msgCat_ToolTitle}" id="weather" backButton="true" homeButton="true" cssFilename="weather">
	<kme:content>
		<kme:listView id="currentConditionsList" dataTheme="c" dataDividerTheme="b" filter="false">
			<kme:listItem dataTheme="b" dataRole="list-divider">${msgCat_CurrentConditions}</kme:listItem>
			<kme:listItem>
				<h3 class="wrap">${forecast.currentCondition}</h3>
			</kme:listItem>
			<kme:listItem>
				<fmt:formatNumber var="celcius" value="${((forecast.temperature - 32) * 5 / 9)}" maxFractionDigits="0" />
				<h3 class="wrap">${msgCat_CurrentTemperature} : ${forecast.temperature}&deg;F / ${celcius}&deg;C</h3>
			</kme:listItem>
			<kme:listItem>
				<h3 class="wrap">${msgCat_Humidity} : ${forecast.humidity}%</h3>
			</kme:listItem>
			<kme:listItem>
				<h3 class="wrap">${msgCat_Wind} : ${forecast.wind} </h3>
			</kme:listItem>
			<kme:listItem>
				<h3 class="wrap">${msgCat_Pressure} : ${forecast.pressure} in. Hg</h3>
			</kme:listItem>
		
			<kme:listItem dataTheme="b" dataRole="list-divider">${msgCat_Forecast}</kme:listItem>
			<c:forEach items="${forecast.forecasts}" var="item" varStatus="status">
				<kme:listItem>
					<div class="forecastItem" style="background-image:url('${item.iconLink}')">
						<h3>${item.name}</h3>
						<p class="wrap">${item.text}</p>
					</div>
				</kme:listItem>
			</c:forEach>

			<kme:listItem dataTheme="b" dataRole="list-divider">${msgCat_Alerts}</kme:listItem>
            <kme:listItem hideDataIcon="true">
            	<a href="${alertsTool.url}" style="background-image: url('${alertsTool.iconUrl}');">
		      		<h3>${alertsTool.title}</h3>
		      		<p class="wrap">${alertsTool.description}</p>
		      		<c:if test="${not empty alertsTool.badgeCount}"> 
		      			<span class="countBadge ui-btn-up-c ui-btn-corner-all">${alertsTool.badgeCount}</span>
		      		</c:if>
		      	</a>
            </kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>
