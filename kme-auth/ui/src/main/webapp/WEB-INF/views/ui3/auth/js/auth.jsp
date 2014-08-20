<%--
Copyright 2014-2014 The Kuali Foundation Licensed under the Educational
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

var auth = angular.module("authApp", ['ngRoute','ngSanitize','ui.bootstrap'],
    function($routeProvider,$locationProvider) {
        $locationProvider.html5Mode(false);
        $routeProvider.
            when('/', {  templateUrl: '${pageContext.request.contextPath}/auth/templates/auth',     controller: 'AuthController' }).
            otherwise({ redirectTo: "/" });
    });

auth.factory('AuthData',function() {
    return {
        errors:null,
        infos:null,
        successes:null,
        alerts:null
    };
});

auth.directive('notificationList', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/auth/templates/notificationList'
    }
});

auth.directive('authDirective', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/auth/templates/auth'
    }
});

auth.controller("AuthController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,AuthData) {
    $scope.init = function() {
        $scope.AuthData = AuthData;
        AuthData.errors = [];
        AuthData.alerts = [];
        AuthData.infos = [];
        AuthData.successes = [];

        var menuItems = "{\"menus\": ["+
            <%--"{ \"url\":\"/auth\" , \"label\":\"Auth\" },"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
        // send to home screen.
        AuthData.errors = [];
        AuthData.alerts = [];
        AuthData.infos = [];
        AuthData.successes = [];
        window.history.back();
    }

    $scope.doLogin = function() {
        var data = new Object();
        data.loginName = $scope.loginName;
        data.password = $scope.password;
        $http({
            method: 'POST',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/authentication/login.json',
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: data,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
            if( data.authenticationResponse.didAuthenticate == false ) {
                var errorMsg = new Object();
                errorMsg.name = data.authenticationResponse.message;
                AuthData.errors.push(errorMsg);
            } else {
                window.location = "${pageContext.request.contextPath}/home";
            }
        }).error(function(data, status, headers, config) {
            var errorMsg = new Object();
            errorMsg.name = "HTTP status was "+status;
            PeopleData.errors.push(errorMsg);
        });
        AuthData.errors = [];
    }

    $scope.clickHideErrors = function(obj) {
        var i = AuthData.errors.indexOf(obj);
        if( i > -1 ) {
            AuthData.errors.splice(i,1);
        }
    }

    $scope.clickHideSuccesses = function(obj) {
        var i = AuthData.successes.indexOf(obj);
        if( i > -1 ) {
            AuthData.successes.splice(i,1);
        }
    }

    $scope.clickHideInfos = function(obj) {
        var i = AuthData.infos.indexOf(obj);
        if( i > -1 ) {
            AuthData.infos.splice(i,1);
        }
    }

    $scope.clickHideAlerts = function(obj) {
        var i = AuthData.alerts.indexOf(obj);
        if( i > -1 ) {
            AuthData.alerts.splice(i,1);
        }
    }
});
