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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="maps.title" var="msgCat_ToolTitle"/>
<spring:message code="maps.search" var="msgCat_Search"/>
<spring:message code="maps.searchErrorMessage" var="searchErrorMessage"/>

var maps = angular.module("MapApp", ['ngRoute','ngSanitize','ui.bootstrap'],
	function($routeProvider, $locationProvider) {
		$locationProvider.html5Mode(false);
		$routeProvider.
			when('/', {
				templateUrl: '${pageContext.request.contextPath}/maps/templates/google',
				controller: 'MapController'
			}).when('/:city/:postalCode/:state/:street1/:street2', {
				templateUrl: '${pageContext.request.contextPath}/maps/templates/google',
				controller: 'MapController'
			}).when('/:city/:postalCode/:state/:street1', {
				templateUrl: '${pageContext.request.contextPath}/maps/templates/google',
				controller: 'MapController'
			}).when('/:latitude/:longitude', {
				templateUrl: '${pageContext.request.contextPath}/maps/templates/google',
				controller: 'MapController'
			}).otherwise({
				redirectTo: '/'
			});
	}
);

maps.controller("MapController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log) {

    $scope.init = function() {
        $scope.markersArray = [];
        $scope.userMarkersArray = [];
        $scope.searchText = null;
        $scope.campus = "${campus}";
        $scope.errors = [];
        $scope.infos = [];
        $scope.alerts = [];
        $scope.successes = [];
        $scope.locations = null;
        $scope.showResults = false;
        $scope.noInfoError = "No map data found.";

        $scope.initialLocation = new google.maps.LatLng(${initialLatitude},${initialLongitude});
        $scope.mapOptions = {
            center: $scope.initialLocation,
            zoom: ${initialZoom},
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        $scope.map = new google.maps.Map(document.getElementById("map_canvas"),$scope.mapOptions);

        $scope.geocoder = new google.maps.Geocoder();
        // Remove all references to the current map before purging the markers to avoid
		// a memory leak on the client side.
		for (var i = 0; i < $scope.markersArray.length; i++) {
		   	$scope.markersArray[i].setMap(null);
		}
		$scope.markersArray = [];
        if ('undefined' !== typeof $routeParams.street1) {
        	$scope.address = $routeParams.city + ' ' + $routeParams.postalCode + ' ' + $routeParams.state + ' ' + $routeParams.street1 + ' ' + $routeParams.street2;
	        $scope.geocoder.geocode( { 'address': $scope.address}, function(results, status) {
			    if (status == google.maps.GeocoderStatus.OK) {
			    	$scope.map.setCenter(results[0].geometry.location);
			    	
			      	var marker = new google.maps.Marker({
			        	map: $scope.map,
			          	position: results[0].geometry.location
			      	});
			      	$scope.markersArray.push( marker );
			    } else {
			      	$scope.errors = eval('({"error":[{"id":'+status+',"name":"Geocode was not successful for the following reason: " + status}]})');
				}
			});
		} else if('undefined' !== typeof $routeParams.latitude) {
			var foundLocation = new google.maps.LatLng($routeParams.latitude, $routeParams.longitude);
			$scope.map.setCenter(foundLocation);
            var foundMarker = new google.maps.Marker({
                position: foundLocation,
                map: $scope.map,
                title: "test",
                animation: google.maps.Animation.DROP});
            $scope.markersArray.push( foundMarker );
		}

        var menuItems = "{\"menus\": ["+
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
        "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
	    $scope.errors = [];
        $scope.infos = [];
        $scope.alerts = [];
        $scope.successes = [];
        window.history.back();
    }

    $scope.searchForMapLocations = function() {
        if( this.searchText ) {
            $scope.map.markers = [];
            $http({
                method: 'GET',
                url: '<c:out value="${pageContext.request.contextPath}"/>/services/maps/search?searchText='+this.searchText+'&campus='+$scope.campus+'&_type=json'
            }).success(function(data, status, headers, config) {
                if( status != 200 ) {
                    $scope.errors = eval('({"error":[{"id":'+status+',"name":"'+$scope.noInfoError+'"}]})');
                } else {
                    $scope.locations = data;
                    if( 'undefined' === typeof $scope.locations.location
                        || $scope.locations.location.length == 0 ) {
                        $scope.errors = eval('({"error":[{"id":'+status+',"name":"'+$scope.noInfoError+'"}]})');
                    } else {
                        $scope.showResults = true;
                    }
                }
            }).error(function(data, status, headers, config) {
                $scope.errors = eval('({"error":[{"id":'+status+',"name":"'+$scope.noInfoError+'"}]})');
            });
        }
    }

    $scope.clickLocation = function(obj) {
        if( 'undefined' === typeof obj ) {
            $scope.errors = eval('({"error":[{"id":5000,"name":"'+$scope.noInfoError+'"}]})');
            $log.error("No location object passed to clickLocation.");
        } else {
            // Remove all references to the current map before purging the markers to avoid
            // a memory leak on the client side.
            for (var i = 0; i < $scope.markersArray.length; i++) {
                $scope.markersArray[i].setMap(null);
            }
            $scope.markersArray = [];
            $scope.currentLocation = obj;
            var myLocation = new google.maps.LatLng(obj.latitude, obj.longitude);
            var myMarker = new google.maps.Marker({
                position: myLocation,
                map: $scope.map,
                title: obj.name,
                animation: google.maps.Animation.DROP});
            $scope.markersArray.push( myMarker );
            $scope.showResults = false;

            var contentString = '<div id="content">'+
                    '<div id="siteNotice">'+
                    '</div>'+
                    '<h1 id="firstHeading" class="firstHeading">'+
                    obj.name+'</h1>'+
                    '<div id="bodyContent">';
                if( 'undefined' !== typeof obj.description ) {
                    contentString+='<p>'+obj.description+'</p>';
                }
                contentString+='<p>';
                if( 'undefined' != typeof obj.streetNumber ) {
                    contentString+=obj.streetNumber+' ';
                }
                if( 'undefined' != typeof obj.streetDirection ) {
                    contentString+=obj.streetDirection+' ';
                }
                if( 'undefined' != typeof obj.street ) {
                    contentString+=obj.street;
                }
                contentString+='<br/>';
                if( 'undefined' != typeof obj.city ) {
                    contentString+=obj.city+' ';
                }
                if( 'undefined' != typeof obj.state ) {
                    contentString+=obj.state+' ';
                }
                if( 'undefined' != typeof obj.zip ) {
                    contentString+=obj.zip;
                }
                contentString+='</p>'+
                    '</div>'+
                    '</div>';

            var infowindow = new google.maps.InfoWindow({
                content: contentString
            });

            google.maps.event.addListener(myMarker, 'click', function() {
                infowindow.open($scope.map,myMarker);
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