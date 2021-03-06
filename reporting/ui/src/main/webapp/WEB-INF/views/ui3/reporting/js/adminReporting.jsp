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

<spring:message code="reporting.reportingAdmin" var="msgCat_ReportingAdmin"/>

var adminReporting = angular.module("adminReportingApp", ['ngRoute','ngSanitize','ui.bootstrap'],
	function($routeProvider, $locationProvider) {
		$locationProvider.html5Mode(false);
		$routeProvider.
		when('/', {
			templateUrl: '${pageContext.request.contextPath}/reporting/templates/submissions',
			controller: 'adminReportingController'
		}).when('/submissionDetails', {
			templateUrl: '${pageContext.request.contextPath}/reporting/templates/details',
			controller: 'submissionDetailsController'
		}).when('/submissionEdit', {
			templateUrl: '${pageContext.request.contextPath}/reporting/templates/edit',
			controller: 'submissionEditController'
		}).when('/submissionRevision', {
			templateUrl: '${pageContext.request.contextPath}/reporting/templates/revisions',
			controller: 'revisionsController'
		}).otherwise({
        	redirectTo: "/"
        });
	}
);

adminReporting.factory('Submission', function() {
	return {
		submission:null,
		revisions:null
	};
});

adminReporting.controller("adminReportingController", function($scope,$http,$location,Submission){
	$scope.init = function() {
		$scope.title = "<c:out value="${msgCat_ReportingAdmin}"/>";
		$scope.pageTitle = $scope.feedbackTitle;
		$scope.Submission = Submission;
		$scope.getAllSubmissions();
		
		var menuItems = "{\"menus\": ["+
            "{ \"url\":\"/reporting/admin/index\" , \"label\":\"Reporting Admin\" },"+
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
	}
	
	$scope.kmeNavLeft = function() {
        // send to home screen.
        window.history.back();
    }
    
    $scope.submissionClick = function(submissionId) {
    	var subId = new Object();
    	subId.id = submissionId;
    	$http({
	    	method: 'POST',
	    	url: '<c:out value="${pageContext.request.contextPath}"/>/services/cxfreporting/getSubmissionById',
	    	data: subId,
	    	headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	    }).success(function(data, status, headers, config) {
	    	if( status != 200 ) {
                console.log("failure number was " + status)
            }
            $scope.Submission.submission = data.submission;
            $location.path('/submissionDetails');
	    }).error(function(data, status, headers, config) {
	    	//TO-DO
	    });
    }
	
	$scope.getAllSubmissions = function() {
		$http({
			method: 'GET',
			url: '<c:out value="${pageContext.request.contextPath}"/>/services/cxfreporting/getAllSubmissions'
		}).success(function(data, status, headers, config) {
            if( status != 200 ) {
                console.log("failure number was " + status);
            }
            $scope.submissionsFailed = false;
            $scope.submissions = data.submission;
        }).error(function(data, status, headers, config) {
            $scope.submissionsFailed = true;
        });
	}
});

adminReporting.controller("submissionDetailsController", function($scope,$http,$location,Submission) {
	$scope.init = function() {
		$scope.Submission = Submission;
		angular.forEach(Submission.submission.attributes, function(value, key) {
			if (value.key === "SUMMARY") {
				$scope.summary = value.valueLargeText;
			} else if (value.key === "EMAIL") {
				$scope.email = value.valueText;
			} else if (value.key === "CONTACT_ME") {
				if (value.valueNumber == 0) {
					$scope.contactMe = "yes";
				} else {
					$scope.contactMe = "no";
				}
			} else if (value.key === "AFFILIATION_STUDENT") {
				$scope.student = value.valueText;
			} else if (value.key === "AFFILIATION_FACULTY") {
				$scope.faculty = value.valueText;
			} else if (value.key === "AFFILIATION_STAFF") {
				$scope.staff = value.valueText;
			} else if (value.key === "AFFILIATION_OTHER") {
				$scope.other = value.valueText;
			}
		});
	}
	
	$scope.editSubmission = function() {
		$location.path('/submissionEdit');
	}
	
	$scope.revisionSubmission = function(id) {
		var submissionId = new Object();
		submissionId.id = submissionId;
    	$http({
	    	method: 'POST',
	    	url: '<c:out value="${pageContext.request.contextPath}"/>/services/cxfreporting/getRevisionsById',
	    	data: subId,
	    	headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	    }).success(function(data, status, headers, config) {
	    	if( status != 200 ) {
                console.log("failure number was " + status)
            }
            console.log(data);
            $scope.Submission.revisions = data.submission;
           	$location.path('/submissionRevision');
	    }).error(function(data, status, headers, config) {
	    	//TO-DO
	    });
	}
});

adminReporting.controller("revisionsController", function ($scope,$http,$location,Submission) {
	$scope.init = function() {
		$scope.Submission = Submission;
		$scope.submissions = $scope.Submission.revisions;
	}
	
	$scope.submissionClick = function(id) {
		var subId = new Object();
    	subId.id = id;
    	$http({
	    	method: 'POST',
	    	url: '<c:out value="${pageContext.request.contextPath}"/>/services/cxfreporting/getSubmissionById',
	    	data: subId,
	    	headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	    }).success(function(data, status, headers, config) {
	    	if( status != 200 ) {
                console.log("failure number was " + status)
            }
            $scope.Submission.submission = data.submission;
            $location.path('/submissionDetails');
	    }).error(function(data, status, headers, config) {
	    	//TO-DO
	    });
	}
});

adminReporting.controller("submissionEditController", function($scope,$http,$location,Submission) {
	$scope.init = function() {
		$scope.Submission = Submission;
		angular.forEach(Submission.submission.attributes, function(value, key) {
			if (value.key === "SUMMARY") {
				$scope.summary = value.valueLargeText;
			} else if (value.key === "EMAIL") {
				$scope.emailAddress = value.valueText;
			} else if (value.key === "CONTACT_ME") {
				$scope.contactMe = value.valueNumber;
			} else if (value.key === "AFFILIATION_STUDENT") {
				$scope.studentAffiliation = value.valueText;
			} else if (value.key === "AFFILIATION_FACULTY") {
				$scope.facultyAffiliation = value.valueText;
			} else if (value.key === "AFFILIATION_STAFF") {
				$scope.staffAffiliation = value.valueText;
			} else if (value.key === "AFFILIATION_OTHER") {
				$scope.otherAffiliation = value.valueText;
			}
		});
	}
	
	$scope.saveEdit = function() {
		var form = new Object();
		form.id = $scope.Submission.submission.id;
		form.userId = $scope.Submission.submission.userId;
		form.postDate = $scope.Submission.submission.postDate.nanos;
		form.revisionNumber = $scope.Submission.submission.revisionNumber;
		form.summary = $scope.summary;
		form.emailAddress = $scope.emailAddress;
		form.contactMe = $scope.contactMe;
		form.studentAffiliation = $scope.studentAffiliation;
		form.facultyAffiliation = $scope.facultyAffiliation;
		form.staffAffiliation = $scope.staffAffiliation;
		form.otherAffiliation = $scope.otherAffiliation;
		$http({
			method: 'POST',
			url: '<c:out value="${pageContext.request.contextPath}"/>/services/cxfreporting/submitIncident',
			data: form,
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success(function(data, status, headers, config) {
			$location.path('/');
		}).error(function (data, status, headers, config) {
		    // TODO
			console.log("ERROR" + status);
			// $scope.showError(status);
		});
	}
});