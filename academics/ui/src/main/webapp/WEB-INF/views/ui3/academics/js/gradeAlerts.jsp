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

