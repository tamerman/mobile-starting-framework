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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="bus.label.map" var="mapLabel"/>
<spring:message code="bus.label.stops" var="stopsLabel"/>

<kme:page title="${stop.name}" id="busstopdetail" backButton="true" homeButton="true" cssFilename="bus">
    <kme:content>
        <kme:listView id="busarrivals" dataTheme="c" dataDividerTheme="b" filter="false">
            <script id="arrivalTemplate" type="text/x=jquery-templ">
				<li data-theme="c" data-icon="false"><a href="${pageContext.request.contextPath}/bus/viewBusTracking?routeId=\${routeId}&stopId=\${stopId}">Find \${stopName} stop on map</a></li>
                {{each scheduledStop}}
				{{if bus.color}}
                <li data-theme="c" data-icon="false"><a href="${pageContext.request.contextPath}/bus/viewBusTracking?routeId=\${bus.routeId}"><div style="white-space:nowrap;"><p class="arrivealP">\${timeToArrival}  min</p><div class="busDiv"><img class="circleImg" src="${pageContext.request.contextPath}/images/service-icons/BusDirectional_30px_circle.png"></img><div class="colorDiv" style="background:#\${bus.color}"></div><img class="busImg" src="${pageContext.request.contextPath}/images/service-icons/BusDirectional_30px_bus.png"></img></div>  \${bus.routeName}</div>
				</a></li>
				{{/if}}
                {{/each}}
            </script>
        </kme:listView>
	</kme:content>
    <%@ include file="footer.jsp" %>
    	<script type="text/javascript">
    	    var routeId = '${routeId}';
    	    var stopId = '${stop.id}';
    	    var stopName = '${stop.name}';
            $(document).ready( function() {
                $('[data-role=page][id=busstopdetail]').live('pagebeforeshow', $.fn.loadSchedule() );
            });
            $.fn.loadSchedule = function() {
                $('#arrivalTemplate').template('arrivalTemplate');
                refreshTemplate('${pageContext.request.contextPath}/services/bus/stop/schedule/${campus}?routeId=${routeid}&stopId=${stop.id}&_type=json',
                '#busarrivals',
                'arrivalTemplate',
                '<li>No scheduled buses were found</li>',
                function() {
                    $('#busarrivals').listview('refresh');
                    //setTimeout( function() {$.fn.loadSchedule();}, 15000 );
                });
            };
            
            function LOG(msg) {
                //                setTimeout(function() {
                //                    throw new Error(msg);
                //                }, 0);
            }
        </script>
</kme:page>