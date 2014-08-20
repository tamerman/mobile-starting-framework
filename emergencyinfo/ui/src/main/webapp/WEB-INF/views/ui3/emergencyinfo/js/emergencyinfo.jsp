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

<spring:message code="emergencyinfo.title" var="msgCat_ToolTitle"/>
<spring:message code="emergencyinfo.true" var="msgCat_TrueEmer"/>
<spring:message code="emergencyinfo.phonenumbers" var="msgCat_Phone"/>
<spring:message code="emergencyinfo.noContacts" var="msgCat_NoContacts"/>



var emergencyinfo = angular.module("emergencyInfoApp", ['ngSanitize','ui.bootstrap']);

emergencyinfo.controller("EmergencyInfoController", function($scope,$http,$templateCache,$location,$sce,$log) {

    $scope.init = function() {
        $scope.emergencyinfoToolTitle = "<c:out value="${msgCat_ToolTitle}"/>";
        $scope.errors = null;
        $scope.infos = null;
        $scope.alerts = null;
        $scope.successes = null;
	    $scope.pageTitle = $scope.emergencyinfoToolTitle;
	    $scope.loadEmergencyInfo();
	    
	    var menuItems = "{\"menus\": ["+
            <%-- "{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+ --%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
	    
	    
    }
    
    $scope.kmeNavLeft = function() {
    	$scope.errors = [];
        $scope.infos = [];
        $scope.alerts = [];
        $scope.successes = [];
        window.history.back();
    }
    
    $scope.loadEmergencyInfo = function(){
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/emergency/information/bycampus/<c:out value="${campus}"/>?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                $scope.errors = eval('({"error":[{"id":' + status + ',"name":"No Data"}]})');
            } else {
	            for(var i=0; i < data.emergencyInfo.length; i++){
	            	(data.emergencyInfo[i].type == "PHONE") ? data.emergencyInfo[i].isPhone=true : data.emergencyInfo[i].isPhone=false;
	            	(data.emergencyInfo[i].type == "EMAIL") ? data.emergencyInfo[i].isEmail=true : data.emergencyInfo[i].isEmail=false;
	            }
	            $scope.emergencyinfos = data.emergencyInfo;
	        }
        }).error(function(data, status, headers, config) {
            $scope.errors = eval('({"error":[{"id":'+status+',"name":"No Data"}]})');
        });
    }   
        
    $scope.emergencyInfoClick = function(obj) {
        console.log(obj);
		if(obj.isPhone){
			window.location.href = "tel:"+ obj.link;
		}else if(obj.isEmail){
			window.location.href = "mailto:"+ obj.link;
		}
    }
    $scope.clickHideResults = function() {
        $scope.showResults = false;
        $scope.errors = [];
        $scope.infos = [];
        $scope.alerts = [];
        $scope.successes = [];
    }
});