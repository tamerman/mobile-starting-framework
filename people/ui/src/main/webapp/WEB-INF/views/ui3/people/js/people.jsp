<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
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

<spring:message code="people.personNotFound" var="personNotFound"/>
<spring:message code="people.groupNotFound" var="groupNotFound"/>
<spring:message code="people.noMembersFound" var="noMembersFound"/>
<spring:message code="people.noPeopleFound" var="noPeopleFound"/>
<spring:message code="people.noResultsFoundMessage" var="noResultsFoundMessage"/>
<spring:message code="people.errorFindingContact" var="errorFindingContact"/>
<spring:message code="people.noMembersFound" var="noMembersFound"/>
<spring:message code="people.searchTypeSimple" var="searchTypeSimple"/>
<spring:message code="people.searchTypeAdvanced" var="searchTypeAdvanced"/>
<spring:message code="people.resultsShow" var="resultsShow"/>
<spring:message code="people.resultsHide" var="resultsHide"/>

var people = angular.module("peopleApp", ['ngRoute', 'ngSanitize','ui.bootstrap']);

people.config(['$routeProvider','$logProvider',
	function($routeProvider,$logProvider) {
		$routeProvider.
			when('/', {
				templateUrl: '${pageContext.request.contextPath}/people/templates/searching',
				controller: 'searchController'
			}).when('/person', {
				templateUrl: '${pageContext.request.contextPath}/people/templates/personInfo',
				controller: 'personController'
			}).when('/group', {
				templateUrl: '${pageContext.request.contextPath}/people/templates/groupInfo',
				controller: 'groupController'
			}).when('/group/:sourceId', {
				templateUrl: '${pageContext.request.contextPath}/people/templates/groupInfo',
				controller: 'groupController'
			}).otherwise({
				redirectTo: '/'
			});
        $logProvider.debugEnabled(true);
	}
]);

people.factory('PeopleData',function() {
    return {
        searchUsed:0, // track last search used
        searchedFor:null,
        subGroupDNs:[],
        isExact:false,
        currentGroup:null,
        currentPerson:null,

        searchResults:null,
        cachedSearch:null,
        errors:[],
        infos:[],
        alerts:[],
        successes:[],

        personNotFound:"<c:out value="${personNotFound}"/>",
        groupNotFound:"<c:out value="${groupNotFound}"/>",
        peopleToolTitle:"<c:out value="Directory"/>",

        advancedToggle:true,
        showSimpleSearch:true,
        showAdvancedSearch:false,
        showAdvancedSearchForm:true,
        simpleResultsPage:false,
        locations:[],
        enableAdvancedSearchToggle:${enableAdvancedSearchToggle},

        error:null
    };
});

people.directive('handlePhoneSubmit', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
                    var textField = $(element).find('input[type=text]');

                    $(element).submit( function() {
                        //console.log("form was submitted");
                        //console.log("textField: %o" , textField);
                        textField.blur();
                    });
                }
    }
});

people.directive('peopleSearchResults', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/people/templates/peopleSearchResults'
    }
});

people.directive('groupSearchResults', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/people/templates/groupSearchResults'
    }
});

people.controller("membersController", function($scope,$http,$templateCache,$location,$sce,$log,PeopleData) {
    $scope.init = function() {
        $scope.PeopleData = PeopleData;
    }
});

people.controller("personController", function($scope,$http,$templateCache,$location,$sce,$log,PeopleData) {
    $scope.init = function() {
        $scope.PeopleData = PeopleData;
        PeopleData.errors = [];
        PeopleData.alerts = [];
        PeopleData.infos = [];
        PeopleData.successes = [];

        if( 'undefined' === typeof PeopleData.currentPerson ) {

            PeopleData.errors = eval('({"error":[{"id":5000,"name":"'+PeopleData.personNotFound+'"}]})');
            $log.error("No person object passed to peopleClick.");
        }
    }
});

