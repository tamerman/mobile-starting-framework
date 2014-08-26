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
<spring:message code="academics.title" var="msgAcademicsTitle"/>
<spring:message code="academics.scheduleOfClasses" var="msgAcademicsScheduleOfClasses"/>
<spring:message code="academics.classSchedule" var="msgAcademicsClassSchedule"/>
<spring:message code="academics.classSearch" var="msgAcademicsClassSearch"/>
<spring:message code="academics.gradeAlerts" var="msgAcademicsGradeAlerts"/>
<spring:message code="academics.terms" var="msgAcademicsTerms"/>
<spring:message code="academics.careers" var="msgAcademicsCareers"/>
<spring:message code="academics.subjects" var="msgAcademicsSubjects"/>
<spring:message code="academics.catalogNumbers" var="msgAcademicsCatalogNumbers"/>
<spring:message code="academics.noInfoError" var="msgAcademicsNoInfo"/>
<spring:message code="academics.searchResult" var="msgAcademicsSearchResult"/>

var scheduleOfClasses = angular.module("scheduleOfClassesApp", ['ngRoute','ngSanitize','ui.bootstrap', 'academicsI18nModule'],
    function($routeProvider,$locationProvider) {
        $locationProvider.html5Mode(false);
        $routeProvider.
        when('/',               {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socTermList',           controller: 'ScheduleOfClassesController' }).
        when('/term',           {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socTermList',           controller: 'ScheduleOfClassesController' }).
        when('/career',         {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socCareerList',         controller: 'SocCareerController' }).
        when('/subject',        {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socSubjectList',        controller: 'SocSubjectController' }).
        when('/catalogNumber',  {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socCatalogNumberList',  controller: 'SocCatalogNumberController' }).
        when('/section',        {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionList',        controller: 'SocSectionController' }).
        when('/sectionDetail',  {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetail',      controller: 'SocSectionDetailController' }).
        when('/searchResults',  {  templateUrl: '${pageContext.request.contextPath}/academics/templates/socKeywordSearchResults',controller: 'SocSearchResultsController' }).
        otherwise({ redirectTo: "/" });
    });

scheduleOfClasses.factory('SocData',function(){
    return {
        socToolTitle:"<c:out value="${msgAcademicsTitle}"/>",
        spcScheduleOfClasses:"<c:out value="${msgAcademicsScheduleOfClasses}"/>",
        socClassSchedule:"<c:out value="${msgAcademicsClassSchedule}"/>",
        socClassSearch:"<c:out value="${msgAcademicsClassSearch}"/>",
        socGradeAlerts:"<c:out value="${msgAcademicsGradeAlerts}"/>",
        socTermLabel:"<c:out value="${msgAcademicsTerms}"/>",
        socCareerLabel:"<c:out value="${msgAcademicsCareers}"/>",
        socSubjectLabel:"<c:out value="${msgAcademicsSubjects}"/>",
        socCatalogNumberLabel:"<c:out value="${msgAcademicsCatalogNumbers}"/>",
        noInfoError:"<c:out value="${msgAcademicsNoInfo}"/>",

        socTerms:null,
        socCatalogNumbers:null,
        socCurrentTerm:null,
        socCurrentCareer:null,
        socCurrentSubject:null,
        socCurrentCatalogNumber:null,
        socCurrentSection:null,
        errors:null,
        socPageTitle:null,
        socKeywordSearch:null
    };
});

scheduleOfClasses.directive('socKeywordSearch', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socKeywordSearch'
    }
});

scheduleOfClasses.directive('socSectionListMeeting', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionListMeeting'
    }
});

scheduleOfClasses.directive('socSectionDetailHeader', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailHeader'
    }
});

scheduleOfClasses.directive('socSectionDetailNav', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailNav'
    }
});

scheduleOfClasses.directive('socSectionDetailBody', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailBody'
    }
});

scheduleOfClasses.directive('socSectionDetailMeeting', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailMeeting'
    }
});

scheduleOfClasses.directive('socSectionDetailDescription', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailDescription'
    }
});

