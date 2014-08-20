<%--
Copyright 2014 The Kuali Foundation Licensed under the Educational
Community License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License
at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
--%>
<%@ page language="java" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="weather.title" var="msgCat_ToolTitle"/>
<spring:message code="weather.forecastPage.label.currentConditions" var="msgCat_CurrentConditions"/>
<spring:message code="weather.forecastPage.label.currentTemperature" var="msgCat_CurrentTemperature"/>
<spring:message code="weather.forecastPage.label.humidity" var="msgCat_Humidity"/>
<spring:message code="weather.forecastPage.label.wind" var="msgCat_Wind"/>
<spring:message code="weather.forecastPage.label.pressure" var="msgCat_Pressure"/>
<spring:message code="weather.forecastPage.label.forecast" var="msgCat_Forecast" />
<spring:message code="weather.forecastPage.label.alerts" var="msgCat_Alerts"/>



var weather = angular.module("weatherApp", ['ngSanitize','ui.bootstrap']).
	filter('toCelsius', function(){
		return function(input, places){
			return weather.convertToCelsius(input, places);
		}
	});

weather.convertToCelsius = function(input, places){
	return ((input - 32) * 5 / 9).toFixed(places);
};

weather.controller("WeatherController", function($scope,$http,$templateCache,$location,$sce,$log) {

    $scope.init = function() {
        $scope.weatherToolTitle = "<c:out value="${msgCat_ToolTitle}"/>";
	    $scope.pageTitle = $scope.weatherToolTitle;
	    $scope.loadWeather();
	    
        var menuItems = "{\"menus\": ["+
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");	    
	    
    }
    
    $scope.kmeNavLeft = function() {
        window.history.back();
    }
    
    $scope.loadWeather = function(){
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/weather/getWeatherForecast?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
            }
            $scope.weather = data.weather;
        }).error(function(data, status, headers, config) {
            $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
        });
    }    
});