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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme3:page title="Logout" toolName="auth" ngAppName="unAuthApp" ngControllerName="UnAuthController" ngInitFunction="init" backFunction="kmeNavLeft" cssFilename="auth" jsFilename="">
    <div id="theContentArea" class="main-view" ng-view="">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><spring:message code="kme-auth.confirm.logout"/></h3>
            </div>
            <div class="panel-body">
                <a href="${pageContext.request.contextPath}/home" class="btn btn-default active"><spring:message code="kme-auth.logout.cancel"/></a>
                <a href="${pageContext.request.contextPath}/auth/logoutConfirm" class="btn btn-default"><spring:message code="kme-auth.logout.button"/></a>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var authApp = angular.module("unAuthApp", ['ngSanitize', 'ui.bootstrap', 'xx-http-error-handling']);

        authApp.controller("UnAuthController", function ($scope, $http, $log) {
            $scope.init = function() {
                var menuItems = "{\"menus\": ["+
                    "]}";
            }

            $scope.kmeNavLeft = function () {
                window.history.back();
            }
        });
    </script>
</kme3:page>
