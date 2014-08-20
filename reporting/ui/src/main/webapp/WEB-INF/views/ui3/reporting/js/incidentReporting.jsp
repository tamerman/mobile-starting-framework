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

var incidentReporting = angular.module("incidentApp", ['ngRoute','ngSanitize','ui.bootstrap'],
	function($routeProvider, $locationProvider) {
		$locationProvider.html5Mode(false);
		$routeProvider.
		when('/', {
			templateUrl: '${pageContext.request.contextPath}/reporting/templates/form',
			controller: 'incidentController'
		}).otherwise({
        	redirectTo: "/"
        });
	}
);

incidentReporting.controller("incidentController", function($scope,$http,$location) {
	$scope.init = function() {
		var menuItems = "{\"menus\": ["+
            "{ \"url\":\"/incidentForm\" , \"label\":\"Incident\" },"+
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
        $scope.incidentDiv = true;
        $scope.submitProblem = false;
        $scope.contactMe = 'false';
	}
	
	$scope.kmeNavLeft = function() {
        // send to home screen.
        window.history.back();
    }
	
	$scope.submitForm = function() {
		if($scope.summary && $scope.summary !='') {
			var form = new Object();
			form.summary = $scope.summary;
			if (!angular.isUndefined($scope.emailAddress)) {
				form.email = $scope.emailAddress;
			} else {
				form.email = "";
			}
			if (!angular.isUndefined($scope.studentAffiliation)) {
				form.studentAffiliation = $scope.studentAffiliation;
			} else {
				form.studentAffiliation = false;
			}
			if (!angular.isUndefined($scope.facultyAffiliation)) {
				form.facultyAffiliation = $scope.facultyAffiliation;
			} else {
				form.facultyAffiliation = false;
			}
			if (!angular.isUndefined($scope.staffAffiliation)) {
				form.staffAffiliation = $scope.staffAffiliation;
			} else {
				form.staffAffiliation = false;
			}
			if (!angular.isUndefined($scope.otherAffiliation)) {
				form.otherAffiliation = $scope.otherAffiliation;
			} else {
				form.otherAffiliation = false;
			}
			form.contactMe = $scope.contactMe;
			$http({
				method: 'POST',
				url: '<c:out value="${pageContext.request.contextPath}"/>/services/cxfreporting/submitIncident',
				data: form,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(data, status, headers, config) {
				if (data) {
					$scope.incidentDiv = false
					$scope.submitProblem = false;
				} else {
					$scope.submitProblem = true;
					$scope.incidentDiv = true;
				}
			}).error(function (data, status, headers, config) {
			    // TODO
				console.log("ERROR" + status);
				// $scope.showError(status);
			});
		}
	}
});