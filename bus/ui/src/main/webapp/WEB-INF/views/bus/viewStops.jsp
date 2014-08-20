<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="bus.label.map" var="mapLabel"/>
<spring:message code="bus.label.stops" var="stopsLabel"/>

<kme:page title="Stops" id="bus-webapp" backButton="true" homeButton="true" cssFilename="bus">
	<kme:content>
		<script type="text/javascript" src="https://maps.google.com/maps/api/js?libraries=geometry&v=3&sensor=true"></script>
		<kme:listView id="stops" dataTheme="c" dataDividerTheme="b" filter="false">
		</kme:listView>
		<script type="text/javascript">
			var contextPath = "${pageContext.request.contextPath}";
			var routes = ${routes};
			var stops = [];
			var sortedStops = [];
			var updateLocation;
			var stopsTimeout;
			var geoOptions = {
					enableHighAccuracy: true,
					timeout: 5000,
					maximumAge: 0
			};
			$(window).load(function () {
				$.mobile.showPageLoadingMsg();
				getUserLocation();
			});
			
			function buildStops() {
				$.each(routes, function (index, route) {
					$.each(route.stops, function (stopIndex, stop) {
						var newStop = {
								id: stop.id,
								name: stop.name,
								routeName: route.name,
								routeId: route.id
						};
						stops.push(newStop);
					});
				});
				stopsByName();
			}
			
			function getUserLocation() {
				if(navigator.geolocation) {
					function success(position) {
						stopsByDistance(position);
					};
					function error() {
						buildStops();
					};
					updateLocation = navigator.geolocation.getCurrentPosition(success, error, geoOptions);
				} else {
					alert("Your device does not support location services.");
					buildStops();
				}
			}
			
			function stopsByDistance(userPosition) {
				var lat = userPosition.coords.latitude;
		        var lon = userPosition.coords.longitude;
		        //radius in km
		        var radius = 5000;

		        // Non-CXF
//		        var stopsDistanceUrl = contextPath + '/bus/routesByDistance?latitude=' + lat + '&longitude='+ lon + '&radius=' + radius;
				// CXF Service url. 
				var stopsDistanceUrl = contextPath + '/services/buses/routes/byDistance?latitude=' + lat + '&longitude='+ lon + '&radius=' + radius;

				$.ajax({
					url: stopsDistanceUrl,
					dataType: 'json',
					success: sortStops,
					error: badFile,
					cache: false
				});
				/*
				var destinationlst = [];
				var dividedDestinationlst = [];
				var location = new google.maps.LatLng(userPosition.coords.latitude, userPosition.coords.longitude);
				var origin = [location];
				$.each(stops, function (index, stop) {
					destinationlst.push(stop.location);
				});
				//divides up the list because getDistanceMatrix can only take a total of 25 origins and/or destinations
				while (destinationlst.length > 0) {
					dividedDestinationlst.push(destinationlst.splice(0,20));
				}
				getBusStopDistances(origin, dividedDestinationlst, 0);
				*/
			}
			
			function sortStops(data) {
				$.each(data, function(index, route) {
					$.each(route.stops, function(i, stop) {
						var distance;
						/*
						if (parseFloat(stop.distance) > 1.40818 && parseFloat(stop.distance) <= 1.81051){ //over a mile
							distance = "1 mile";
						} else if (parseFloat(stop.distance) > 1.00584 && parseFloat(stop.distance) <= 1.40818) { //1/4th a mile
							distance = "3/4 of a mile";
						} else if (parseFloat(stop.distance) > 0.603504 && parseFloat(stop.distance) <= 1.00584) { //1/2 a mile
							distance = "1/2 of a mile";
						} else if (parseFloat(stop.distance) > 0.201168 && parseFloat(stop.distance) <= 0.603504) { //1/4th a mile
							distance = "1/4 of a mile";
						} else if (parseFloat(stop.distance) <= 0.201168)  { //distance in feet
							distance = (Math.round((parseInt(parseFloat(stop.distance) * 3280.8 * 10)/10)/5) * 5) + " feet";
						} else {
							distance = Math.round(stop.distance * 0.62137) + " miles";
						}
						*/
						if (parseFloat(stop.distance) > 1407 && parseFloat(stop.distance) <= 1809){ //over a mile
							distance = "1 mile";
						} else if (parseFloat(stop.distance) > 1007 && parseFloat(stop.distance) <= 1407) { //1/4th a mile
							distance = "3/4 of a mile";
						} else if (parseFloat(stop.distance) > 605 && parseFloat(stop.distance) <= 1007) { //1/2 a mile
							distance = "1/2 of a mile";
						} else if (parseFloat(stop.distance) > 202 && parseFloat(stop.distance) <= 605) { //1/4th a mile
							distance = "1/4 of a mile";
						} else if (parseFloat(stop.distance) <= 202)  { //distance in feet
							distance = (Math.round((parseInt(parseFloat(stop.distance) * 3.2808 * 10)/10)/5) * 5) + " feet";
						} else {
							distance = Math.round(stop.distance * 0.00062137) + " miles";
						}
						var newStop = {
								id: stop.id,
								distance: stop.distance,
								distanceText: distance,
								name: stop.name,
								routeName: route.name,
								routeId: route.id
						};
						sortedStops.push(newStop);
					});
				});
				sortedStops.sort(function(a, b) {return a.distance - b.distance;});
				displayStops();
			}
			
			function badFile(data) {
				buildStops();
			}
			
			function getBusStopDistances(origin, dividedDestinationlst, index) {
				if (index < dividedDestinationlst.length) {
					var service = new google.maps.DistanceMatrixService();
					service.getDistanceMatrix({
						origins: origin,
						destinations: dividedDestinationlst[index],
						travelMode: google.maps.TravelMode.WALKING,
						unitSystem: google.maps.UnitSystem.IMPERIAL,
						avoidHighways: false,
						avoidTolls: false
					}, function (response, status) {callback(response, status, origin, dividedDestinationlst, index);});
				} else {
					sortedStops.sort(function(a, b) {return a.distanceValue - b.distanceValue;});
					displayStops();
				}
			}
			
			function callback(response, status, origin, dividedDestinationlst, shrunkListIndex) {
				if (status != google.maps.DistanceMatrixStatus.OK) {
					if (status == "OVER_QUERY_LIMIT") {
						setTimeout(function() {getBusStopDistances(origin, dividedDestinationlst, index);},500);
					} else {
						alert('Error was: ' + status);			
					}
				} else {
					var results = response.rows[0].elements;
					for (var i = 0; i < results.length; i++) {
						if (results[i].status == google.maps.DistanceMatrixStatus.OK) {
							var distance;
							var disValue = results[i].distance.value;
							//measured within 400 meters for every quarter mile within 1 mile
							//measured in feet if under 202 meters
							if (disValue > 1407 && disValue <= 1809){ //over a mile
								distance = "1 mile";
							} else if (disValue > 1007 && disValue <= 1407) { //1/4th a mile
								distance = "3/4 of a mile";
							} else if (disValue > 605 && disValue <= 1007) { //1/2 a mile
								distance = "1/2 of a mile";
							} else if (disValue > 202 && disValue <= 605) { //1/4th a mile
								distance = "1/4 of a mile";
							} else if (disValue <= 202)  { //distance in feet
								distance = (Math.round((parseInt(disValue * 3.2 * 10)/10)/5) * 5) + " feet";
							} else {
								distance = results[i].distance.text;
							}
							var distanceInfo = {
								stop: stops[shrunkListIndex * 20 + i],
								distanceDuration: results[i].duration.text,
								distanceValue: disValue,
								distanceText: distance
							};
							sortedStops.push(distanceInfo);
						}
					}
					var index = shrunkListIndex + 1;
					setTimeout(function() {getBusStopDistances(origin, dividedDestinationlst, index);},500);
				}
			}
			
			function displayStops() {
				if (sortedStops.length > 0 && sortedStops[0].distance > 16090) {
					buildStops();
				} else if (sortedStops.length == 0) {
					$.mobile.hidePageLoadingMsg();
					toast("No routes running at this time.");
				} else {
					$.mobile.hidePageLoadingMsg();
					var htmlStops = "";
					$.each(sortedStops, function (index, stop) {
						htmlStops += '<li class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-first-child ui-btn-up-c" data-icon="arrow-r" data-id="" style="padding-right:0.7em"><div class="ui-btn-inner ui-li"><div class="ui-btn-text"><a class="ui-link-inherit" href="${pageContext.request.contextPath}/bus/viewStop?routeId=' + stop.routeId + '&stopId=' + stop.id + '&campus=ALL"><h3 class="stopName">' + stop.name + '</h3><div class="stopDistance">' + stop.distanceText + '</div><p class="wrap ui-li-desc">' + stop.routeName + '</p></a></div><span class="ui-icon ui-icon-arrow-r ui-icon-shadow"></span></div></li>';
					});
					$('#stops').html(htmlStops);
				}
			}
			
			function stopsByName() {
				if (stops.length > 0) {
					$.mobile.hidePageLoadingMsg();
					stops.sort(function(a, b) {
						var nameA=a.name.toLowerCase();
						var nameB=b.name.toLowerCase();
						if (nameA < nameB) {
							return -1;
						} else if (nameA > nameB) {
						 	return 1;
						} else {
							return 0;
						}
					});
					clearTimeout(stopsTimeout);
					var htmlStops = "";
					$.each(stops, function (index, stop) {
						htmlStops += '<li class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-first-child ui-btn-up-c" data-icon="arrow-r" data-id="" style="padding-right:0.7em"><div class="ui-btn-inner ui-li"><div class="ui-btn-text"><a class="ui-link-inherit" href="${pageContext.request.contextPath}/bus/viewStop?routeId=' + stop.routeId + '&stopId=' + stop.id + '&campus=ALL"><h3 style="margin-top:0;">' + stop.name + '</h3><p class="wrap ui-li-desc">' + stop.routeName + '</p></a></div><span class="ui-icon ui-icon-arrow-r ui-icon-shadow"></span></div></li>';
					});
					$('#stops').html(htmlStops);
				} else {
					$.mobile.hidePageLoadingMsg();
					toast("No routes running at this time.");
				}
			}
			
			// brings up a popup message that disappears after 1.5 seconds
			var toast = function(msg) {
				$("<div class='ui-loader ui-overlay-shadow ui-body-a ui-corner-all'><h3>"+msg+"</h3></div>")
				.css({ display: "block",
					opacity: 1.0,
					position: "fixed",
					padding: "7px",
					"text-align": "center",
					width: "270px",
					left: ($(window).width() - 284)/2,
					top: $(window).height()/2 })
				.appendTo( $.mobile.pageContainer ).delay( 1500 )
				.fadeOut( 400, function(){
					$(this).remove();
				});
			};
		</script>
	</kme:content>
	<%@ include file="footer.jsp" %>
</kme:page>