scheduleOfClasses.directive('socSectionDetailExtra', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailExtra'
    }
});

scheduleOfClasses.directive('socSearchResultMeeting', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSearchResultMeeting'
    }
});

scheduleOfClasses.directive('socSearchResultMessages', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSearchResultMessages'
    }
});

scheduleOfClasses.controller("ScheduleOfClassesController", function($scope,$http,$templateCache,$location,$sce,$log,SocData) {

    $scope.init = function() {
        $scope.SocData = SocData;

        SocData.socPageTitle = SocData.socTermLabel;
        $scope.loadTermData();

        var menuItems = "{\"menus\": ["+
                "{ \"url\":\"/academics\" , \"label\":\"<c:out value="${msgAcademicsTitle}"/>\" },"+
                "{\"divider\":\"true\"},"+
                "{ \"url\":\"/academics/terms\" , \"label\":\"<c:out value="${msgAcademicsScheduleOfClasses}"/>\" },"+
                "{ \"url\":\"/myAcademics\" , \"label\":\"<c:out value="${msgAcademicsClassSchedule}"/>\" },"+
                "{ \"url\":\"/academics/search\" , \"label\":\"<c:out value="${msgAcademicsClassSearch}"/>\" },"+
                "{ \"url\":\"/myAcademics/gradeAlerts\" , \"label\":\"<c:out value="${msgAcademicsGradeAlerts}"/>\" },"+
                <%--"{\"divider\":\"true\"},"+--%>
                <%--  "{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
                "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
        SocData.errors = [];
        window.history.back();
    }

    $scope.loadTermData = function () {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getTerms?_type=json'
        }).success(function (data, status, headers, config) {
            if (status != 200) {
                SocData.errors = eval('({"error":[{"id":' + status + ',"name":"' + SocData.noInfoError + '"}]})');
            }
            SocData.socTerms = data;
            $scope.socTerms = SocData.socTerms;
        }).error(function (data, status, headers, config) {
            SocData.errors = eval('({"error":[{"id":' + status + ',"name":"' + SocData.noInfoError + '"}]})');
        });
    }

    $scope.socTermClick = function (obj) {
        if ('undefined' === typeof obj) {
            SocData.errors = eval('({"error":[{"id":5000,"name":"' + SocData.noInfoError + '"}]})');
            $log.error("No term object passed to socTermClick.");
        } else {
            SocData.socCurrentTerm = obj;
        }
    }

    $scope.clickHideErrors = function() {
        SocData.errors=[];
    }

});

scheduleOfClasses.controller("SocCareerController", function($scope,$http,$templateCache,$location,$sce,$log,SocData,AcademicsI18N) {
    // Attach localisation to scope
    $scope.i18n = AcademicsI18N;

    $scope.init = function() {
        $scope.SocData = SocData;
        $scope.socCurrentTerm = SocData.socCurrentTerm;
        SocData.socPageTitle = $scope.socCurrentTerm.shortDescription;

        // Set the watermark for the search
        $scope.searchTextPlaceholder = $scope.i18n.getString('academics.searchClasses', [SocData.socCurrentTerm.description]);

        if( 'undefined'=== typeof SocData.socCurrentTerm.career ) {
            $scope.loadCareerData();
        }
    }

    $scope.loadCareerData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getCareers?termId='+SocData.socCurrentTerm.id+'&_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
            }
            SocData.socCurrentTerm.academicCareer = data.academicCareer;
        }).error(function(data, status, headers, config) {
            SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
        });
    }

    $scope.socCareerClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            SocData.errors = eval('({"error":[{"id":5000,"name":"'+SocData.noInfoError+'"}]})');
            $log.error("No career object passed to socCareerClick.");
        } else {
            SocData.socCurrentCareer = obj;
        }
    }

    $scope.clickHideErrors = function() {
        SocData.errors=[];
    }
});

