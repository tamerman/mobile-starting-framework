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
<spring:message code="academics.title" var="msgAcademicsTitle"/>
<spring:message code="academics.scheduleOfClasses" var="msgAcademicsScheduleOfClasses"/>
<spring:message code="academics.classSchedule" var="msgAcademicsClassSchedule"/>
<spring:message code="academics.classSearch" var="msgAcademicsClassSearch"/>
<spring:message code="academics.gradeAlerts" var="msgAcademicsGradeAlerts"/>
<spring:message code="academics.searchResult" var="msgAcademicsSearchResult"/>

var search = angular.module("searchApp", ['ngRoute','ngSanitize','ui.bootstrap'],
	function($routeProvider,$locationProvider) {
		$locationProvider.html5Mode(false);
        $routeProvider.
        when('/', {
        	templateUrl: '${pageContext.request.contextPath}/academics/templates/searchDetails',           
        	controller: 'searchController'
        }).when('/searchResults', {
        	templateUrl: '${pageContext.request.contextPath}/academics/templates/searchResults', 
        	controller: 'searchResultsController'
        }).when('/searchResultDetails', {
        	templateUrl: '${pageContext.request.contextPath}/academics/templates/searchResultDetails', 
        	controller: 'searchResultDetailsController'
        }).otherwise({
        	redirectTo: "/"
        });
	}
);

search.factory('SearchData', function() {
	return {
        searchToolTitle:"<c:out value="${msgAcademicsClassSearch}"/>",
        noInfoError:"<c:out value="${msgAcademicsNoInfo}"/>",

        searchPageTitle:null,
        searchCriteria:null,
		data:null,
		class:null,
        errors:null,
		arrayCheck: function(arrayToTest) {
			if(angular.isArray(arrayToTest)) {
				return arrayToTest;
			} else {
				return [arrayToTest];
			}
		}
	};
});

search.directive('socSearchResultMeeting', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSearchResultMeeting'
    }
});

search.directive('socSectionDetailHeader', function() {
     return {
         restrict:'E',
         transclude:true,
         replace:true,
         templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailHeader'
     }
});

search.directive('socSectionDetailNav', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailNav'
    }
});

search.directive('socSectionDetailBody', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailBody'
    }
});

search.directive('socSectionDetailMeeting', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailMeeting'
    }
});

search.directive('socSectionDetailDescription', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailDescription'
    }
});

search.directive('socSectionDetailExtra', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailExtra'
    }
});

search.controller("searchResultDetailsController", function($scope,$http,$templateCache,$location,$sce,$log,SearchData) {
	$scope.init = function() {
        $scope.SearchData = SearchData;
		<%--$scope.searchResultData = $scope.SearchData.class;--%>
		<%--$scope.socShowDetailBody = true;--%>
        if ('undefined' == SearchData.class) {
            console.log("No section object passed to sectionDetail page.");
        }

        $scope.SocData = new Object();
        $scope.SocData.socCurrentSection = SearchData.class ;

        SearchData.searchPageTitle = SearchData.class.subjectId + ' ' + SearchData.class.catalogNumber;

        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getSectionDetail.json?termId='+SearchData.class.termId+'&subjectId='+SearchData.class.subjectId+'&catalogNumber='+SearchData.class.catalogNumber+'&sectionNumber='+SearchData.class.number
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                SearchData.errors = eval('({"error":[{"id":'+status+',"name":"'+ SearchData.noInfoError+'"}]})');
            }
            $scope.SocData.socCurrentSection = data.section[0];
        }).error(function(data, status, headers, config) {
            SearchData.errors = eval('({"error":[{"id":'+status+',"name":"'+ SearchData.noInfoError+'"}]})');
        });


    }

    $scope.socSectionDetailBodyClick = function() {
        $scope.socShowDetailBody = true;
        $scope.socShowDetailDescription = false;
        $scope.socShowDetailExtra = false;
    }

    $scope.socSectionDetailDescriptionClick = function() {
        $scope.socShowDetailBody = false;
        $scope.socShowDetailDescription = true;
        $scope.socShowDetailExtra = false;
    }

    $scope.socSectionDetailExtraClick = function() {
        $scope.socShowDetailBody = false;
        $scope.socShowDetailDescription = false;
        $scope.socShowDetailExtra = true;
    }
    $scope.sectionOpen = function(obj) {
        if( 'undefined' == typeof obj ) {
            return false;
        } else {
            return (obj.hasOwnProperty('enrollmentStatus')
                    && obj.enrollmentStatus.toLowerCase() == 'open' );
        }
    }

    $scope.sectionClosed = function(obj) {
        if( 'undefined' == typeof obj ) {
            return false;
        } else {
            return (obj.hasOwnProperty('enrollmentStatus')
                    && obj.enrollmentStatus.toLowerCase() == 'closed' );
        }
    }

    $scope.sectionWaitList = function(obj) {
        if( 'undefined' == typeof obj ) {
            return false;
        } else {
            return (obj.hasOwnProperty('enrollmentStatus')
                    && ( obj.enrollmentStatus.toLowerCase() == 'waitlist'
                            || obj.enrollmentStatus.toLowerCase() == 'wait list' ) );
        }
    }
	
	$scope.isArray = function(isMeetings) {
		return angular.isArray(isMeetings);
	}
	
	$scope.callArrayCheck = function(arrayToTest) {
		//console.log(arrayToTest);
		return $scope.SearchData.arrayCheck(arrayToTest);
	}

    $scope.clickHideErrors = function() {
        SearchData.errors=[];
    }
});

