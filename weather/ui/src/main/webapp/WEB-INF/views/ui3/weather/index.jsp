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
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:message code="weather.title" var="msgCat_ToolTitle"/>
<spring:message code="weather.forecastPage.label.currentConditions" var="msgCat_CurrentConditions"/>
<spring:message code="weather.forecastPage.label.currentTemperature" var="msgCat_CurrentTemperature"/>
<spring:message code="weather.forecastPage.label.humidity" var="msgCat_Humidity"/>
<spring:message code="weather.forecastPage.label.wind" var="msgCat_Wind"/>
<spring:message code="weather.forecastPage.label.pressure" var="msgCat_Pressure"/>
<spring:message code="weather.forecastPage.label.forecast" var="msgCat_Forecast" />
<spring:message code="weather.forecastPage.label.alerts" var="msgCat_Alerts"/>

<kme3:page title="${msgCat_ToolTitle}" toolName="weather" ngAppName="weatherApp" ngControllerName="WeatherController" ngInitFunction="init" backFunction="kmeNavLeft"  cssFilename="weather" jsFilename="weather">

<div id="theContentArea" class="main-view" ng-view="">
	<div class="alert alert-danger" ng-repeat="error in errors.error">
		{{error.name}}
	</div>
	<div>
	    <div class="list-group-item list-header">${msgCat_CurrentConditions}</div>
	    <div class="list-group-item">
	        <p>{{weather.currentCondition}}</p>
	        <p>${msgCat_CurrentTemperature} : {{weather.temperature}}&deg;F / {{weather.temperature | toCelsius:2}}&deg;C</p>
	        <p>${msgCat_Humidity} : {{weather.humidity}}%</p>
	        <p>${msgCat_Wind} : {{weather.windSpeed}} {{weather.windDirection}}</p>
	        <p>${msgCat_Pressure} : {{weather.pressure}} in. Hg</p>
	    </div>

        <div class="list-group-item list-header">${msgCat_Forecast}</div>
        <div ng-repeat="dailyForecast in weather.dailyForecasts">
            <div class="list-group-item forecast-list-item">
                <img class="weather" ng-src="{{dailyForecast.iconLink}}" />
                <div class="forecastItem">
                    <h3>{{dailyForecast.name}}</h3>
                    <p>{{dailyForecast.text}}</p>
                </div>
            </div>
        </div>
	</div>
</div>

</kme3:page>
