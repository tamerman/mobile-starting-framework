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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<spring:message code="maps.title" var="msgCat_ToolTitle"/>
<c:set var="localeCode" value="${pageContext.response.locale}" />


<kme:page title="${msgCat_ToolTitle}" id="mapslocation" backButton="true" homeButton="true" cssFilename="location" jsFilename="maps" usesGoogleMaps="true" mapLocale="${localeCode}">
	<kme:content>
	
<div id="map_canvas"></div>
	
<script type="text/javascript">
/* Maps */

var markersArray = [];
var userMarkersArray = [];

$(window).resize(function(){resizeMap();});
$(window).load(function(){resizeMap();});

$('#mapslocation').live("pageshow", function() {
	setContextPath("${pageContext.request.contextPath}");
/* 	$('#map_canvas').gmap({'center': getLatLng(), 'callback': function() {
	
	}); */
	//var buildingCode = $('#map_canvas').jqmData('code');
	var map = initialize("map_canvas", 39.17, -86.5);
	deleteOverlays(markersArray);
	resizeMap();
	var buildingCode = getParameterByName("id");
	if (buildingCode) {
		showBuildingByCode(map, buildingCode);	
	} else {
		var latitude = getParameterByName("latitude");
		var longitude = getParameterByName("longitude");
		if (latitude && longitude) {
			showLocationByCoordinates(map, markersArray, latitude, longitude);	
		}
	}
	//drawUserLocation(map, markersArray, userMarkersArray);
	
	//alert(buildingCode);

});



</script>
	</kme:content>
</kme:page>
