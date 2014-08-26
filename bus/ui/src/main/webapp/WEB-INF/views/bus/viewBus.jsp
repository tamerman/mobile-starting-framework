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

<spring:message code="bus.label.map" var="mapLabel"/>
<spring:message code="bus.label.stops" var="stopsLabel"/>

<kme:page title="Bus ${busId}" id="busDetails" backButton="true" homeButton="true" cssFilename="bus">
	<kme:content>
		<kme:listView id="stops" dataTheme="c" dataDividerTheme="b" filter="false">
		
		</kme:listView>
		<script type="text/javascript">
			var route = ${route};
			var busId = ${busId};
			var stops = [];
			function buildStops() {
				$.each(route.stops, function (stopIndex, stop) {
					$.each(stop.schedule, function (scheduleIndex, schedule){
						if (schedule.bus.id == busId) {
							var stopInfo = {
									id: stop.id,
									stopName: stop.name,
									arrivalTime: schedule.timeToArrival
							}
							stops.push(stopInfo);
						}
					});
				});
				stops.sort(function(a, b) {return a.arrivalTime - b.arrivalTime;});
				var htmlStops = "";
				$.each(stops, function (index, stop) {
					htmlStops += '<li class="ui-btn ui-li ui-btn-up-c" data-id="" style="padding:0 0.7em 0 0.7em;"><div class="ui-btn-inner ui-li"><div class="ui-btn-text"><a class="ui-link-inherit" href="${pageContext.request.contextPath}/bus/viewBusTracking?routeId=' + ${route.id} + '&stopId=' + stop.id + '\"><div class="ui-btn-text"><div style="float:left;"><h3 class="stopName">' + stop.stopName + '</h3></div><div class="stopDistance">' + (stop.arrivalTime > 0 ? stop.arrivalTime + " mins" : "arriving") + '</div></div></a></div></div></li>';
				});
				$('#stops').html(htmlStops);
			}
			$(window).load(function () {
				buildStops();
			});
		</script>
	</kme:content>
	<%@ include file="footer.jsp" %>
</kme:page>