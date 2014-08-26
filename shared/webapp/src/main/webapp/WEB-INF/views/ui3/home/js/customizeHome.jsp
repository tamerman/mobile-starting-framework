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

<spring:message code="customize.home.title" var="msgCat_ToolTitle"/>

var home = angular.module("customizehomeApp", ['ngSanitize','ui.bootstrap']);

home.controller("customizeHomeController", function($scope,$http,$templateCache,$location,$sce,$log) {

    $scope.init = function() {
        $scope.homeToolTitle = "<c:out value="${msgCat_homeTitle}"/>";
	    $scope.pageTitle = $scope.homeToolTitle;
	    var hometools ;
	    $scope.showHideTools= [{}];
	    $scope.toggleCustomization=false;
	   	if( localStorage
                && localStorage.getItem( "home_tools_data" ) != null ) {
            $log.debug("Local storage is available in the browser.");
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
    var hometools ;
     $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/home/json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
            }
            $scope.HomeTools = data;
            <%--To set default on values to on off switch array--%>
			for(var i=0;i< $scope.HomeTools.tools.length+1;i++)
            {
            	$scope.showHideTools[i]=true;
            }
            if( localStorage)
            {
            	localStorage.setItem( "home_tools_data", JSON.stringify(data));
				updateToolArrayOrder();
			}
			console.log(data);
			 
        }).error(function(data, status, headers, config) {
            $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
        });
    }   
        
    $scope.homeClick = function(obj) {
		if(obj.isDining){
			window.location.href = "dining:"+ obj.link;
		}else if(obj.isMaps){
			window.location.href = "maps:"+ obj.link;
		}
    }
    
    $scope.upArrowClick = function(index) {
        if( $scope.toggleCustomization == true ) {
            if(index!=0){
                localStorage.setItem( "homeTools.homeToolId"+$scope.HomeTools.tools[index]["homeToolId"],$scope.HomeTools.tools[index]["order"]-1);
                localStorage.setItem( "homeTools.homeToolId"+getHomeToolIdByOrder($scope.HomeTools.tools[index]["order"]-1),getToolByOrder($scope.HomeTools.tools[index]["order"]-1)["order"]+1);
		    }
        }
    }
    
    $scope.downArrowClick = function(index) {
        if( $scope.toggleCustomization == true ) {
            if(index!=$scope.HomeTools.tools.length-1){
                localStorage.setItem( "homeTools.homeToolId"+$scope.HomeTools.tools[index]["homeToolId"],$scope.HomeTools.tools[index]["order"]+1);
                localStorage.setItem( "homeTools.homeToolId"+getHomeToolIdByOrder($scope.HomeTools.tools[index]["order"]+1),getToolByOrder($scope.HomeTools.tools[index]["order"]+1)["order"]-1);
            }
        }
    }

    function getHomeToolIdByOrder(order){
    	return getToolByOrder(order)["homeToolId"];
    }

    function getToolByOrder(order){
        var returnValue = 0;
        for(var i=0;i< $scope.HomeTools.tools.length;i++) {
            if($scope.HomeTools.tools[i].order==order)
            {
                returnValue = $scope.HomeTools.tools[i];
                break;
            }
        }
        return returnValue;
    }

    $scope.resetToDefault = function(){
        console.log("resetToDefault Method called");
       	 $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/home/json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
            }
            <%--To set data directly from webservice--%>
            $scope.HomeTools = data;
            if( localStorage
                && localStorage.getItem( "home_tools_data" ) != null ) {
            for(var i=0;i< $scope.HomeTools.tools.length;i++)
            {
            	<%--To reset order in localstorage--%>
            	if(localStorage.getItem("homeTools.homeToolId"+ $scope.HomeTools.tools[i]["homeToolId"]) != null)
	    		{
		    		localStorage.setItem("homeTools.homeToolId"+ $scope.HomeTools.tools[i]["homeToolId"],$scope.HomeTools.tools[i]["order"]);
	    		}
	    		<%--To reset hide show switch per tool--%>
	    		if(localStorage.getItem("homeTools.displayTool"+ $scope.HomeTools.tools[i]["homeToolId"]) != null)
	    		{
    				localStorage.setItem( "homeTools.displayTool"+$scope.HomeTools.tools[i]["homeToolId"],true);
    				$scope.showHideTools[$scope.HomeTools.tools[i]["homeToolId"]]=true;
    			}
	    	}
	    		<%--To reset hide show switch for home screen customization--%>
	    	if(localStorage.getItem("homeTools.customization") != null ) {
            	localStorage.getItem( "homeTools.customization",'false')
            	$scope.toggleCustomization=false;
            }
        }
			
        }).error(function(data, status, headers, config) {
            $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
        });
    }
    $scope.toggleCustomizationSwitch = function(){
    	if(localStorage)
    	{
    		localStorage.setItem( "homeTools.customization",!$scope.toggleCustomization)
    	}
    }
    $scope.showHideTool = function(tool){
        if( $scope.toggleCustomization == true ) {
            <%--set negated value as angular js change value to model afterwards for chackboxes.--%>
            $scope.showHideTools[tool]=!$scope.showHideTools[tool];
            for(var i=0;i<$scope.showHideTools.length;i++){
                <%-- Here i is the hometoolId--%>
                localStorage.setItem( "homeTools.displayTool"+i,$scope.showHideTools[i]);
                console.log("--showHideTool-get--"+localStorage.getItem( "homeTools.displayTool"+i));
            }
        }
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
                    <%--To update show hide status of tool by retrieving status of that tool from localhost using the hometoolid of tool--%>
                    if(localStorage.getItem("homeTools.displayTool"+$scope.HomeTools.tools[i]["homeToolId"]) != null)
                    {
                        $scope.showHideTools[$scope.HomeTools.tools[i]["homeToolId"]]=localStorage.getItem("homeTools.displayTool"+$scope.HomeTools.tools[i]["homeToolId"])=='false'?false:true;
                    }
                }
            }
        }
    }
    
});