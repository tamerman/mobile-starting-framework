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