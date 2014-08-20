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
