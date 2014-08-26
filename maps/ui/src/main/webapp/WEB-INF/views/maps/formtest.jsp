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
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="shared.campus" var="msgCat_Campus"/>
<spring:message code="maps.search" var="msgCat_Search"/>
<spring:message code="maps.location" var="msgCat_Location"/>
<spring:message code="maps.latitude" var="msgCat_Latitude"/>
<spring:message code="maps.longitude" var="msgCat_Longitude"/>
<spring:message code="maps.noBuildingCode" var="msgCat_NoBuildingCode"/>
<spring:message code="maps.title" var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="formtest" cssFilename="location" jsFilename="maps" usesGoogleMaps="true">
	<kme:content>

<style type="text/css">
<!--
div.overflow {
height: 200px;
width: 300px;
overflow: auto;
border: 1px solid #666;
padding: 8px;
}
-->
</style> 

<form:form action="${pageContext.request.contextPath}/maps/search" commandName="mapsearchform" data-ajax="false">
	<fieldset>
	<label for="searchCampus">${msgCat_Campus}</label>
	<form:select path="searchCampus" multiple="false">
		<form:option value="" label="" data-placeholder="true" />
		<form:option value="BL" label="Bloomington" data-placeholder="true"/>
		<form:option value="IN" label="Indianapolis" data-placeholder="true"/>
	</form:select>
	<label for="searchText">${msgCat_Search}</label>
	<form:input path="searchText" cssClass="text ui-widget-content ui-corner-all" />
	<form:errors path="searchText" />
	<div id="searchresults" class="overflow"></div>
	</fieldset>
	<fieldset>
	<label for="locationName">${msgCat_Location}</label>
	<form:input path="locationName" cssClass="text ui-widget-content ui-corner-all" />
	</fieldset>
	<fieldset>
	<form:hidden path="venueId"/>
	<div id="venues" class="overflow"></div>
	</fieldset>
	<fieldset>
	<label for="searchLatitude">${msgCat_Latitude}</label>
	<form:input path="searchLatitude" cssClass="text ui-widget-content ui-corner-all" />
	<label for="searchLongitude">${msgCat_Longitude}</label>
	<form:input path="searchLongitude" cssClass="text ui-widget-content ui-corner-all" />
	<%--
	<label for="searchBuilding">Building Code</label> 
	<form:input path="searchBuilding" cssClass="text ui-widget-content ui-corner-all" />
	--%>
<%-- 	<label for="searchBuilding">Building Code</label>
	<form:select path="searchBuilding" multiple="false">
		<form:option value="" label="" data-placeholder="true"/>
	</form:select> --%>
	</fieldset>
</form:form>

<div id="map_canvas" style="height:300px;"></div>
	
<script type="text/javascript">
/* Maps */

var map;
var markersArray = [];
var userMarkersArray = [];
var edit = true;

$('#formtest').live("pagebeforeshow", function() {
	setContextPath("${pageContext.request.contextPath}");
	deleteOverlays(markersArray);
	map = initialize("map_canvas", 39.17, -86.5);
//		var buildingCode = getParameterByName("id");
//		showBuildingByCode(map, buildingCode);
//		drawUserLocation(map);
/* 		$("#searchCampus").change(function() {
			deleteOverlays(markersArray);
			var groupCode = $("#searchCampus").val();
			if (groupCode) {
				var buildingData = retrieveBuildingsForGroup(groupCode);
				var bounds = getCampusBounds(groupCode);
				if (bounds) {
					map.fitBounds(bounds);	
				}
			} else {
				alert("No group code");
			}
		}); */
	$("#searchBuilding").change(function() {
		deleteOverlays(markersArray);
		var buildingCode = $("#searchBuilding").val();
		//alert("Test");
		if (buildingCode) {
			retrieveBuildingByCode(buildingCode);	
		} else {
			alert("${msgCat_NoBuildingCode}");
		}
	});
    google.maps.event.addListener(map, 'click', function(event) {
    	//$('#searchCampus').val("");
    	//$('#searchCampus').selectmenu('refresh', true);
    	$('#searchBuilding').val("");
    	$('#searchBuilding').selectmenu('refresh', true);
    	$('#searchLatitude').val(event.latLng.lat());
    	$('#searchLongitude').val(event.latLng.lng());
    	deleteOverlays(markersArray);
    	addMarker(map, markersArray, event.latLng);
    	findFoursquareVenues(event.latLng);
    	//alert(event.latLng);
    });
    initializeMapSearch();
});

$('#formtest').live("pageshow", function() {
	deleteOverlays(markersArray);
	// If the browser keeps the search parameters, search on page load
	var lat = $('#searchLatitude').val();
	var lng = $('#searchLongitude').val();
	var buildingCode = $('#searchBuilding').val();
	if (buildingCode) {
		showBuildingByCode(buildingCode);
	} else if ((lat != "" && lng != "" && lat != 0 && lng != 0)) {
		showLocationByCoordinates(map, markersArray, lat, lng);
	} else {
		
	}
	initializeMapSearchDefault();
});

