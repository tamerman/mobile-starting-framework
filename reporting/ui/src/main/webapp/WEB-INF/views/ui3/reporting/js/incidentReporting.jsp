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