scheduleOfClasses.controller("SocSubjectController", function($scope,$http,$templateCache,$location,$sce,$log,SocData,AcademicsI18N) {
    // Attach localisation to scope
    $scope.i18n = AcademicsI18N;

    $scope.init = function() {
        $scope.SocData = SocData;
        $scope.socCurrentTerm = SocData.socCurrentTerm;
        $scope.socCurrentCareer = SocData.socCurrentCareer;
        SocData.socPageTitle = $scope.socCurrentCareer.shortDescription;

        // Set the watermark for the search
        $scope.searchTextPlaceholder = $scope.i18n.getString('academics.searchClasses', [SocData.socCurrentTerm.description]);


        if( 'undefined'=== typeof SocData.socCurrentCareer.subject ) {
            $scope.loadSubjectData();
        }
    }

    $scope.loadSubjectData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getSubjects?termId='+SocData.socCurrentTerm.id+'&careerId='+SocData.socCurrentCareer.id+'&_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
            }
            SocData.socCurrentCareer.subject = data.subject;
        }).error(function(data, status, headers, config) {
            SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
        });
    }

    $scope.socSubjectClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            SocData.errors = eval('({"error":[{"id":5000,"name":"'+SocData.noInfoError+'"}]})');
            $log.error("No subject object passed to socSubjectClick.");
        } else {
            SocData.socCurrentSubject = obj;
        }
    }

    $scope.clickHideErrors = function() {
        SocData.errors=[];
    }
});

scheduleOfClasses.controller("SocCatalogNumberController", function($scope,$http,$templateCache,$location,$sce,$log,SocData,AcademicsI18N) {

    // Attach localisation to scope
    $scope.i18n = AcademicsI18N;

    $scope.init = function() {
        $scope.SocData = SocData;
        $scope.socCurrentTerm = SocData.socCurrentTerm;
        $scope.socCurrentCareer = SocData.socCurrentCareer;
        $scope.socCurrentSubject = SocData.socCurrentSubject;
        SocData.socPageTitle = $scope.socCurrentSubject.shortDescription;

        // Set the watermark for the search
        $scope.searchTextPlaceholder = $scope.i18n.getString('academics.searchClasses', [SocData.socCurrentTerm.description]);

        $scope.loadCatalogNumberData();
    }

    $scope.loadCatalogNumberData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getCatalogNumbers?termId='+SocData.socCurrentTerm.id+'&subjectId='+SocData.socCurrentSubject.id+'&_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
            }
            SocData.socCatalogNumbers = data;
            $scope.socCatalogNumbers = SocData.socCatalogNumbers;
        }).error(function(data, status, headers, config) {
            SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
        });
    }

    $scope.socCatalogNumberClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            SocData.errors = eval('({"error":[{"id":5000,"name":"'+SocData.noInfoError+'"}]})');
            $log.error("No catalogNumber object passed to socCatalogNumberClick.");
        } else {
            SocData.socCurrentCatalogNumber = obj;
        }
    }

    $scope.clickHideErrors = function() {
        SocData.errors=[];
    }
});

scheduleOfClasses.controller("SocSectionController", function($scope,$http,$templateCache,$location,$sce,$log,SocData) {
    $scope.init = function() {
        $scope.SocData = SocData;
        $scope.sectionMeetingDisplayLimit = 2;

        SocData.socPageTitle = SocData.socCurrentSubject.id + ' ' + SocData.socCurrentCatalogNumber.number;

        if( 'undefined'=== typeof SocData.socCurrentCatalogNumber.section ) {
            $scope.loadSectionData();
        }
    }

    $scope.loadSectionData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getSections?termId='+SocData.socCurrentTerm.id+'&careerId='+SocData.socCurrentCareer.id+'&subjectId='+SocData.socCurrentSubject.id+'&catalogNumber='+SocData.socCurrentCatalogNumber.number+'&_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
            }
            SocData.socCurrentCatalogNumber.section = data.section;
        }).error(function(data, status, headers, config) {
            SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
        });
    }

    $scope.socSectionClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            SocData.errors = eval('({"error":[{"id":5000,"name":"'+SocData.noInfoError+'"}]})');
            $log.error("No section object passed to socSectionClick.");
        } else {
            SocData.socCurrentSection = obj;
        }
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

    $scope.isLastMeetingPattern = function(i) {
        return i==$scope.sectionMeetingDisplayLimit;
    }

    $scope.clickHideErrors = function() {
        SocData.errors=[];
    }
});

