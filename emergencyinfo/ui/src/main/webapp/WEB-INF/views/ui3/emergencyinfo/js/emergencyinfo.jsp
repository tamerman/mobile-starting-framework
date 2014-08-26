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