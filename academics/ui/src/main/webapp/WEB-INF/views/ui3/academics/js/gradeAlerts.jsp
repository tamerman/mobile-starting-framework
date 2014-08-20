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
<spring:message code="academics.gradeAlerts.msgFail" var="msgGradeAlertsMsgFail"/>

var gradeAlerts = angular.module("gradeAlertsApp", ['ngSanitize', 'ui.bootstrap', 'xx-http-error-handling']);

gradeAlerts.controller("GradeAlertsController", function ($scope, $http, $log) {
    $scope.init = function () {
        $scope.isNative = "<c:out value="${cookie['native'].value}"/>";
        //console.log("isNative=" + $scope.isNative);

        $scope.getGradeAlertOpt() ;

        $scope.testResult = '-1';
        var menuItems = "{\"menus\": [" +
                    "{ \"url\":\"/academics\" , \"label\":\"<c:out value="${msgAcademicsTitle}"/>\" }," +
                    "{\"divider\":\"true\"}," +
                    "{ \"url\":\"/academics/terms\" , \"label\":\"<c:out value="${msgAcademicsScheduleOfClasses}"/>\" }," +
                    "{ \"url\":\"/myAcademics\" , \"label\":\"<c:out value="${msgAcademicsClassSchedule}"/>\" }," +
                    "{ \"url\":\"/academics/search\" , \"label\":\"<c:out value="${msgAcademicsClassSearch}"/>\" }," +
                    "{ \"url\":\"/myAcademics/gradeAlerts\" , \"label\":\"<c:out value="${msgAcademicsGradeAlerts}"/>\" }," +
//                        "{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+
                    "]}";

        $scope.menuItems = eval("(" + menuItems + ")");
    };

    $scope.getGradeAlertOpt = function () {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/myAcademics/getGradeAlertOpt?_type=json'
        }).success(function (data, status, headers, config) {
            $scope.gradeAlertOpt = data;
        }).error(function (data, status, headers, config) {
            $log.error ( status );
        });
    };

    $scope.update = function () {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/myAcademics/updateGradeAlertOpt?opt=' + $scope.gradeAlertOpt + '&_type=json'
        }).success(function (data, status, headers, config) {
            if (data === true || data === 'true') {
                        // Not display the successful result
                $scope.updateResult = "";
            }
            else {
                $scope.updateResult = "<c:out value="${msgGradeAlertsMsgFail}"/>";
            }

        }).error(function (data, status, headers, config) {
            $scope.updateResult = "<c:out value="${msgGradeAlertsMsgFail}"/>";
        });
    };

    $scope.gradeAlertTest = function () {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/myAcademics/testGradeAlert'
        }).success(function (data, status, headers, config) {
            $scope.testResult = data;
        }).error(function (data, status, headers, config) {
            $scope.testResult = '0';
        });
    };

    $scope.kmeNavLeft = function () {
        window.location.href = '<c:out value="${pageContext.request.contextPath}"/>/academics';
    }
});

