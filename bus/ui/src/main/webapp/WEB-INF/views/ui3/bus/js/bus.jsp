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

var busApp = angular.module("BusApp",['ngRoute','ngSanitize','ui.bootstrap'],
    function($routeProvider,$locationProvider) {
        $locationProvider.html5Mode(false);
        $routeProvider.
            <%--when('/',               {  templateUrl: '${pageContext.request.contextPath}/bus/templates/busRerouting',    controller: 'BusController' }).--%>
            when('/routes',         {  templateUrl: '${pageContext.request.contextPath}/bus/templates/busRouteList',    controller: 'BusRouteListController' }).
            when('/stops',          {  templateUrl: '${pageContext.request.contextPath}/bus/templates/busStopList',     controller: 'BusStopListController' }).
            when('/stopDetail',     {  templateUrl: '${pageContext.request.contextPath}/bus/templates/busStopDetail',   controller: 'BusStopDetailController' }).
            when('/favorites',      {  templateUrl: '${pageContext.request.contextPath}/bus/templates/busFavorites',    controller: 'BusFavoritesController' }).
            when('/nearbyStops',    {  templateUrl: '${pageContext.request.contextPath}/bus/templates/busNearbyStops',  controller: 'BusNearbyStopController' }).
            when('/map',            {  templateUrl: '${pageContext.request.contextPath}/bus/templates/busMap',          controller: 'BusMapController' }).
            <%--otherwise({ redirectTo: "/" });--%>
        <c:choose>
        <c:when test="${defaultTab == 'map'}">
        otherwise({ redirectTo: "/map" });
        </c:when>
        <c:when test="${defaultTab == 'routes'}">
        otherwise({ redirectTo: "/routes" });
        </c:when>
        <c:when test="${defaultTab == 'favorites'}">
        otherwise({ redirectTo: "/favorites" });
        </c:when>
        <c:when test="${defaultTab == 'nearbystops'}">
        otherwise({ redirectTo: "/nearbyStops" });
        </c:when>
        </c:choose>
    }
);

busApp.factory('BusData',function(){
    return {
        noInfoError:"No Bus data is available.",
        units:"${distanceUnits}",
        tabOrder:"${tabOrder}",
        campus:"${campus}",
        rawTabOrder:"${tabOrder}",
        tabOrder:[],
        tabNumber:0, // 0 = maps, 1 = routes, 2 = favorites, 3 = nearby stops

        errors:[],
        alerts:[],
        infos:[],
        successes:[],

        routes:[],
        nearbyStops:[],
        currentRoute:null,
        currentStop:null,
        favoriteStops:[],
        favoriteColor:"${favoriteDisabled}",
        defaultFavoriteColor:"${favoriteDisabled}",
        enabledFavoriteColor:"${favoriteEnabled}"
    };
});

busApp.directive('busTabs', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/bus/templates/busTabs'
    }
});


busApp.controller("BusController", function($scope,$http,$templateCache,$location,$sce,$log,BusData) {

    $scope.init = function() {
        $scope.BusData = BusData;
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];
        BusData.favoriteColor = BusData.defaultFavoriteColor;
        BusData.tabOrder = BusData.rawTabOrder.split(",");

        var menuItems = "{\"menus\": ["+
                <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
                "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];
        window.history.back();
    }

	$scope.clickHideErrors = function() {     
   		BusData.errors = [];
   		resizeMap();
	}
	
	$scope.clickHideAlerts = function() {
		BusData.alerts = [];
	}
	
	$scope.clickHideInfos = function() {
		BusData.infos = [];
	}
	
	$scope.clickHideSuccesses = function() {
		BusData.successes = [];
	}
});

busApp.controller("BusRouteListController", function($scope,$http,$templateCache,$location,$sce,$log,BusData) {

    $scope.init = function() {
        $scope.BusData = BusData;
        BusData.favoriteColor = BusData.defaultFavoriteColor;
        BusData.tabNumber = 1;
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];
        $scope.loadRouteData();
    }

    $scope.loadRouteData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/bus/route/lookup/${campus}?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
            }
            BusData.routes = data;
        }).error(function(data, status, headers, config) {
            BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
        });
    }

    $scope.busRouteClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            BusData.errors = eval('({"error":[{"id":5000,"name":"'+BusData.noInfoError+'"}]})');
            $log.error("No route object passed to busRouteClick.");
        } else {
            BusData.currentRoute = obj;
            if( 'undefined' === typeof obj.stops ) {
                BusData.errors = eval('({"error":[{"id":5000,"name":"No stops data available"}]})');
            }
        }
    }
});

