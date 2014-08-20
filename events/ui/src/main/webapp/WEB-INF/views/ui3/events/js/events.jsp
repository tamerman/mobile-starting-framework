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
        <spring:message code="events.title.byDate" var="msgCat_ToolTitle"/>
        <spring:message code="events.eventDetail" var="eventDetail"/>
        <spring:message code="events.title.byCategory" var="eventCategory"/>

        var events = angular.module("eventsApp", ['ngRoute', 'ngSanitize', 'ui.bootstrap'],
                function ($routeProvider, $locationProvider) {
                    $locationProvider.html5Mode(false);
                    $routeProvider.
                            when('/', {  templateUrl: '${pageContext.request.contextPath}/events/templates/eventHome', controller: 'EventsController' }).
                            when('/byDate', {  templateUrl: '${pageContext.request.contextPath}/events/templates/eventsForDateRange', controller: 'EventsController' }).
                            when('/eventsDateRangeResult', {  templateUrl: '${pageContext.request.contextPath}/events/templates/EventsDateRangeResult', controller: 'EventsController' }).
                            when('/detail', {  templateUrl: '${pageContext.request.contextPath}/events/templates/eventDetail', controller: 'EventsDetailController' }).
                            when('/category', {  templateUrl: '${pageContext.request.contextPath}/events/templates/category', controller: 'EventsCategoryController' }).
                            when('/viewEvents', {  templateUrl: '${pageContext.request.contextPath}/events/templates/eventsList', controller: 'EventsController' }).
                            otherwise({ redirectTo: "/" });
                });

        events.factory('EventsData', function () {
            return {
                allEvents: null,
                events: null,
                eventsForDateRange: null,
                currentEvent: null,
                date: "${today}",
                date1: null,
                errors:null,
        		infos:null,
        		alerts:null,
        		successes:null,
                eventsPageTitle: null
            };
        });

        events.directive('eventTabs', function() {
            return {
                restrict:'E',
                transclude:true,
                replace:true,
                templateUrl: '${pageContext.request.contextPath}/events/templates/eventTabs'
            }
        });
        events.directive('notificationList', function() {
		    return {
		        restrict:'E',
		        transclude:true,
		        replace:true,
		        templateUrl: '${pageContext.request.contextPath}/events/templates/notificationList'
		    }
		});
        

        events.controller("EventsController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, EventsData) {

            $scope.init = function () {
                $scope.EventsData = EventsData;
                EventsData.errors = [];
		      	EventsData.alerts = [];
		      	EventsData.infos = [];
		      	EventsData.successes = [];
                $scope.eventDetail = "<c:out value="${eventDetail}"/>";
                $scope.eventCategory = "<c:out value="${eventCategory}"/>";
                $scope.msgCat_ToolTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                //$scope.eventsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                //EventsData.eventsPageTitle = $scope.eventsPageTitle;

                /**
                 * Defaults for date fields
                 */
                var year = new Date().getFullYear();
                EventsData.startDate = new Date(year, 0, 1);
                EventsData.endDate = new Date(year, 11, 31);
                $scope.eventsSession = EventsData;

                if ($location.path() === '/category') {
                    console.log("$location.path() === /category  , loading catelog...");
                    $scope.loadCategories();;
                }
                else {
                    console.log("$location.path() != /category, loading events...");
                    $scope.loadEvents();

                }

                var menuItems = "{\"menus\": [" +
                        "{ \"url\":\"/events\" , \"label\":\"Events Home\" }," +
                        <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }" +--%>
                        "]}";

                $scope.menuItems = eval("(" + menuItems + ")");
            }


            $scope.open = function(flag, $event) {
                $event.preventDefault();
                $event.stopPropagation();

                if('start' === flag){
                    $scope.endDateOpen = false;
                    $scope.startDateOpen = true;
                }
                else if ('end' === flag){
                    $scope.startDateOpen = false;
                    $scope.endDateOpen = true;
                }
            };


            $scope.kmeNavLeft = function () {
                // send to home screen.
                EventsData.errors = [];
                EventsData.alerts = [];
                EventsData.infos = [];
                EventsData.successes = [];
                window.history.back();
            }

            $scope.SearchClick = function(obj) {
                var fromDate = obj.startDate.getFullYear()+"-"+("0" + (obj.startDate.getMonth() + 1)).slice(-2)+"-"+("0" + obj.startDate.getDate()).slice(-2);
                var toDate = obj.endDate.getFullYear()+"-"+("0" + (obj.endDate.getMonth() + 1)).slice(-2)+"-"+("0" + obj.endDate.getDate()).slice(-2);
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/getEventsForDateRange?fromDate=' + fromDate + '&toDate='+ toDate + '&_type=json'
                }).success(function (data, status, headers, config) {
                            if (status != 200) {
                                EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load events feeds"}]})');
                            }
                            EventsData.eventsForDateRange = data;
                            $scope.eventsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                            EventsData.eventsPageTitle = $scope.eventsPageTitle;

                        }).error(function (data, status, headers, config) {
                            EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                        });

            };

            $scope.nextDayClick = function () {
                var currentDate = EventsData.date;
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/nextDay?currentDate=' + currentDate + '&_type=json'
                }).success(function (result, status, headers, config) {
                            if (status != 200) {
                                EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Category Feeds"}]})');
                            }
                            EventsData.date = result;

                            $http({
                                method: 'GET',
                                url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/onDate?date=' + result + '&_type=json'
                            }).success(function (data, status, headers, config) {
                                        if (status != 200) {
                                            EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load events feeds"}]})');
                                        }
                                        EventsData.events = data;
                                        EventsData.date = result;
                                        $scope.eventsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                                        EventsData.eventsPageTitle = $scope.eventsPageTitle;
                                    }).error(function (data, status, headers, config) {
                                        EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                                    });

                        }).error(function (data, status, headers, config) {
                            EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                        });
            }


            $scope.previousDayClick = function () {
                var st = EventsData.date;
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/previousDay?currentDate=' + st + '&_type=json'
                }).success(function (result, status, headers, config) {
                            if (status != 200) {
                                EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Category Feeds"}]})');
                            }
                            EventsData.date = result;

                            $http({
                                method: 'GET',
                                url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/onDate?date=' + result + '&_type=json'
                            }).success(function (data, status, headers, config) {
                                        if (status != 200) {
                                            EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load events feeds"}]})');
                                        }
                                        EventsData.events = data;
                                        EventsData.date = result;
                                        $scope.eventsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                                        EventsData.eventsPageTitle = $scope.eventsPageTitle;

                                    }).error(function (data, status, headers, config) {
                                        EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                                    });

                        }).error(function (data, status, headers, config) {
                            EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                        });
            }


            $scope.isActive = function (viewLocation) {
                var active = (viewLocation === $location.path());
                return active;
            };

            $scope.loadEvents = function () {
                var d = new Date();
                var currentDate;
                var year = d.getFullYear();
                var month = d.getMonth() + 1;
                if (month < 10) {
                    month = "0" + month;
                }
                ;

                var day = ("0" + d.getDate()).slice(-2)
                currentDate = year + "-" + month + "-" + day;
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/onDate?date=' + currentDate + '&_type=json'
                }).success(function (data, status, headers, config) {
                    if (status != 200) {
                        EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load events feeds"}]})');
                    }
                    EventsData.events = data;
                    EventsData.date = year + "-" + month + "-" + day;
                    $scope.eventsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                    EventsData.eventsPageTitle = $scope.eventsPageTitle;

                }).error(function (data, status, headers, config) {
                    EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                });
            }


            $scope.categoryClick = function (category) {
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/byCategory/' + category.categoryId + '?_type=json'
                }).success(function (data, status, headers, config) {
                            if (status != 200) {
                                EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Category Feeds"}]})');
                            }
                            EventsData.allEvents = data;

                    $scope.groupBy( 'displayStartDate', EventsData.allEvents.event);

                            $scope.eventsPageTitle = category.title;
                            EventsData.eventsPageTitle = $scope.eventsPageTitle;
                        }).error(function (data, status, headers, config) {
                            EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                        });
            }

            $scope.loadCategories = function () {
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/events/categories?_type=json'
                }).success(function (data, status, headers, config) {
                            if (status != 200) {
                                EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Category Feeds"}]})');
                            }
                            EventsData.categories = data;
                            $scope.eventsPageTitle = "<c:out value="${eventCategory}"/>";
                            EventsData.eventsPageTitle = EventsData.eventsPageTitle;

                        }).error(function (data, status, headers, config) {
                            EventsData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Events Feeds"}]})');
                        });
            }


            $scope.eventClick = function (obj) {
                if ('undefined' === typeof obj) {
                    EventsData.errors = eval('({"error":[{"id":5000,"name":"Failed to find event data."}]})');
                    $log.error("No event object passed to eventClick.");
                } else {
                    EventsData.currentEvent = obj;
                }
            }
            
            $scope.clickHideErrors = function() {     
		   		EventsData.errors = [];
			}
			$scope.clickHideAlerts = function() {     
		       	EventsData.alerts = [];
			}
			$scope.clickHideInfos = function() {     
		       	EventsData.infos = [];
			}
			$scope.clickHideSuccesses = function() {     
		       	EventsData.successes = [];
			}

            $scope.groupBy = function( attribute,  eventList ) {
                $scope.groups = [];
                var groupValue = "_INVALID_GROUP_VALUE_";
                for (var i=0; i<eventList.length; i++) {
                    var oneEvent = eventList[i];

                    if (oneEvent[attribute] !== groupValue) {
                        var group = {
                            label: oneEvent[attribute],
                            eventsByLabel: []
                        };
                        groupValue = group.label;
                        $scope.groups.push(group);
                    }

                    group.eventsByLabel.push(oneEvent);
                }
            }
        });


        events.controller("EventsCategoryController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, EventsData) {
            $scope.init = function () {
                $scope.EventsData = EventsData;
                EventsData.errors = [];
		      	EventsData.alerts = [];
		      	EventsData.infos = [];
		      	EventsData.successes = [];
                $scope.eventCategory = "<c:out value="${eventCategory}"/>";
                $scope.eventsPageTitle = "<c:out value="${eventCategory}"/>";
                EventsData.eventsPageTitle = $scope.eventsPageTitle;


                $scope.loadCategories();
            }
            
            $scope.clickHideErrors = function() {     
		   		EventsData.errors = [];
			}
			$scope.clickHideAlerts = function() {     
		       	EventsData.alerts = [];
			}
			$scope.clickHideInfos = function() {     
		       	EventsData.infos = [];
			}
			$scope.clickHideSuccesses = function() {     
		       	EventsData.successes = [];
			}
        });


        events.controller("EventsByDateRangeController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, EventsData) {
            $scope.init = function () {
                $scope.EventsData = EventsData;
                EventsData.errors = [];
                EventsData.alerts = [];
                EventsData.infos = [];
                EventsData.successes = [];
                $scope.eventsPageTitle = "<c:out value="${eventCategory}"/>";
                EventsData.eventsPageTitle = $scope.eventsPageTitle;


                $scope.loadCategories();
            }

            $scope.clickHideErrors = function() {
                EventsData.errors = [];
            }
            $scope.clickHideAlerts = function() {
                EventsData.alerts = [];
            }
            $scope.clickHideInfos = function() {
                EventsData.infos = [];
            }
            $scope.clickHideSuccesses = function() {
                EventsData.successes = [];
            }
        });


        events.controller("EventsDetailController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, EventsData) {
            $scope.init = function () {
                $scope.EventsData = EventsData;
                EventsData.errors = [];
		      	EventsData.alerts = [];
		      	EventsData.infos = [];
		      	EventsData.successes = [];
                $scope.eventDetail = "<c:out value="${eventDetail}"/>";
                $scope.eventCategory = "<c:out value="${eventCategory}"/>";
                $scope.msgCat_ToolTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                $scope.eventsPageTitle = "<c:out value="${eventDetail}"/>";
                EventsData.eventsPageTitle = $scope.eventsPageTitle;
                $scope.currentEvent = EventsData.currentEvent;
            }
            
            $scope.clickHideErrors = function() {     
		   		EventsData.errors = [];
			}
			$scope.clickHideAlerts = function() {     
		       	EventsData.alerts = [];
			}
			$scope.clickHideInfos = function() {     
		       	EventsData.infos = [];
			}
			$scope.clickHideSuccesses = function() {     
		       	EventsData.successes = [];
			}
        });
