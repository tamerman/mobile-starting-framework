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