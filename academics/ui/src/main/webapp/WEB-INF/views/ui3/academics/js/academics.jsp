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