people.controller("groupController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,PeopleData) {
    $scope.init = function() {
        $scope.PeopleData = PeopleData;
        PeopleData.errors = [];
        PeopleData.alerts = [];
        PeopleData.infos = [];
        PeopleData.successes = [];
        
        $scope.sourceId = $routeParams.sourceId && $routeParams.sourceId || 0;
        $scope.loadSource($scope.sourceId);
    }

    $scope.clickPerson = function(obj) {
        PeopleData.currentPerson = obj;
    }
    
    $scope.loadSource = function(sourceId) {
    	$scope.subGroupDNFound;
    	for (var index = 0; index < PeopleData.subGroupDNs.length; index++) {
    		if (PeopleData.subGroupDNs[index].key == sourceId) {
    			$scope.subGroupDNFound = PeopleData.subGroupDNs[index].value;
    		}
    	}
    	if ($scope.subGroupDNFound) {
	    	$http({
		    	method: 'GET',
		    	url: '<c:out value="${pageContext.request.contextPath}"/>/services/directory/group/lookup.json?dn='+$scope.subGroupDNFound,
		    	<%--data: PeopleData.currentGroup.DN,--%>
		    	headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(data, status, headers, config) {
		        PeopleData.currentGroup = data.group;
			}).error(function(data, status, headers, config) {
				PeopleData.errors = eval('({"error":[{"id": " + status + ","name": $scope.groupNotFound}]})');
			});
		}
    }
    
    $scope.clickGroup = function(obj) {
    	PeopleData.currentGroup = obj;
    	var foundGroupDN = false;
    	for (var index = 0; index < PeopleData.subGroupDNs.length; index++) {
    		if (PeopleData.subGroupDNs[index].key == PeopleData.currentGroup.displayName) {
    			foundGroupDN = true;
    		}
    	}
    	if (!foundGroupDN) {
	    	PeopleData.subGroupDNs.push({key : PeopleData.currentGroup.displayName, value : PeopleData.currentGroup.DN});
	    	sessionStorage.setItem("list_group_DNs", JSON.stringify(PeopleData.subGroupDNs));
    	}
    	//$scope.loadSource(obj.currentGroup.displayName);
    }

	
});