search.controller("searchResultsController", function($scope,$http,$templateCache,$location,$sce,$log,SearchData) {
	$scope.init = function() {
        SearchData.searchPageTitle = "${msgAcademicsSearchResult}";
		$scope.SearchData = SearchData;
		$scope.searchResult = $scope.SearchData.data;
        $scope.sectionMeetingDisplayLimit = 2;
	}
	
	$scope.callArrayCheck = function(arrayToTest) {
		return $scope.SearchData.arrayCheck(arrayToTest);
	}
	$scope.classDetails = function(selectedClass) {
		$scope.SearchData.class = selectedClass;
		$location.path('/searchResultDetails');
	}

    $scope.sectionOpen = function(obj) {
        if( 'undefined' == typeof obj ) {
            return false;
        } else {
            return (obj.hasOwnProperty('enrollmentStatus')
                    && obj.enrollmentStatus.toLowerCase() == 'open' );
        }
    }

    $scope.sectionClosed = function(obj) {
        if( 'undefined' == typeof obj ) {
            return false;
        } else {
            return (obj.hasOwnProperty('enrollmentStatus')
                    && obj.enrollmentStatus.toLowerCase() == 'closed' );
        }
    }

    $scope.sectionWaitList = function(obj) {
        if( 'undefined' == typeof obj ) {
            return false;
        } else {
            return (obj.hasOwnProperty('enrollmentStatus')
                    && ( obj.enrollmentStatus.toLowerCase() == 'waitlist'
                            || obj.enrollmentStatus.toLowerCase() == 'wait list' ) );
        }
    }

    $scope.clickHideErrors = function() {
        SearchData.errors=[];
    }

});