scheduleOfClasses.controller("SocSectionDetailController", function($scope,$http,$templateCache,$location,$sce,$log,SocData) {
    $scope.init = function() {
        $scope.SocData = SocData;

        $scope.loadSectionDetailData();
    }

    $scope.loadSectionDetailData = function() {
        if( 'undefined' != typeof SocData.socCurrentSection && SocData.socCurrentSection != null ) {
            $http({
                method: 'GET',
                url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getSectionDetail.json?termId='+SocData.socCurrentSection.termId+'&subjectId='+SocData.socCurrentSection.subjectId+'&catalogNumber='+SocData.socCurrentSection.catalogNumber+'&sectionNumber='+SocData.socCurrentSection.number
            }).success(function(data, status, headers, config) {
                if( status != 200 ) {
                    SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
                }
                SocData.socCurrentSection = data.section[0];
            }).error(function(data, status, headers, config) {
                SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
            });
        } else {
            console.log("SocData.socCurrentSection undefined, use socCurrentTerm/socCurrentCareer/socCurrentSubject/socCurrentCatalogNumber");
            $http({
                method: 'GET',
                url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/getSectionDetail?termId='+SocData.socCurrentTerm.id+'&careerId='+SocData.socCurrentCareer.id+'&subjectId='+SocData.socCurrentSubject.id+'&catalogNumber='+SocData.socCurrentCatalogNumber.number+'&sectionNumber='+SocData.socCurrentSection.number+'&_type=json'
            }).success(function(data, status, headers, config) {
                if( status != 200 ) {
                    SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
                }
                SocData.socCurrentSection = data.section[0];
            }).error(function(data, status, headers, config) {
                SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
            });
        }

        SocData.socPageTitle = SocData.socCurrentSection.subjectId + ' ' + SocData.socCurrentSection.catalogNumber;

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

    $scope.clickHideErrors = function() {
        SocData.errors=[];
    }
});

scheduleOfClasses.controller("SocSearchController", function($scope,$http,$templateCache,$location,$sce,$log,SocData) {
    $scope.init = function() {
        $scope.SocData = SocData;
    }

    $scope.doKeywordSearch = function() {
        var data = new Object();
        data.term = SocData.socCurrentTerm.id;
        data.searchText = $scope.searchText;
        $http({
            method: 'POST',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/academics/keywordSearch.json',
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: data,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
            if( 'undefined' === typeof data.searchResult ) {
                SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
            } else {
                SocData.socKeywordSearch = data.searchResult;
            }
            $location.path("/searchResults");
        }).error(function(data, status, headers, config) {
            SocData.errors = eval('({"error":[{"id":'+status+',"name":"'+SocData.noInfoError+'"}]})');
        });
    }
});

scheduleOfClasses.controller("SocSearchResultsController", function($scope,$http,$templateCache,$location,$sce,$log,SocData) {
    $scope.init = function() {
        $scope.SocData = SocData;
        $scope.sectionMeetingDisplayLimit = 2;

        SocData.socPageTitle = "${msgAcademicsSearchResult}";
    }

    $scope.socSectionClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            SocData.errors = eval('({"error":[{"id":5000,"name":"'+SocData.noInfoError+'"}]})');
            $log.error("No section object passed to socSectionClick.");
        } else {
            SocData.socCurrentSection = obj;
        }
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

    $scope.isLastMeetingPattern = function(i) {
        return i==$scope.sectionMeetingDisplayLimit;
    }

    $scope.clickHideErrors = function() {
        SocData.errors=[];
    }
});
