<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
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