search.controller("searchController", function($scope,$http,$templateCache,$location,$sce,$log,SearchData) {
	$scope.init = function() {
        SearchData.searchPageTitle = SearchData.searchToolTitle;
		$scope.SearchData = SearchData;
        $scope.searchCriteria ={};

        if (angular.isUndefined(SearchData.searchCriteria) || SearchData.searchCriteria==null) {
            $scope.searchCriteria.showOpen="on";
         }
        else {
            $scope.searchCriteria = SearchData.searchCriteria;

            if (angular.isUndefined($scope.searchCriteria.catalogNumber) || $scope.searchCriteria.catalogNumber=="") {
                if (SearchData.searchCriteria.filterCriteria.indexOf("100") != -1) {
                    $scope.oneLevel = true;
                }
                if (SearchData.searchCriteria.filterCriteria.indexOf("200") != -1) {
                    $scope.twoLevel = true;
                }
                if (SearchData.searchCriteria.filterCriteria.indexOf("300") != -1) {
                    $scope.threeLevel = true;
                }
                if (SearchData.searchCriteria.filterCriteria.indexOf("400") != -1) {
                    $scope.fourLevel = true;
                }
                if (SearchData.searchCriteria.filterCriteria.indexOf("500") != -1) {
                    $scope.fiveLevelPlus = true;
                }
            }
        }

        $scope.buildTerm();

        var menuItems = "{\"menus\": ["+
                "{ \"url\":\"/academics\" , \"label\":\"<c:out value="${msgAcademicsTitle}"/>\" },"+
                "{ \"divider\":\"true\"},"+
                "{ \"url\":\"/academics/terms\" , \"label\":\"<c:out value="${msgAcademicsScheduleOfClasses}"/>\" },"+
                "{ \"url\":\"/myAcademics\" , \"label\":\"<c:out value="${msgAcademicsClassSchedule}"/>\" },"+
                "{ \"url\":\"/academics/search\" , \"label\":\"<c:out value="${msgAcademicsClassSearch}"/>\" },"+
                "{ \"url\":\"/myAcademics/gradeAlerts\" , \"label\":\"<c:out value="${msgAcademicsGradeAlerts}"/>\" },"+
                <%--"{ \"divider\":\"true\"},"+--%>
              <%--  "{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
                "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
	}
	
	$scope.kmeNavLeft = function() {
        // send to home screen.
        SearchData.errors=[];
        window.history.back();
    }
	
	$scope.courseSearch = function() {
		var dataEntered = new Object();

        dataEntered = $scope.searchCriteria;

        dataEntered.filterCriteria = [];
        if (angular.isUndefined($scope.searchCriteria.catalogNumber)
                || $scope.searchCriteria.catalogNumber == "") {
            if ($scope.oneLevel) {
                dataEntered.filterCriteria.push("100");
            }

            if ($scope.twoLevel) {
                dataEntered.filterCriteria.push("200");
            }

            if ($scope.threeLevel) {
                dataEntered.filterCriteria.push("300");
            }

            if ($scope.fourLevel) {
                dataEntered.filterCriteria.push("400");
            }

            if ($scope.fiveLevelPlus) {
                dataEntered.filterCriteria.push("500");
            }
        }

        $scope.SearchData.searchCriteria = dataEntered;
		$http({
			method: 'POST',
			url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/classSearch',
			data: dataEntered,
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success(function(data, status, headers, config) {
			if( status != 200 ) {
                console.log("failure number was " + status)
            }
            $scope.SearchData.data = data.searchResult;
            $location.path('/searchResults');
            
		}).error(function(data, status, headers, config) {
            SearchData.errors = eval('({"error":[{"id":'+status+',"name":"'+SearchData.noInfoError+'"}]})');
			console.log("we got problems");
		});
	}

    $scope.buildTerm = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getTerms?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                console.log("failure number was " + status)
            }
            $scope.terms = data.term;

            if (angular.isUndefined($scope.searchCriteria.termId) ) {
                $scope.searchCriteria.termId = $scope.terms[0].id;
            }

            $scope.buildSchool($scope.searchCriteria.termId);

        }).error(function(data, status, headers, config) {
            SearchData.errors = eval('({"error":[{"id":'+status+',"name":"'+SearchData.noInfoError+ ' - terms' + '"}]})');
            console.log("we got problems buildTerm");
        });
    }

    $scope.termChange = function() {
        $scope.searchCriteria.careerId = "ALL";
        $scope.searchCriteria.subjectId = "ALL";
        $scope.buildSchool($scope.searchCriteria.termId);
    }

    $scope.buildSchool = function(term) {
        var anySubjects = new Object();
        anySubjects.id = "ALL";
        anySubjects.description = "Any";
        var data = new Object();
        data.termId = term;
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getCareers?termId='+term+'&_type=json'
        }).success(function(data, status, headers, config) {
            var allCareers = data.academicCareer;
            allCareers.unshift(anySubjects);
            $scope.schools = allCareers;

            if (angular.isUndefined($scope.searchCriteria.careerId)) {
                $scope.searchCriteria.careerId = $scope.schools[0].id;
            }

            $scope.buildSubject($scope.searchCriteria.termId, $scope.searchCriteria.careerId);

        }).error(function(data, status, headers, config) {
            SearchData.errors = eval('({"error":[{"id":'+status+',"name":"'+SearchData.noInfoError+ '- schools' + '"}]})');
            console.log("we got problems to buildSchool");
        });
    }


    $scope.schoolChange = function() {
        $scope.searchCriteria.subjectId = "ALL";
        $scope.buildSubject($scope.searchCriteria.termId, $scope.searchCriteria.careerId);
    }

    $scope.buildSubject = function(term, school) {
        var anySubjects = new Object();
        anySubjects.id = "ALL";
        anySubjects.description = "Any";
        var data = new Object();
        data.termId = term;
        data.careerId = school;
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getSubjects?termId='+term+'&careerId='+school+'&_type=json'
        }).success(function(data, status, headers, config) {
            var allSubjects = data.subject;
            allSubjects.unshift(anySubjects);
            $scope.subjects = allSubjects;
            if (angular.isUndefined($scope.searchCriteria.subjectId)) {
                $scope.searchCriteria.subjectId = $scope.subjects[0].id;;
            }

        }).error(function(data, status, headers, config) {
            SearchData.errors = eval('({"error":[{"id":'+status+',"name":"'+SearchData.noInfoError+ '- subjects' + '"}]})');
            console.log("we got problems to buildSubject");
        });
    }
	
	$scope.subjectChange = function() {
		<%-- place holder for if something is added for changing the subject drop down --%>
	}

    $scope.clickHideErrors = function() {
        SearchData.errors=[];
    }
});