people.controller("searchController", function($scope,$http,$templateCache,$location,$sce,$log,PeopleData) {
    $scope.init = function() {
        PeopleData.advancedToggle = PeopleData.enableAdvancedSearchToggle;
        $scope.PeopleData = PeopleData;
        PeopleData.errors = [];
        PeopleData.alerts = [];
        PeopleData.infos = [];
        PeopleData.successes = [];
        $scope.loadCachedResults();
        if( 'undefined' === PeopleData.searchResults ) {
            PeopleData.simpleResultsPage = false;
            PeopleData.advancedResultsPage = false;
            PeopleData.searchUsed = 0;
        }
        if( PeopleData.searchUsed == 2 && PeopleData.enableAdvancedSearchToggle ) {
            PeopleData.showAdvancedSearch == true;
            PeopleData.showSimpleSearch == false;
        }
        if( PeopleData.enableAdvancedSearchToggle == false ) {
            PeopleData.showAdvancedSearchForm = false
            PeopleData.showAdvancedSearch = false;
            PeopleData.showSimpleSearch = true;
            PeopleData.advancedResultsPage = false;
            PeopleData.searchUsed = 1;
        }
    }

    $scope.checkExact = function() {
    	if ($scope.matching == "exactly") {
    		PeopleData.isExact = true;
    	} else {
    		PeopleData.isExact = false;
    	}
    }

	$scope.nameFilter = function(simplePeople) {
    	console.log(simplePeople.userName + " " + $PeopleData.searchResults.searchCriteria);
    	return simplePeople.userName == $PeopleData.searchResults.searchCriteria;
    }

    $scope.loadCachedResults = function() {
        $log.debug("Entered loadCachedResults.");
        if(sessionStorage && sessionStorage.getItem("people_search_results") != null ) {
            $log.debug("Session storage is available in the browser.");
            var cachedData = JSON.parse( sessionStorage.getItem( "people_search_results" ) );
            PeopleData.searchUsed = sessionStorage.getItem("people_search_type");
            PeopleData.cachedSearch = cachedData;
            PeopleData.searchResults = cachedData.searchResults;
            PeopleData.subGroupDNs = JSON.parse(sessionStorage.getItem("list_group_DNs"));
            if( PeopleData.searchResults.people[0].displayName == "Error" ) {
                PeopleData.simpleResultsPage = false;
                PeopleData.advancedResultsPage = false;
            } else {
                PeopleData.simpleResultsPage = true;
                PeopleData.advancedResultsPage = false;
            }
            if( PeopleData.searchUsed == 2 ) {
                PeopleData.showSimpleSearch = false;
                PeopleData.showAdvancedSearch = true;
                PeopleData.advancedResultsPage = true;
                PeopleData.showAdvancedSearchForm = false;
                $("#searchHide").html("${resultsHide}");
                $("#searchType").html("${searchTypeSimple}");
            } else {
                PeopleData.showSimpleSearch = true;
                PeopleData.showAdvancedSearch = false;
            }
            $scope.simpleText = PeopleData.searchResults.searchCriteria.searchText;
            $scope.firstNameText = PeopleData.searchResults.searchCriteria.firstName;
            $scope.lastNameText = PeopleData.searchResults.searchCriteria.lastName;
            $scope.matching = PeopleData.searchResults.searchCriteria.exactness;
            $scope.checkExact();
            $scope.status = PeopleData.searchResults.searchCriteria.status;
            $scope.locationSelect = PeopleData.searchResults.searchCriteria.location;
            $scope.usernameText = PeopleData.searchResults.searchCriteria.username;
        } else {
            $log.debug("Session storage is unavailable in the browser or no data found.");
            PeopleData.simpleResultsPage = false;
            PeopleData.advancedResultsPage = false;
            PeopleData.searchUsed = 0;
        }
        $log.debug("Leaving loadCachedResults");
    }

	$scope.simpleSubmit = function() {
        PeopleData.searchUsed = 1;
        sessionStorage.setItem("people_search_type",PeopleData.searchUsed);
		PeopleData.searchedFor = $scope.simpleText;
    	var data = new Object();
    	data.exactness = '';
    	data.firstName = '';
    	data.lastName = '';
    	data.location = '';
    	if (!angular.isUndefined($scope.simpleText)) {
    		data.searchText = $scope.simpleText;
    	} else {
    		data.searchText = "";
    	}
    	data.status = '';
    	data.username = '';
    	$http({
    		method: 'POST',
    		url: '<c:out value="${pageContext.request.contextPath}"/>/services/directory/search.json',
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: data,
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    	}).success(function(data, status, headers, config) {
    		<%--console.log(data);--%>
            if( sessionStorage ) {
                sessionStorage.setItem( "people_search_results", JSON.stringify(data) );
                PeopleData.cachedSearch = data;
                PeopleData.searchResults = data.searchResults;
            }
            PeopleData.simpleResultsPage = true;
    	}).error(function(data, status, headers, config) {
            PeopleData.advPeopleResults = null;
    		var errorResults = new Object();
    		errorResults.affiliations = "<c:out value="${errorFindingContact}"/>"
    		errorResults.displayName = "Error";
            PeopleData.error = errorResults;
    	});
    }
    
    $scope.advancedSubmit = function() {
        PeopleData.searchUsed = 2;
        sessionStorage.setItem("people_search_type",PeopleData.searchUsed);
    	var userData = new Object();
    	userData.exactness = $scope.matching;
    	$scope.checkExact();
    	if (!angular.isUndefined($scope.firstNameText)) {
	    	userData.firstName = $scope.firstNameText;
	    } else {
	    	userData.firstName = '';
	    }
	    if (!angular.isUndefined($scope.lastNameText)) {
	    	userData.lastName = $scope.lastNameText;
	    } else {
	    	userData.lastName = '';
	    }
	    userData.location = $scope.locationSelect;
    	userData.searchText = '';
	    userData.status = $scope.status;
	    if (!angular.isUndefined($scope.usernameText)) {
	    	userData.username = $scope.usernameText;
	    } else {
	    	userData.username = '';
	    }
    	$http({
    		method: 'POST',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/directory/search.json',
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
    		data: userData,
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    	}).success(function(data, status, headers, config) {
            if( sessionStorage ) {
                sessionStorage.setItem( "people_search_results", JSON.stringify(data) );
                PeopleData.cachedSearch = data;
                PeopleData.searchResults = data.searchResults;
            }
            PeopleData.advancedResultsPage = true;
            PeopleData.showAdvancedSearchForm = false;
    	}).error(function(data, status, headers, config) {
            PeopleData.advPeopleResults = null;
    		var errorResults = new Object();
    		errorResults.affiliations = "<c:out value="${errorFindingContact}"/>"
    		errorResults.displayName = "Error";
            PeopleData.error = errorResults;
            PeopleData.advancedResultsPage = true;
    	});
    }

    $scope.clickSearchType = function() {
        PeopleData.showSimpleSearch=!PeopleData.showSimpleSearch;
        PeopleData.showAdvancedSearch=!PeopleData.showAdvancedSearch;
        if( PeopleData.showAdvancedSearch ) {
            $("#searchType").html("${searchTypeSimple}");
            if( PeopleData.searchUsed != 2 ) {
                PeopleData.advancedResultsPage == false;
                PeopleData.showAdvancedSearchForm = true;
            }
        } else {
            $("#searchType").html("${searchTypeAdvanced}");
            if( PeopleData.searchUsed == 1 ) {
                PeopleData.simpleResultsPage == false;
            }
        }
    }

    $scope.clickHideResults = function() {
        PeopleData.showAdvancedSearchForm=!PeopleData.showAdvancedSearchForm;
        PeopleData.advancedResultsPage=!PeopleData.advancedResultsPage;
        if( PeopleData.showAdvancedSearchForm ) {
            $("#searchHide").html("${resultsShow}");
        } else {
            $("#searchHide").html("${resultsHide}");
        }
    }

    $scope.shouldHideResultButton = function() {
        return PeopleData.searchUsed == 2
            && PeopleData.showAdvancedSearch
            && PeopleData.searchResults;
    }

    $scope.peopleClick = function(obj) {
        PeopleData.currentPerson = obj;
    }
    
    $scope.groupClick = function(obj) {
        PeopleData.currentGroup = obj;
        var foundGroupDN = false;
    	for (var index = 0; index < PeopleData.subGroupDNs.length; index++) {
    		if (PeopleData.subGroupDNs[index].key == PeopleData.currentGroup.displayName) {
    			foundGroupDN = true;
    		}
    	}
    	if (!foundGroupDN) {
	        PeopleData.subGroupDNs.push({key : PeopleData.currentGroup.displayName, value : PeopleData.currentGroup.DN});
	        sessionStorage.setItem("list_group_DNs", JSON.stringify(PeopleData.subGroupDNs));
	    }
    }

});

people.controller("peopleController", function($scope,$http,$templateCache,$location,$sce,$log,PeopleData) {
	$scope.init = function() {
        $scope.PeopleData=PeopleData;
        <c:forEach var="location" items="${locations}" varStatus="loop">
        PeopleData.locations.push('${location}');
        </c:forEach>

        $scope.pageTitle = $scope.peopleToolTitle;

        var menuItems = "{\"menus\": ["+
                <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
                "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
	}
	
	$scope.kmeNavLeft = function() {
		PeopleData.errors = [];
        PeopleData.alerts = [];
        PeopleData.infos = [];
        PeopleData.successes = [];
	    window.history.back();
    }
});