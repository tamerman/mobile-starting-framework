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
<spring:message code="academics.terms" var="msgAcademicsTerms"/>
<spring:message code="academics.careers" var="msgAcademicsCareers"/>
<spring:message code="academics.subjects" var="msgAcademicsSubjects"/>
<spring:message code="academics.catalogNumbers" var="msgAcademicsCatalogNumbers"/>
<spring:message code="academics.noInfoError" var="msgAcademicsNoInfo"/>
<spring:message code="academics.myScheduleNotEnrolled" var="msgNotEnrolled"/>
<spring:message code="academics.myScheduleAnyTermLabel" var="msgAnyTermLabel"/>

var mySchedule = angular.module("myScheduleApp", ['ngRoute','ngSanitize','ui.bootstrap','xx-http-error-handling'],
    function($routeProvider,$locationProvider) {
        $locationProvider.html5Mode(false);
        $routeProvider.
                when('/',               {  templateUrl: '${pageContext.request.contextPath}/academics/templates/myTermList',        controller: 'MyScheduleController' }).
                when('/schedule',       {  templateUrl: '${pageContext.request.contextPath}/academics/templates/mySectionList',     controller: 'MyScheduleListController' }).
                when('/sectionDetail',  {  templateUrl: '${pageContext.request.contextPath}/academics/templates/mySectionDetail',   controller: 'MyScheduleSectionDetailController' }).
                otherwise({ redirectTo: "/" });
    });

mySchedule.factory('myScheduleData',function(){
    return {
        socToolTitle:"<c:out value="${msgAcademicsTitle}"/>",
        spcScheduleOfClasses:"<c:out value="${msgAcademicsScheduleOfClasses}"/>",
        socClassSchedule:"<c:out value="${msgAcademicsClassSchedule}"/>",
        socClassSearch:"<c:out value="${msgAcademicsClassSearch}"/>",
        socGradeAlerts:"<c:out value="${msgAcademicsGradeAlerts}"/>",
        socTermLabel:"<c:out value="${msgAcademicsTerms}"/>",

        noInfoError:"<c:out value="${msgAcademicsNoInfo}"/>",
        notEnrolled:"<c:out value="${msgNotEnrolled}"/>",
        anyTermLabel:"<c:out value="${msgAnyTermLabel}"/>",

        sectionMeetingDisplayLimit:<c:out value="${meetingDisplayLimit}"/>,

        myTerms:null,
        mySchedule:null,
        myCurrentTerm:null,
        myCurrentCareer:null,
        myCurrentSection:null,
        socCurrentSection:null,  // Added so we can reuse templates from soc.

        didChangeTermCareer:false,

        errors:null,
        pageTitle:"<c:out value="${msgAcademicsTitle}"/>"
    };
});

mySchedule.directive('myCareerList', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/myCareerList'
    }
});

mySchedule.directive('mySectionListMeeting', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/mySectionListMeeting'
    }
});

mySchedule.directive('mySectionDetailHeader', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/mySectionDetailHeader'
    }
});

mySchedule.directive('socSectionDetailNav', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailNav'
    }
});

mySchedule.directive('mySectionDetailBody', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/mySectionDetailBody'
    }
});

mySchedule.directive('socSectionDetailMeeting', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailMeeting'
    }
});

mySchedule.directive('socSectionDetailDescription', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailDescription'
    }
});

mySchedule.directive('socSectionDetailExtra', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/academics/templates/socSectionDetailExtra'
    }
});