/* function retrieveBuildingByCode(buildingCode) {
	//clearOverlays(markersArray);
	$.getJSON('${pageContext.request.contextPath}/maps/building/' + buildingCode, function(data) {
		showLocationByCoordinates(map, markersArray, data.latitude, data.longitude);
	}).error(function() { 
		alert("Could not retrieve data at this time."); 
	});
} */





function setCampusMapCenter(map, campus) {
	var latlng = new google.maps.LatLng(39.794187,-86.178589);
	var zoom = 6;
	if (campus == "BL") {
		latlng = new google.maps.LatLng(39.168486,-86.523455);
		zoom = 15;
	} else if (campus == "CO") {
		latlng = new google.maps.LatLng(39.251214,-85.901606);
		zoom = 16;
	} else if (campus == "EA") {
		latlng = new google.maps.LatLng(39.868306,-84.880856);
		zoom = 17;
	} else if (campus == "FW") {
		latlng = new google.maps.LatLng(41.117480,-85.108680);
		zoom = 15;
	} else if (campus == "IN") {
		latlng = new google.maps.LatLng(39.773186,-86.175041);
		zoom = 15;
	} else if (campus == "KO") {
		latlng = new google.maps.LatLng(40.459160,-86.132769);
		zoom = 15;
	} else if (campus == "NW") {
		latlng = new google.maps.LatLng(41.556685,-87.338390);
		zoom = 17;
	} else if (campus == "SB") {
		latlng = new google.maps.LatLng(41.663855,-86.219276);
		zoom = 15;
	} else if (campus == "SE") {
		latlng = new google.maps.LatLng(38.344542,-85.819562);
		zoom = 15;
	} 
	map.setCenter(latlng);
	map.setZoom(zoom);
	//findFoursquareVenues(latlng);
}

/*
 * From maps home
 */

function initializeMapSearch() {
	$('#searchText').keypress(function (event) {
		// Prevent enter key from submitting the form
		lastTypedKeyCode = event.keyCode;
		//console.log(lastTypedKeyCode);
		if (lastTypedKeyCode == 13) {
			event.preventDefault();
			return false;
		} else {
			if (timeout) { 
				clearTimeout(timeout);
			}
			timeout = setTimeout(function(){
				mapSearch();
			}, 200);
		}
	});
	$("#searchCampus").change(function() {
		mapSearch();
	});
}

function initializeMapSearchDefault() {
	// If the browser keeps the search parameters, search on page load
	mapSearch();	
}

var timeout;
var previousSearchKey;
var mapsRemoteCallCount = 0;
var mapsCurrentDisplayNumber = 0;

function mapSearch() {
	var inputString = $("#searchText").val();
	var groupCode = $("#searchCampus").val();
	mapsRemoteCallCount++;
	var mapsRemoteCallCountAtStartOfRequest = mapsRemoteCallCount;	
	var searchKey = inputString + ":" + groupCode;

	if (searchKey != previousSearchKey) {
		if (inputString.length < 2 || groupCode == "UA" || groupCode == "") {
			// Hide the suggestion box.
			$('#searchresults').html('');
		} else {
			var requestUrlString = '${pageContext.request.contextPath}/maps/building/searchassist?criteria=' + encodeURI(inputString) + '&groupCode=' + encodeURI(groupCode);
			$.get(requestUrlString, function(data) {
				//console.log("" + requestUrlString + " " + mapsRemoteCallCount + " " + mapsCurrentDisplayNumber);
				if (mapsRemoteCallCountAtStartOfRequest >= mapsCurrentDisplayNumber) {
					mapsCurrentDisplayNumber = mapsRemoteCallCount;
					// Show results
					var pagehtml = '<div id="resultdata"></div>'
					$('#searchresults').html(pagehtml);
					$("#resultdata").html(data).page();
					mapSearchPostProcess();
				}
			});
		}
	}
	previousSearchKey = searchKey;
} // mapSearch

function mapSearchPostProcess() {
    $('a[kmetype="quicksearch"]').click(function(event) {
		event.preventDefault();
		var latitude = $(this).attr("kmelatitude");
		var longitude = $(this).attr("kmelongitude");
		var name = $(this).attr("kmename");
//    	$('#searchLatitude').val(latitude);
//    	$('#searchLongitude').val(longitude);
    	$('#locationName').val(name);
		deleteOverlays(markersArray);
		showLocationByCoordinates(map, markersArray, latitude, longitude);		
		//alert("Test");
    });
}



</script>
	</kme:content>
</kme:page>
