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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="maps.title" var="msgCat_ToolTitle"/>
<spring:message code="maps.search" var="msgCat_Search"/>
<spring:message code="maps.searchErrorMessage" var="searchErrorMessage"/>
<c:set var="localeCode" value="${pageContext.response.locale}" />

<kme:page title="${msgCat_ToolTitle}" id="maps" backButton="true" homeButton="true" cssFilename="mapsHome" jsFilename="maps" usesGoogleMaps="true" mapLocale="${localeCode}">
	<kme:content>
		<script src="${pageContext.request.contextPath}/js/arcgislink.js" type="text/javascript"></script>
		<form:form action="${pageContext.request.contextPath}/maps/building/search" commandName="mapsearchform" data-ajax="false">
			<fieldset>
            <label for="searchText" style="position:absolute; left:-9999px;">${msgCat_Search}:</label>
            <form:input path="searchText" type="search" cssClass="text ui-widget-content ui-corner-all" placeholder="${msgCat_Search}" />
			<form:errors path="searchText" />
			</fieldset>
			<input type="hidden" id="campus" name="campus" value="${campus}"/>
		</form:form>
		<div id="searchresults" class="overlay"></div>
		<div id="map_canvas" class="map"></div>

<script type="text/javascript">
var map;
var markersArray = [];
var userMarkersArray = [];

$(window).resize(function(){resizeMap();});
$(window).load(function(){resizeMap();});

$('#maps').live("pagebeforeshow", function() {
	setContextPath("${pageContext.request.contextPath}");
	deleteOverlays(markersArray);
	map = initialize("map_canvas", ${initialLatitude}, ${initialLongitude}, "${arcGisUrl}");
	<c:if test="${useCampusBounds == true}">
	var campus = $("#campus").val();
	// Bounds will be for the state of Indiana for anything other than the valid values.
	var bounds = getCampusBounds(campus);
	map.fitBounds(bounds);
	</c:if>
});

$('#maps').live("pageshow", function() {
	// If the browser keeps the search parameters, search on page load
	if( $("#searchText").val() != "" ) {
		$.fn.mapSearch(null);
	}
});

$('#mapsearchform').submit(function(e) {
	$.fn.mapSearch(e);
});

$('#mapsearchform').keyup(function(e) {
	if( e.keyCode != 13 ) {
	$('#searchresults').empty();
	}
});

$.fn.mapSearch = function(e) {
	var searchText = $("#searchText").val();
	var groupCode = $("#searchCampus").val();
	if( e != null ) {
		e.stopPropagation();
		e.preventDefault();
	}
	$.mobile.showPageLoadingMsg();
	$.ajax({
		type:'Get',
		url:'${pageContext.request.contextPath}/maps/building/searchassist?criteria=' + encodeURI(searchText) + '&groupCode=' + encodeURI(groupCode),
		dataType:'html',
		success:function(data) {
			$.mobile.hidePageLoadingMsg();
			$('#searchresults').empty();
			var pagehtml = '<div id="resultdata"></div>'
			$('#searchresults').html(pagehtml);
			$("#resultdata").html(data).page();
//			$('#searchresults').trigger('updatelayout');
			$.fn.mapSearchPostProcess();
			$('#searchresults').show();
//			$('#searchresults').trigger('create');
		},
		error:function(data) {
			$.mobile.hidePageLoadingMsg();
			var errorHtml = "";
			errorHtml += '<ul data-role="listview" data-theme="c" data-inset="false" data-filter="false">';
			errorHtml += '<li data-theme="c">';
			errorHtml += '${searchErrorMessage}';
			errorHtml += '</li>';
			errorHtml += '</ul>';
			$('#searchresults').html(errorHtml);
			$('#searchresults').show();
			return false;
		}
	});
}

$.fn.mapSearchPostProcess = function() {
    $('a[kmetype="quicksearch"]').click(function(event) {
		event.preventDefault();
		$('#searchresults').hide();
		var latitude = $(this).attr("kmelatitude");
		var longitude = $(this).attr("kmelongitude");
		var name = $(this).attr("kmename");
		var info = $(this).attr("kmeinfo");
    	$('#searchText').val(name);
		deleteOverlays(markersArray);
		showLocationByCoordinates(map, markersArray, latitude, longitude, info);
		//alert("Test");
    });
}
</script>

	</kme:content>
</kme:page>
