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

<spring:message code="mdot.pageTitle" var="msgCat_ToolTitle"/>

var home = angular.module("homeApp", ['ngSanitize','ui.bootstrap']);

home.controller("homeController", function($scope,$http,$templateCache,$location,$sce,$log) {

    $scope.init = function() {
        $scope.homeToolTitle = "<c:out value="${msgCat_homeTitle}"/>";
	    $scope.pageTitle = $scope.homeToolTitle;
        $scope.lastAlertDate = null;
	    var hometools;
	    $scope.toggleCustomization=true;
	    if( localStorage
                && localStorage.getItem( "home_tools_data" ) != null ) {
            $log.debug("Local storage is available in the browser.");
            if(localStorage.getItem("homeTools.customization") != null ) {
            	$scope.toggleCustomization=localStorage.getItem( "homeTools.customization")=='false'?false:true;
            }
            hometools = JSON.parse(localStorage.getItem( "home_tools_data" ) );
            $scope.HomeTools = hometools;
        }
	    updateToolArrayOrder();
	    $scope.loadHome();
	    var menuItems = "{\"menus\": ["+
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
	    
    }
    
    $scope.kmeNavLeft = function() {
        window.history.back();
    }
    
    $scope.loadHome = function(){
        var hometools;
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/home/json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
            }
            $scope.HomeTools = data;
             if( localStorage)
             {
            	localStorage.setItem( "home_tools_data", JSON.stringify(data));
				updateToolArrayOrder();
			 }
			
        }).error(function(data, status, headers, config) {
            $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
        });
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/alerts/newest/<c:out value="${campus}"/>.json'
        })
        .success(
            function(data,status,headers,config) {
                $scope.lastAlertDate = data.lastAlertDate.date;
                updateToolArrayOrder();
            }
        )
        .error(
            function(data,status,headers,config) {
                $log.error("Unable to update alerts timestamp.");
            }
        );
    }  
        
    $scope.homeClick = function(obj) {
		if(obj.isDining){
			window.location.href = "dining:"+ obj.link;
		}else if(obj.isMaps){
			window.location.href = "maps:"+ obj.link;
		}
    }

    $scope.shouldMoveAlerts = function() {
        var shouldMove = false;
        if(  $scope.lastAlertDate != null
            && localStorage &&
                localStorage.getItem("last_alert") != null) {
            var lastCheck = localStorage.getItem("last_alert");
            var year = lastCheck.substring(0,3);
            var month = lastCheck.substring(5,6);
            var day = lastCheck.substring(8,9);
            var hour = lastCheck.substring(11,12);
            var minute = lastCheck.substring(14,15);
            var lastCheckDate = new Date(year,month,day,hour,minute,0,0);

            var year2 = $scope.lastAlertDate.substring(0,3);
            var month2 = $scope.lastAlertDate.substring(5,6);
            var day2 = $scope.lastAlertDate.substring(8,9);
            var hour2 = $scope.lastAlertDate.substring(11,12);
            var minute2 = $scope.lastAlertDate.substring(14,15);
            var lastDate = new Date(year2,month2,day2,hour2,minute2,0,0);
            <%--$log.debug("last checked: ["+lastCheckDate.getTime()+"]");--%>
            <%--$log.debug("last alert issued: ["+lastDate.getTime()+"]");--%>
            // For some reason when getting the time, the value is negative so the
            // logic needs to be flipped.
            if( lastDate.getTime() < lastCheckDate.getTime() ) {
                shouldMove = true;
                $log.debug("We should move the alert to the top of the screen.");
            } else {
                $log.debug("We should not move the alerts.");
            }
        }
        return shouldMove;
    }

    <%--This function updates the order filed according to localstorage--%>
    function updateToolArrayOrder(){
        if( localStorage
            && localStorage.getItem( "home_tools_data" ) != null ) {
            $log.debug("Local storage is available in the browser.");
            if(localStorage.getItem("homeTools.customization") != null ) {
            	$scope.toggleCustomization=localStorage.getItem( "homeTools.customization")=='false'?false:true;
            }
            if($scope.toggleCustomization==true)
            {
            	for(var i=0;i< $scope.HomeTools.tools.length;i++)
            	{
            		<%--To update order of tool by retrieving order of that tool from localhost using the hometoolid of tool--%>
            		if(localStorage.getItem("homeTools.homeToolId"+ $scope.HomeTools.tools[i]["homeToolId"]) != null)
	    			{
	    			 	$scope.HomeTools.tools[i]["order"]= +localStorage.getItem("homeTools.homeToolId"+ $scope.HomeTools.tools[i]["homeToolId"]);
	    			}
	    			<%--To add hideData attribute to tool for using as ng-hide condition by retrieving status of that tool from localhost using the hometoolid of tool. here we need to update toggled value --%>
	    			if(localStorage.getItem("homeTools.displayTool"+$scope.HomeTools.tools[i]["homeToolId"]) != null)
		    		{
		    			$scope.HomeTools.tools[i]["hideData"]=localStorage.getItem("homeTools.displayTool"+$scope.HomeTools.tools[i]["homeToolId"])=='false'?true:false;
		    		}
	    		}
	    	}
            var shouldMoveAlerts = $scope.shouldMoveAlerts();
            if( shouldMoveAlerts ) {
                $log.debug("Filtering alerts to move them to the top of the screen.");
                for(var i=0;i< $scope.HomeTools.tools.length;i++)
                {
                    if($scope.HomeTools.tools[i].tool.alias == "alerts") {
                        $log.debug("Found the alerts tool and moving it to the top of the screen.");
                        $scope.HomeTools.tools[i]["order"] = -1;
                    }
                }
            }
        }
    }

    function getToolByOrder(order){
        for(var i=0;i< $scope.HomeTools.tools.length;i++)
            if($scope.HomeTools.tools[i].order==order)
            {
                return $scope.HomeTools.tools[i];
            }
    }
});