mySchedule.controller("MyScheduleController", function($scope,$http,$templateCache,$location,$sce,$log,myScheduleData) {

    $scope.init = function() {
        $scope.myScheduleData = myScheduleData;
        myScheduleData.pageTitle = myScheduleData.socTermLabel;
        //console.log("myScheduleData.myTerms=" + myScheduleData.myTerms);
        if ('undefined'== myScheduleData.myTerms || myScheduleData.myTerms == null) {
            $scope.loadTermData();
        }
        else {
            $scope.myTerms = myScheduleData.myTerms;
        }

        var menuItems = "{\"menus\": ["+
                "{ \"url\":\"/academics\" , \"label\":\"<c:out value="${msgAcademicsTitle}"/>\" },"+
                "{\"divider\":\"true\"},"+
                "{ \"url\":\"/academics/terms\" , \"label\":\"<c:out value="${msgAcademicsScheduleOfClasses}"/>\" },"+
                "{ \"url\":\"/myAcademics\" , \"label\":\"<c:out value="${msgAcademicsClassSchedule}"/>\" },"+
                "{ \"url\":\"/academics/search\" , \"label\":\"<c:out value="${msgAcademicsClassSearch}"/>\" },"+
                "{ \"url\":\"/myAcademics/gradeAlerts\" , \"label\":\"<c:out value="${msgAcademicsGradeAlerts}"/>\" },"+
                <%--"{\"divider\":\"true\"},"+--%>
            <%--    "{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
                "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
        myScheduleData.errors=[];
        window.history.back();
    }

    $scope.loadTermData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/myAcademics/getTerms?_type=json'
        }).success(function(data, status, headers, config) {
                    if( status != 200 ) {
                        myScheduleData.errors = eval('({"error":[{"id":'+status+',"name":"'+myScheduleData.noInfoError+'"}]})');
                    }
                    myScheduleData.myTerms = data;
                    $scope.myTerms = myScheduleData.myTerms;
                    if( myScheduleData.myTerms.term.length == 0 ) {
                        myScheduleData.errors = eval('({"error":[{"id":'+status+',"name":"'+myScheduleData.notEnrolled+' '+myScheduleData.anyTermLabel+'"}]})');
                    }
                }).error(function(data, status, headers, config) {
                    myScheduleData.errors = eval('({"error":[{"id":'+status+',"name":"'+myScheduleData.noInfoError+'"}]})');
                });
    }

    $scope.myCareerClick = function(term,career) {
        if( 'undefined' === typeof term || 'undefined' === typeof career ) {
            myScheduleData.errors = eval('({"error":[{"id":5000,"name":"'+myScheduleData.noInfoError+'"}]})');
            $log.error("No term object passed to socTermClick.");
        } else {
            myScheduleData.myCurrentTerm = term;
            myScheduleData.myCurrentCareer = career;
            myScheduleData.didChangeTermCareer = true;
        }
    }

    $scope.clickHideErrors = function() {
        myScheduleData.errors=[];
    }

    $scope.init();
});

mySchedule.controller("MyScheduleListController", function($scope,$http,$templateCache,$location,$sce,$log,myScheduleData) {
    $scope.init = function() {
        $scope.myScheduleData = myScheduleData;
        $scope.sectionMeetingDisplayLimit = myScheduleData.sectionMeetingDisplayLimit;;

        $scope.myCurrentTerm = myScheduleData.myCurrentTerm;
        $scope.myCurrentCareer = myScheduleData.myCurrentCareer;
        myScheduleData.pageTitle = myScheduleData.myCurrentTerm.shortDescription;

        if( myScheduleData.didChangeTermCareer == true ) {
            $scope.loadScheduleData();
        }
        else {
            $scope.mySchedule = myScheduleData.mySchedule;
        }
        myScheduleData.didChangeTermCareer=false;
    }

    $scope.loadScheduleData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/myAcademics/getClassSchedule?termId='+myScheduleData.myCurrentTerm.id+'&careerId='+myScheduleData.myCurrentCareer.id+'&_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                myScheduleData.errors = eval('({"error":[{"id":'+status+',"name":"'+myScheduleData.noInfoError+'"}]})');
            }
            myScheduleData.mySchedule = data;
            $scope.mySchedule = myScheduleData.mySchedule;
        }).error(function(data, status, headers, config) {
            myScheduleData.errors = eval('({"error":[{"id":'+status+',"name":"'+myScheduleData.noInfoError+'"}]})');
        });
    }


    $scope.mySectionClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            myScheduleData.errors = eval('({"error":[{"id":5000,"name":"'+myScheduleData.noInfoError+'"}]})');
            $log.error("No section object passed to socSectionClick.");
        } else {
            myScheduleData.myCurrentSection = obj;
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
                            || obj.enrollmentStatus.toLowerCase() == 'wait list'
                            || obj.enrollmentStatus.toLowerCase() == 'waiting' ) );
        }
    }

    $scope.isLastMeetingPattern = function(i) {
        return i==myScheduleData.sectionMeetingDisplayLimit;
    }

    $scope.clickHideErrors = function() {
        myScheduleData.errors=[];
    }
});

mySchedule.controller("MyScheduleSectionDetailController", function($scope,$http,$templateCache,$location,$sce,$log,myScheduleData) {
    $scope.init = function() {
        $scope.myScheduleData = myScheduleData;
        $scope.SocData = myScheduleData;
        myScheduleData.socCurrentSection = myScheduleData.myCurrentSection;
        myScheduleData.pageTitle = myScheduleData.myCurrentSection.subjectId + ' ' + myScheduleData.myCurrentSection.catalogNumber;
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
                            || obj.enrollmentStatus.toLowerCase() == 'wait list'
                            || obj.enrollmentStatus.toLowerCase() == 'waiting' ) );
        }
    }

    $scope.clickHideErrors = function() {
        myScheduleData.errors=[];
    }
});