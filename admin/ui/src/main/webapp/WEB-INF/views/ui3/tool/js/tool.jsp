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
        <spring:message code="tools.title" var="msgCat_ToolTitle"/>

        var tool = angular.module("toolApp", ['ngRoute', 'ngSanitize', 'ui.bootstrap'],
                function ($routeProvider, $locationProvider) {
                    $locationProvider.html5Mode(false);
                    $routeProvider.
                            when('/', 				{  templateUrl: '${pageContext.request.contextPath}/tool/templates/listTools', controller: 'ToolController' }).
                            otherwise({ redirectTo: "/" });
                });

        tool.factory('ToolData', function () {
            return {
                tools: null,
                modifiedTools: null,
                currentTool: null,
                errors:null,
        		infos:null,
        		alerts:null,
        		successes:null,
                toolPageTitle: null
            };
        });
        
        tool.directive('notificationList', function() {
		    return {
		        restrict:'E',
		        transclude:true,
		        replace:true,
		        templateUrl: '${pageContext.request.contextPath}/tool/templates/notificationList'
		    }
		});
        

        tool.controller("ToolController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, ToolData) {

            $scope.init = function () {
                $scope.ToolData = ToolData;
                ToolData.errors = [];
		      	ToolData.alerts = [];
		      	ToolData.infos = [];
		      	ToolData.successes = [];              
		      	  
                $scope.msgCat_ToolTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                $scope.toolPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                ToolData.toolPageTitle = $scope.toolPageTitle;

                $scope.loadTools();

                var menuItems = "{\"menus\": [" +
                        "{ \"url\":\"/tool\" , \"label\":\"Tool Home\" }," +
                        <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }" +--%>
                        "]}";

                $scope.menuItems = eval("(" + menuItems + ")");
            }


            $scope.kmeNavLeft = function () {
                // send to home screen.
                ToolData.errors = [];
                ToolData.alerts = [];
                ToolData.infos = [];
                ToolData.successes = [];
                window.history.back();
            }
			
			$scope.manageTools = function(tools, tool, remove) {
            	var results = ToolData.modifiedTools.tool; 
				for (index = 0; index < tools.length; ++index) {
			        entry = tools[index];
			        if (entry && entry.name && entry.name.indexOf(tool.name) !== -1) {
			        	if(remove == true){			        		
			            	results.push(entry);
			            } else {
			            	results.splice(results.indexOf(entry), 1);
			            }
			        }
			    }
			    ToolData.modifiedTools.tool = results;
			}
            
            $scope.isActive = function (viewLocation) {
                var active = (viewLocation === $location.path());
                return active;
            };

            $scope.loadTools = function () {                
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/tool/tools?_type=json'
                }).success(function (data, status, headers, config) {
                    if (status != 200) {
                        ToolData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Tools"}]})');
                    }
                    if (data.tool.length == 0){
                        ToolData.infos = eval('({"info":[{"id":' + status + ',"name":"Tool is empty"}]})');
                    }
                    ToolData.tools = data;
                    ToolData.modifiedTools = angular.copy(data);
                    ToolData.modifiedTools.tool = [];
                    $scope.toolPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                    ToolData.toolPageTitle = $scope.toolPageTitle;

                }).error(function (data, status, headers, config) {
                    ToolData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Groups"}]})');
                });
            }

            $scope.toolClick = function (obj) {
                if ('undefined' === typeof obj) {
                    ToolData.errors = eval('({"error":[{"id":5000,"name":"Failed to find tool data."}]})');
                } else {
                    ToolData.currentTool = obj;
                }
            }
            
            $scope.clickHideErrors = function() {     
		   		ToolData.errors = [];
			}
			$scope.clickHideAlerts = function() {     
		       	ToolData.alerts = [];
			}
			$scope.clickHideInfos = function() {     
		       	ToolData.infos = [];
			}
			$scope.clickHideSuccesses = function() {     
		       	ToolData.successes = [];
			}
        });


        