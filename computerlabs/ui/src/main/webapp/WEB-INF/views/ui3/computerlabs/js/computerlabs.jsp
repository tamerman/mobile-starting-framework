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
<spring:message code="computerlabs.title" var="labToolTitle"/>
<spring:message code="computerlabs.noLabInfo" var="labNoInfo"/>
<spring:message code="computerlabs.noLabsFound" var="labNoneFound"/>
<spring:message code="computerlabs.noSeatsFound" var="labNoSeats"/>
<spring:message code="computerlabs.seats" var="labSeats"/>
<spring:message code="computerlabs.all" var="labAll"/>

var labs = angular.module("computerlabsApp", ['ngRoute','ngSanitize','ui.bootstrap'],
    function($routeProvider,$locationProvider) {
        $locationProvider.html5Mode(false);
        $routeProvider.
                when('/',       {  templateUrl: '${pageContext.request.contextPath}/computerlabs/templates/labGroups',  controller: 'ComputerlabsController' }).
                when('/groups', {  templateUrl: '${pageContext.request.contextPath}/computerlabs/templates/labGroups',  controller: 'ComputerlabsController' }).
                when('/labs',   {  templateUrl: '${pageContext.request.contextPath}/computerlabs/templates/labList',    controller: 'ComputerlabsListController' }).
                when('/detail', {  templateUrl: '${pageContext.request.contextPath}/computerlabs/templates/labDetail',  controller: 'ComputerlabsDetailController' }).
                otherwise({ redirectTo: "/" });
});

labs.factory('LabData',function(){
    return {
        labGroups:null,
        currentLabGroup:null,
        currentLab:null,
        errors:[],
        alerts:[],
        infos:[],
        successes:[],
        pageTitle:null,
        configGroupLabs:<c:out value="${groupLabs}"/>,
        configUseMaps:<c:out value="${useMaps}"/>,
        configUseDetail:<c:out value="${useDetail}"/>,
        configGroupSeats:<c:out value="${groupSeats}"/>,
        configFeedStats:<c:out value="${feedStatus}"/>,

        labToolTitle:"<c:out value="${labToolTitle}"/>",
        labNoInfo:"<c:out value="${labNoInfo}"/>",
        labNoneFound:"<c:out value="${labNoneFound}"/>",
        labNoSeats:"<c:out value="${labNoSeats}"/>",
        labSeats:"<c:out value="${labSeats}"/>",
        labAll:"<c:out value="${labAll}"/>"
    };
});

labs.controller("ComputerlabsController", function($scope,$http,$templateCache,$location,$sce,$log,LabData) {

    $scope.init = function() {
        $scope.LabData = LabData;
        LabData.errors = [];
        LabData.alerts = [];
        LabData.infos = [];
        LabData.successes = [];
        LabData.pageTitle = LabData.labToolTitle;
        $scope.loadGroups();

        var menuItems = "{\"menus\": ["+
            "{ \"url\":\"/computerlabs\" , \"label\":\"<c:out value="${labToolTitle}"/>\" },"+
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
        // send to home screen.
        LabData.errors = [];
        LabData.alerts = [];
        LabData.infos = [];
        LabData.successes = [];
        window.history.back();
    }
    
    $scope.clickHideErrors = function() {     
   		LabData.errors = [];
	}
	
	$scope.clickHideAlerts = function() {
		LabData.alerts = [];
	}
	
	$scope.clickHideInfos = function() {
		LabData.infos = [];
	}
	
	$scope.clickHideSuccesses = function() {
		LabData.successes = [];
	}

    $scope.loadGroups = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/computerlabs/getLabGroups?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                LabData.errors = eval('({"error":[{"id":'+status+',"name":"'+LabData.labNoInfo+'"}]})');
            }
            LabData.labGroups = data;
        }).error(function(data, status, headers, config) {
            LabData.errors = eval('({"error":[{"id":'+status+',"name":"'+LabData.labNoInfo+'"}]})');
        });
    }

    $scope.labGroupClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            LabData.errors = eval('({"error":[{"id":5000,"name":"'+LabData.labNoInfo+'"}]})');
            $log.error("No lab group object passed to labGroupClick.");
        } else {
        	if (typeof obj.locations === 'undefined') {
        		LabData.errors = eval('({"error":[{"id":5000,"name":"'+LabData.labNoneFound+'"}]})');
        	} else {
	            LabData.currentLabGroup = obj;
	        }
        }
    }

});

labs.controller("ComputerlabsListController", function($scope,$http,$templateCache,$location,$sce,$log,LabData) {

    $scope.init = function() {
        $scope.LabData = LabData;
		LabData.errors = [];
        LabData.alerts = [];
        LabData.infos = [];
        LabData.successes = [];
        LabData.pageTitle = LabData.currentLabGroup.name;
        if( 'undefined' === typeof LabData.pageTitle
            || '' == LabData.pageTitle ) {
            LabData.pageTitle = LabData.labToolTitle;
        }
    }

    $scope.labDetailClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            LabData.errors = eval('({"error":[{"id":5000,"name":"'+LabData.labNoInfo+'"}]})');
            $log.error("No lab group object passed to labDetailClick.");
        } else if( LabData.configUseMaps == 'true' ) {
            if( 'undefined' === typeof (obj.buildingCode) ) {
                LabData.errors = eval('({"error":[{"id":5000,"name":"'+LabData.labNoInfo+'"}]})');
            } else {
                window.location='<c:out value="${pageContext.request.contextPath}"/>/maps?id='+obj.buildingCode;
            }
        } else {
            LabData.currentLab = obj;
        }
    }
});

labs.controller("ComputerlabsDetailController", function($scope,$http,$templateCache,$location,$sce,$log,LabData) {

    $scope.init = function() {
        $scope.LabData = LabData;
        LabData.errors = [];
        LabData.alerts = [];
        LabData.infos = [];
        LabData.successes = [];
        LabData.pageTitle = LabData.currentLab.name;
        if( 'undefined' === typeof LabData.pageTitle
                || '' == LabData.pageTitle ) {
            LabData.pageTitle = LabData.labToolTitle;
        }
    }

});
