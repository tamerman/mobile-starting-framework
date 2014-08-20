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

<spring:message code="mdot.aboutLabel" var="msgCat_ToolTitle"/>

var about = angular.module("aboutApp", ['ngSanitize','ui.bootstrap']);

about.controller("AboutController", function($scope,$http,$templateCache,$location,$sce,$log) {

    $scope.init = function() {
        $scope.homeToolTitle = "<c:out value="${msgCat_homeTitle}"/>";
        $scope.pageTitle = $scope.homeToolTitle;

        var menuItems = "{\"menus\": ["+
                <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
                "]}";
        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
        window.history.back();
    }
});