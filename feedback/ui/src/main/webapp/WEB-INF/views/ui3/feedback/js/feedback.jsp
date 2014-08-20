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
<spring:message code="feedback.title" var="feedbackTitle"/>
<spring:message code="feedback.thanks" var="thanksString"/>

var labs = angular.module("feedbackApp", ['ngSanitize','ui.bootstrap']);

labs.controller("FeedbackController", function ($scope, $http, $templateCache, $location, $sce, $log) {

            $scope.init = function () {
            	$scope.errors = null;
            	$scope.alerts = null;
            	$scope.infos = null;
            	$scope.successes = null;
                $scope.feedbackTitle = "<c:out value="${feedbackTitle}"/>";
                $scope.pageTitle = $scope.feedbackTitle;
                $scope.noInfoError = "Feedback submission failed.";

                var menuItems = "{\"menus\": [" +
                        <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }" +--%>
                        "]}";

                $scope.menuItems = eval("(" + menuItems + ")");

                var browserDetect = window.kme.browserDetect;
                if (browserDetect.isDesktop()) {
                    $scope.deviceType = "feedback.computer";
                }
                else if (browserDetect.isOS(BrowserDetect.OSes.Android)) {
                    $scope.deviceType = "feedback.android";
                }
                else if (browserDetect.isOS(BrowserDetect.OSes.BlackBerry)) {
                    $scope.deviceType = "feedback.blackberry";
                }
                else if (browserDetect.isBrowser(BrowserDetect.browsers.IEMobile)) {
                    $scope.deviceType = "feedback.windowsMobile";
                }
                else if (browserDetect.isIOS()) {
                    if (browserDetect.isOS(BrowserDetect.OSes.iPad)) {
                        $scope.deviceType = "feedback.ipad";
                    }
                    else if (browserDetect.isOS(BrowserDetect.OSes.iPod)) {
                        $scope.deviceType = "feedback.ipod";
                    }
                    else {
                        $scope.deviceType = "feedback.iphone";
                    }
                }
                else {
                    $scope.deviceType = "feedback.default";
                }
            }

            $scope.kmeNavLeft = function () {
                window.history.back();
            }

            $scope.killMessage = function () {
                $scope.message = "";
                $scope.error = false;
            }

            $scope.submitForm = function (isValid) {

                $scope.killMessage();
                $scope.submitted = true;

                if (isValid) {
                    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                    var data = new Object();
                    data.service = $scope.service;
                    data.deviceType = $scope.deviceType;
                    data.noteText = $scope.noteText;
                    data.email = $scope.email;
                    data.userAgent = '${header['User-Agent']}';
                    data.campus = '${cookie['campusSelection'].value}';

                    console.log(data);

                    var url = "<c:out value="${pageContext.request.contextPath}"/>/services/feedback/send";
                    $http({
                        method: 'POST',
                        url: url,
                        data: data,
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).success(function (data, status, headers, config) {
                        // TODO
                        console.log("SUCCESS");
                        $scope.successes = eval('({"success":[{"id": " + status + ","name": "${thanksString}" }]})');
                        $scope.error = false;
                    }).error(function (data, status, headers, config) {
                        // TODO
                        console.log("ERROR" + status);
                        $scope.errors = eval('({"error":[{"id": " + status + ","name": $scope.noInfoError }]})');
                        $scope.error = true;
                    });
                }
            }

			$scope.clickHideErrors = function() {     
        		$scope.errors = [];
    		}
    		$scope.clickHideAlerts = function() {     
            	$scope.alerts = [];
    		}
    		$scope.clickHideInfos = function() {     
            	$scope.infos = [];
    		}
    		$scope.clickHideSuccesses = function() {     
            	$scope.successes = [];
    		}
        });

labs.directive('ngFocus', [function () {
            var FOCUS_CLASS = "ng-focused";
            return {
                restrict: 'A',
                require: 'ngModel',
                link: function (scope, element, attrs, ctrl) {
                    ctrl.$focused = false;
                    element.bind('focus', function (evt) {
                        element.addClass(FOCUS_CLASS);
                        scope.$apply(function () {
                            ctrl.$focused = true;
                        });
                    }).bind('blur', function (evt) {
                        element.removeClass(FOCUS_CLASS);
                        scope.$apply(function () {
                            ctrl.$focused = false;
                        });
                    });
                }
            }
        }]);