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
<%@ page language="java" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="alerts.title" var="msgCat_Title"/>
<spring:message code="alerts.noAlerts" var="msgCat_NoAlerts"/>



var alerts = angular.module("alertsApp", ['ngSanitize','ui.bootstrap']);

alerts.controller("AlertsController", function($scope,$http,$templateCache,$location,$sce,$log,$filter) {

    $scope.init = function() {
        $scope.alertsToolTitle = "<c:out value="${msgCat_Title}"/>";
	    $scope.pageTitle = $scope.alertsToolTitle;
        $scope.campusCode = "<c:out value="${campus}"/>";
	    $scope.loadAlerts();
        $scope.updateLastReadTimestamp();
	    
	    var menuItems = "{\"menus\": ["+
           <%-- "{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }
    
    $scope.kmeNavLeft = function() {
        window.history.back();
    }
    
    $scope.loadAlerts = function(){
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/alerts/byCampus/'+$scope.campusCode+'?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
            }
            for(var i=0;i<data.alert.length;i++){
            	(data.alert[i].type == "Normal") ? data.alert[i].isNormal=true : data.alert[i].isNormal=false;
            	(data.alert[i].type == "Information") ? data.alert[i].isInformation=true : data.alert[i].isInformation=false;
            	(data.alert[i].type == "Caution") ? data.alert[i].isCaution=true : data.alert[i].isCaution=false;
            	(data.alert[i].type == "Emergency") ? data.alert[i].isEmergency=true : data.alert[i].isEmergency=false;
            }
            $scope.alerts = data.alert;
        }).error(function(data, status, headers, config) {
            $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
        });
    }

    $scope.updateLastReadTimestamp = function() {
        if( localStorage ) {
            var todayAsString = $filter('date')(new Date(), "yyyy-MM-dd HH:mm:ss");
            localStorage.setItem("last_alert",todayAsString);
        } else {
            $log.error("Local storage is unavailable on this browser.");
        }
    }
    
    $scope.alertClick = function(obj) {
        window.location = obj.url;
    }
     
});