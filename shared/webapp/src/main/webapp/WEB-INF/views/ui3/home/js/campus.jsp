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

<spring:message code="campus.title" var="msgCat_ToolTitle"/>

	var campus = angular.module("campusApp",  ['ngRoute','ngSanitize','ui.bootstrap'],
	function($routeProvider,$locationProvider) {
	          $locationProvider.html5Mode(false);
	          $routeProvider.
	          when('/',                   {  templateUrl: '${pageContext.request.contextPath}/campus/templates/campusList',     controller: 'CampusController' })
	                
	});

	campus.controller("CampusController", function($scope,$http,$templateCache,$location,$sce,$log) {
	                
	    $scope.init = function() {            
	        $scope.homeToolTitle = "<c:out value="${msgCat_homeTitle}"/>";
	        $scope.pageTitle = $scope.homeToolTitle;
	        $scope.toolName= varToolName;
	        $scope.loadCampuses();
	        var menuItems = "{\"menus\": ["+
	                <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
	                "]}";
	        $scope.menuItems = eval ("(" + menuItems + ")");
	    }

	    $scope.kmeNavLeft = function() {
	        window.history.back();
	    }
	    
	     $scope.loadCampuses = function() {
	              $http({
	                  method: 'GET',
	                  url: '<c:out value="${pageContext.request.contextPath}"/>/services/campus/campuses?_type=json'
	              }).success(function(data, status, headers, config) {
	                           if( status != 200 ) {
	                $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
	            }
	                          
	                           $scope.CampusData = data;
	              }).error(function(data, status, headers, config) {
	            $scope.errors = '{"error":[{"id":'+status+',"name":"NoData"}]}';
	        });
	          }
	          
	      $scope.campusClick = function(obj) {
	              if( 'undefined' === typeof obj ) {
	                  $scope.errors = '{"error":[{"id":5000,"name":"Failed to find campus source."}]}';
	                  $log.error("No campus object passed to campusClick.");
	              } else {
	              try {
					localStorage.setItem("myCampus", obj.code);
				} catch (e) {
					if (e == QUOTA_EXCEEDED_ERR) {
						$log.error("Error: Local Storage limit exceeds.");
					} else {
						$log.error("Error: Saving to local storage.");
					}
				}
	              }
	          }
	});