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

        <spring:message code="academics.title" var="title"/>
        <spring:message code="academics.scheduleOfClasses" var="scheduleOfClasses"/>
        <spring:message code="academics.scheduleOfClassesDesc" var="scheduleOfClassesDesc"/>
        <spring:message code="academics.classSchedule" var="classSchedule"/>
        <spring:message code="academics.classScheduleDesc" var="classScheduleDesc"/>
        <spring:message code="academics.classSearch" var="classSearch"/>
        <spring:message code="academics.classSearchDesc" var="classSearchDesc"/>
        <spring:message code="academics.gradeAlerts" var="gradeAlerts"/>
        <spring:message code="academics.gradeAlertsDesc" var="gradeAlertsDesc"/>


        var academics = angular.module("academicsApp", ['ngSanitize','ui.bootstrap']);

        academics.controller("academicsController", function($scope,$http,$templateCache,$location,$sce,$log) {
            $scope.init = function() {
                $scope.academicsOptions = [];
                <c:forEach var="academic" items="${homeScreenTools}" varStatus="loop">
                var academicsOption = new Object();
                if ("browseclasses" == '${academic}') {
                    academicsOption.name = '${scheduleOfClasses}';
                    academicsOption.description = '${scheduleOfClassesDesc}';
                    academicsOption.path = "/academics/terms";
                    academicsOption.image = "/images/academics/browse-classes.png";
                    $scope.academicsOptions.push(academicsOption);
                } else if ("myschedule" == '${academic}') {
                    academicsOption.name = '${classSchedule}';
                    academicsOption.description = '${classScheduleDesc}';
                    academicsOption.path = "/myAcademics/";
                    academicsOption.image = "/images/academics/my-class-schedule.png";
                    $scope.academicsOptions.push(academicsOption);
                } else if ("advancedsearch" == '${academic}') {
                    academicsOption.name = '${classSearch}';
                    academicsOption.description = '${classSearchDesc}';
                    academicsOption.path = "/academics/search";
                    academicsOption.image = "/images/academics/course-search.png";
                    $scope.academicsOptions.push(academicsOption);
                } else if ("gradealerts" == '${academic}' && "<c:out value="${cookie['native'].value}"/>"=="yes") {
                    academicsOption.name = '${gradeAlerts}';
                    academicsOption.description = '${gradeAlertsDesc}';
                    academicsOption.path = "/myAcademics/gradeAlerts";
                    academicsOption.image = "/images/academics/grade-alert.png";
                    $scope.academicsOptions.push(academicsOption);
                }
                </c:forEach>

                var menuItems = "{\"menus\": ["+
                        "{ \"url\":\"/academics\" , \"label\":\"<c:out value="${title}"/>\" },"+
//		            "{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+
                        "]}";

                $scope.menuItems = eval ("(" + menuItems + ")");
            };

            $scope.kmeNavLeft = function() {
                window.history.back();
            }
        });