busApp.controller("BusStopListController", function($scope,$http,$templateCache,$location,$sce,$log,BusData) {

    $scope.init = function() {
        $scope.BusData = BusData;
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];
        BusData.favoriteColor = BusData.defaultFavoriteColor;

        console.log("clickon a bus, paramValues: " + $location.search());
        var paramObject = $location.search();
        if (paramObject.hasOwnProperty('busId')) {
            $scope.busId = paramObject.busId;
        }
        else {
            $scope.busId = '';
        }
    }

    $scope.busStopClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            BusData.errors = eval('({"error":[{"id":5000,"name":"'+BusData.noInfoError+'"}]})');
            $log.error("No stop object passed to busStopClick.");
        } else {
            BusData.currentStop = obj;
        }
    }
});

busApp.controller("BusStopDetailController", function($scope,$http,$templateCache,$location,$sce,$log,$timeout,BusData) {

    $scope.init = function() {
        $scope.BusData = BusData;
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];

        console.log("paramValues: " + $location.search());
        var paramObject = $location.search();
        if (paramObject.hasOwnProperty('id')) {
            BusData.currentStop = paramObject;
            $scope.checkFavoriteStatus();
            $scope.loadStopData();
        }

        $scope.checkFavoriteStatus();
        $scope.refresh = ${stopRefresh} * 1000;

        $scope.timer = $timeout(
            function() {
                $scope.loadStopData();
                console.log( "Reloading stop data.", Date.now() );
            },
            $scope.refresh
        );
    };

    // Stopping the timer.
    $scope.$on(
        "$destroy",
        function( event ) {
            $timeout.cancel( $scope.timer );
        }
    );

    $scope.loadStopData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/bus/stops/'+BusData.currentStop.id+'?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
            }
            BusData.currentStop = data.stop;
            $scope.timer = $timeout(
                function() {
                    $scope.loadStopData();
                    console.log( "Reloading stop data.", Date.now() );
                },
                $scope.refresh
            );
        }).error(function(data, status, headers, config) {
            BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
        });
    }

    $scope.checkFavoriteStatus = function() {
        if( localStorage
                && localStorage.getItem( "bus_favorites" ) != null ) {
            $log.debug("Local storage is available in the browser.");
            var favs = JSON.parse( localStorage.getItem( "bus_favorites" ) );
            if( Object.prototype.toString.call( favs ) !== '[object Array]' ) {
                favs = new Array();
                favs[0] = localStorage.getItem( "bus_favorites" );
            }
            $log.debug("favs list is:"+favs);
            var n = favs.indexOf( BusData.currentStop.name );
            if( n != -1 ) {
                BusData.favoriteColor = BusData.enabledFavoriteColor;
            } else {
                BusData.favoriteColor = BusData.defaultFavoriteColor;
            }
        } else {
            $log.debug("Local storage is unavailable in the browser or no data found.");
        }
    }

    $scope.busToggleFavorite = function() {
        if( localStorage ) {
            $log.debug("Local storage is available in the browser.");
            var favs;
            if( localStorage.getItem( "bus_favorites" ) != null ) {
                favs = JSON.parse( localStorage.getItem( "bus_favorites" ) );
                if( Object.prototype.toString.call( favs ) !== '[object Array]' ) {
                    favs = new Array();
                    favs[0] = localStorage.getItem( "bus_favorites" );
                }
            } else {
                favs = new Array();
            }

            $log.debug("favs list is:"+favs);
            var n = favs.indexOf( BusData.currentStop.name );
            if( n == -1 ) {
                // Stop isn't in favorites, add it
                favs.push( BusData.currentStop.name );
                BusData.favoriteColor = BusData.enabledFavoriteColor;
            } else {
                // Stop is in favorites, remove it
                favs.splice( n, 1 );
                BusData.favoriteColor = BusData.defaultFavoriteColor;
            }
            $log.debug("bus_favorites is:"+favs);
            localStorage.setItem( "bus_favorites", JSON.stringify(favs) );
            $log.debug("bus_favorites in localStorage is:"+localStorage.getItem("bus_favorites"));
        }
    }

});

busApp.controller("BusFavoritesController", function($scope,$http,$templateCache,$location,$sce,$log,BusData) {

    $scope.init = function() {
        $scope.BusData = BusData;
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];
        BusData.tabNumber = 2;
        if( localStorage
                && localStorage.getItem( "bus_favorites" ) != null ) {
            $log.debug("Local storage is available in the browser.");
            var favs = JSON.parse( localStorage.getItem( "bus_favorites" ) );
            if( Object.prototype.toString.call( favs ) !== '[object Array]' ) {
                favs = new Array();
                favs[0] = localStorage.getItem( "bus_favorites" );
            }
            BusData.favoriteStops = favs;
        } else {
            $log.debug("Local storage is unavailable in the browser or no data found.");
        }
    }

    $scope.checkFavoriteStatus = function() {
        if( localStorage
                && localStorage.getItem( "bus_favorites" ) != null ) {
            $log.debug("Local storage is available in the browser.");
            var favs = JSON.parse( localStorage.getItem( "bus_favorites" ) );
            if( Object.prototype.toString.call( favs ) !== '[object Array]' ) {
                favs = new Array();
                favs[0] = localStorage.getItem( "bus_favorites" );
            }
            $log.debug("favs list is:"+favs);
            var n = favs.indexOf( BusData.currentStop.name );
            if( n != -1 ) {
                BusData.favoriteColor = BusData.enabledFavoriteColor;
            } else {
                BusData.favoriteColor = BusData.defaultFavoriteColor;
            }
        } else {
            $log.debug("Local storage is unavailable in the browser or no data found.");
        }
    }

    $scope.busStopClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            BusData.errors = eval('({"error":[{"id":5000,"name":"'+BusData.noInfoError+'"}]})');
            $log.error("No stop object passed to busStopClick.");
        } else {
            $http({
                method: 'GET',
                url: '<c:out value="${pageContext.request.contextPath}"/>/services/bus/stop/lookupbyname/${campus}?name='+obj+'&_type=json'
            }).success(function(data, status, headers, config) {
                if( status != 200 ) {
                    BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
                }
                BusData.currentStop = data.stop;
                $scope.checkFavoriteStatus();
            }).error(function(data, status, headers, config) {
                BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
            });
        }
    }

});

