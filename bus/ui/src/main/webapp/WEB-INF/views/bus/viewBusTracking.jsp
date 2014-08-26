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

<kme:page title="Bus Tracking" id="bus" backButton="true" homeButton="true" jsFilename="bus" cssFilename="busTracking" usesGoogleMaps="false">
	<kme:content>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<script type="text/javascript" src="https://maps.google.com/maps/api/js?libraries=geometry&v=3&sensor=true"></script>
		<script type="text/javascript" src="https://google-maps-utility-library-v3.googlecode.com/svn/trunk/styledmarker/src/StyledMarker.js"></script>
		<script type="text/javascript" src="https://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerwithlabel/src/markerwithlabel.js"></script>

		<script src="${pageContext.request.contextPath}/js/arcgislink.js" type="text/javascript"></script>
		<script type="text/javascript">
			var sessionTime = '${currentTime}';
			var campus = '${campus}';
			var routeId = '${routeId}';
			var stopId = '${stopId}';
			
			$(window).load(function () {
				if (window.sessionStorage && window.sessionStorage.getItem('storedBusTime') == sessionTime) {
					if (window.sessionStorage.getItem('busRouteId')) {
						routeId = window.sessionStorage.busRouteId;
					}
					if (window.sessionStorage.getItem('busStopId')) {
						stopId = window.sessionStorage.busStopId;
					} else {
						stop = '';
					}
				} else {
					window.sessionStorage.storedBusTime = sessionTime;
					window.sessionStorage.busRouteId = routeId;
					window.sessionStorage.busStopId = stopId;
				}
				
				setContextPath("${pageContext.request.contextPath}");
				var map = initialize("map_canvas", ${initialLatitude}, ${initialLongitude}, campus);
				
				// Non CXF version. 
//				var routeFeedUrl = "${pageContext.request.contextPath}" + '/bus/busRoutes';

				// CXF Version. 
				var routeFeedUrl = "${pageContext.request.contextPath}" + '/services/bus/route/lookup/' +campus;
				$.ajax({
					url: routeFeedUrl,
					dataType: 'json',
					success: buildRoutes,
					error: function(jqXHR, textStatus, errorThrown) {badFile(errorThrown, "Routes");},
					cache: false
				});
				
				$("a.next").click(function(){
		            var $toHighlight = $('.active').next().length > 0 ? $('.active').next() : $('#alertMessageDiv div.alert').first();
		            $('.active').addClass('invisible');
		            $('.active').removeClass('active');
		            $toHighlight.addClass('active');
		            $toHighlight.removeClass('invisible');
		        });

		        $("a.prev").click(function(){
		            var $toHighlight = $('.active').prev().length > 0 ? $('.active').prev() : $('#alertMessageDiv div.alert').last();
		            $('.active').addClass('invisible');
		            $('.active').removeClass('active');
		            $toHighlight.addClass('active');
		            $toHighlight.removeClass('invisible');
		        });
				
				resizeMap();
	  		});
			
			function buildRoutes(data) {
				if (data != null && data.busRoute.length > 0) {
					if (routeId == '') {
						routeId = data.busRoute[0].id;
					}
					setCurRouteId(routeId);
					var select = document.getElementById('select-route');
					$.each(data.busRoute, function (key, route) {
						var opt = document.createElement('option');
						opt.value = route.id;
						opt.innerHTML = route.name;
						select.appendChild(opt);
					});
					configureBusRoutes(data.busRoute);
					fetchBusInformation();
					if (stopId != '') {
						showStop(routeId, stopId);
					}
					dropDownColors(data.busRoute);
		    		changeDropDownColor();
		    		$('select#select-route').val(routeId);
					$("#select-route option[value='ALL']").remove();
					$('select#select-route').selectmenu('refresh');
				} else {
					$('select#select-route').remove();
					resizeMap();
					toast("No routes running at this time.");
				}
				
			}
			
			function dropDownColors(data) {
				var routes = data;
				$.each(routes, function (key, route) {
					var routeColor = "#" + route.color;
					$("#select-route option[value='" + route.id + "']").css('color', 'white');
					$("#select-route option[value='" + route.id + "']").css('backgroundColor', routeColor);
				});
			}
			$(window).resize(function(){resizeMap();});
		</script>
		<select data-mini="true" name="select-route" id="select-route">
			<option value="ALL" style="background-color:black; color:white;">Building routes</option>
		</select>
		<!-- 
		<c:if test="${not empty routes}">
		    <select data-mini="true" name="select-route" id="select-route2">
		    	
		    	<option value="ALL" style="background-color:black; color:white;">All Routes</option>
			     
				<c:forEach items="${routes}" var="route">
				  	<option value="${route.id}"><p style="font-family:color:red;">${route.name}</p></option>
				</c:forEach>
		    </select>
	    </c:if>
	    -->
	    <div id="tooltip" data-role="popup" dataTheme="c">
	    	<div id="prevnext">
		    	<a href="#" class="prev">Prev</a>
		    	<a href="#" class="next">Next</a>
	    	</div>
	    	<div id="alertMessageDiv"></div>
		</div>
	    <div id="map_canvas" style="width:100%; height:100%"></div>
    	<div id="mapFooter" class="ui-navbar ui-mini" data-mini="true" data-role="navbar" role="navigation">
		    <ul class="ui-grid-a">
		    	<li class="ui-block-a">
		    		<a class="ui-btn ui-btn-inline ui-btn-active ui-btn-up-a" data-transition="none" href="${pageContext.request.contextPath}/bus/viewBusTracking" data-corners="false" data-shadow="false" data-iconshadow="true" data-wrapperels="span" data-theme="a" data-inline="true">Map</a>
		    	</li>
		    	<li class="ui-block-b">
			    	<a class="ui-btn ui-btn-inline ui-btn-active ui-btn-up-a" data-transition="none" href="${pageContext.request.contextPath}/bus/viewStops" data-corners="false" data-shadow="false" data-iconshadow="true" data-wrapperels="span" data-theme="a" data-inline="true">Stops</a>
		    	</li>
		    </ul>
	    </div>
    </kme:content>
</kme:page>