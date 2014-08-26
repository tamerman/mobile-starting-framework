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