busApp.controller("BusNearbyStopController", function($scope,$http,$templateCache,$location,$sce,$log,BusData) {

    $scope.init = function() {
        $scope.BusData = BusData;
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];
        BusData.tabNumber = 3;
        $scope.lat=0;
        $scope.lon=0;
        //radius in km  1 Mile = 1609.344 Meters
        $scope.radius=8046.72;
        $scope.geoloaded=0;
        $scope.load=0;
        $scope.geoLocationfunction();
        $scope.isLoading=true;
    }

    $scope.geoLocationfunction =function() {
        if(navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                $scope.lat=position.coords.latitude;
                $scope.lon=position.coords.longitude;
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/bus/stop/nearby?latitude='+$scope.lat+'&longitude='+$scope.lon+'&radius='+$scope.radius+'&_type=json'
                }).success(function(data, status, headers, config) {
                    if( status != 200 ) {
                        BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
                    }
                    BusData.nearbyStops = data;
                    $scope.isLoading=false;
                }).error(function(data, status, headers, config) {
                    BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
                    $scope.isLoading=false;
                });
            }, function(position_error) {
                BusData.errors = eval('({"error":[{"id":'+status+',"name":"An error occured while determining your location."}]})');
                $log.error("An error occured while determining your location. Details are: "+position_error.message);
                $scope.isLoading=false;
            }, {
                enableHighAccuracy: true
            });
        } else {
            BusData.errors = eval('({"error":[{"id":'+status+',"name":"The W3C Geolocation API is not availble."}]})');
            $log.debug("The W3C Geolocation API isn't availble.");
        }
    }

    $scope.busStopClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            BusData.errors = eval('({"error":[{"id":5000,"name":"'+BusData.noInfoError+'"}]})');
            $log.error("No stop object passed to busStopClick.");
        } else {
            BusData.currentStop = obj;
        }
    }
});

busApp.controller("BusMapController", function($scope,$http,$templateCache,$location,$sce,$log,$modal,BusData) {
    $scope.init = function() {
        $scope.BusData = BusData;
        BusData.errors = [];
        BusData.alerts = [];
        BusData.infos = [];
        BusData.successes = [];
        BusData.favoriteColor = BusData.defaultFavoriteColor;
        BusData.tabNumber = 0;
        $scope.loadingRoutes = true;
        $scope.loadRouteData();
        $scope.alertInterval = -1; //no auto sliding if negative
    }
    
    $scope.errorNotification = function(errorMessage) {
    	BusData.errors = eval('({"error":[{"id":"errorAcc","name":"' + errorMessage + '"}]})');
    	$scope.$apply();
    }
        
    $scope.successNotification = function(successMessage) {
    	BusData.successes = eval('({"success":[{"id":"successId","name":"' + successMessage + '"}]})');
    	$scope.$apply();
    }
    
    $scope.infoNotification = function(infoMessage) {
    	BusData.infos = eval('({"info":[{"id":"infoId","name":"' + infoMessage + '"}]})');
    	$scope.$apply();
    }
    
    $scope.alertNotification = function(alertMessage) {
    	BusData.alerts = eval('({"alert":[{"id":"alertId","name":"' + alertMessage + '"}]})');
    }

    $scope.loadRouteData = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/bus/route/lookup/${campus}?_type=json'
        }).success(function(data, status, headers, config) {
            if( status != 200 ) {
                BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
            }
            initialize('map_canvas', ${initialLatitude}, ${initialLongitude}, '${campus}', $modal);
            BusData.routes = data;
            $scope.loadingRoutes = false;
            onGoogleLoadComplete();
            buildRoutes(data);
            resizeMap();
        }).error(function(data, status, headers, config) {
            BusData.errors = eval('({"error":[{"id":'+status+',"name":"'+BusData.noInfoError+'"}]})');
        });
    }
});
