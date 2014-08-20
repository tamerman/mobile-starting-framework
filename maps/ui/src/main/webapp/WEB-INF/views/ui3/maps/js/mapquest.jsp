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
				templateUrl: '${pageContext.request.contextPath}/maps/templates/mapquest',
				controller: 'MapController'
			}).when('/:city/:postalCode/:state/:street1/:street2', {
				templateUrl: '${pageContext.request.contextPath}/maps/templates/mapquest',
				controller: 'MapController'
			}).when('/:city/:postalCode/:state/:street1', {
				templateUrl: '${pageContext.request.contextPath}/maps/templates/mapquest',
				controller: 'MapController'
			}).when('/:latitude/:longitude', {
				templateUrl: '${pageContext.request.contextPath}/maps/templates/mapquest',
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

        $scope.mapOptions = {
            elt: document.getElementById('map_canvas'),
            zoom: ${initialZoom},
            latLng:{lat:${initialLatitude},lng:${initialLongitude}},
            mtype: 'osm'
        };
        $scope.map = new MQA.TileMap($scope.mapOptions);

        MQA.withModule('geocoder', 'largezoom', 'viewoptions', 'traffictoggle', 'mousewheel', 'directions', function() {
            $scope.map.addControl(new MQA.LargeZoom(), new MQA.MapCornerPlacement(MQA.MapCorner.TOP_LEFT, new MQA.Size(5,5)));
            $scope.map.addControl(new MQA.TrafficToggle());
            $scope.map.addControl(new MQA.ViewOptions());
            $scope.map.enableMouseWheelZoom();
            
            <%-- checking for route params for either street address or lat/long pair and adding a marker for that location --%>
            if ('undefined' !== typeof $routeParams.street1) {
            	MQA.Geocoder.constructPOI = function(location) {
					var lat = location.latLng.lat,
				    	lng = location.latLng.lng,
				    	street = location.street,
				        city = location.adminArea5,
				        state = location.adminArea3,
				        p = new MQA.Poi({ lat: lat, lng: lng });
				 
					p.setRolloverContent('<div style="white-space: nowrap">' + city + ', ' + state + '</div>');
				    p.setInfoTitleHTML(p.getRolloverContent());
				    p.setInfoContentHTML('<div style = "white-space: nowrap">' + city + ', '
				      + state + '<br />' + street + '</div>');
				    return p;
				};
	        	$scope.map.geocodeAndAddLocations( {street:$routeParams.street1 + ' ' + $routeParams.street2, city:$routeParams.city, state:$routeParams.state, postalCode:$routeParams.postalCode} );
	        <%--
			var geoCodeUrl = 'http://www.mapquestapi.com/geocoding/v1/address?key=' + "${mapquestApiKey}" + '&street=' + $routeParams.street1 + ' ' + $routeParams.street2 + '&city=' + $routeParams.city + '&state=' + $routeParams.state + '&postalCode=' + $routeParams.postalCode;
			--%>
			} else if('undefined' !== typeof $routeParams.latitude) {
				for (var i = 0; i < $scope.markersArray.length; i++) {
                	$scope.map.removeShape($scope.markersArray[i]);
	            }
	            $scope.markersArray = [];
	            var myMarker = new MQA.Poi( {lat:$routeParams.latitude, lng:$routeParams.longitude} );
	
	            $scope.markersArray.push( myMarker );
	            $scope.map.addShape(myMarker);
	            $scope.map.setCenter({lat:$routeParams.latitude,lng:$routeParams.longitude});
			}
        });
        
        

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
                        $scope.infos = eval('({"info":[{"id":'+status+',"name":"'+$scope.locations.location.length+' location(s) found"}]})');
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
                $scope.map.removeShape($scope.markersArray[i]);
            }
            $scope.markersArray = [];
            $scope.currentLocation = obj;
            var myMarker = new MQA.Poi( {lat:obj.latitude, lng:obj.longitude} );

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

            myMarker.setRolloverContent(obj.name);
            myMarker.setInfoContentHTML(contentString);
            $scope.map.addShape(myMarker);
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