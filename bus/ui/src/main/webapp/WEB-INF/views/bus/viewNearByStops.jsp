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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:message code="bus.title.nearbystops" var="nearbystopsTitle"/>
<spring:message code="bus.label.map" var="mapLabel"/>
<spring:message code="bus.label.routes" var="routesLabel"/>
<spring:message code="bus.label.favorites" var="favoritesLabel"/>
<spring:message code="bus.label.nearbystops" var="nearbystopsLabel"/>

<kme:page title="${nearbystopsTitle}" id="buslocation" backButton="true" homeButton="true" cssFilename="bus">
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script language="javascript">
        $.extend({
            getUrlVars: function(){
                var vars = [], hash;
                var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
                for(var i = 0; i < hashes.length; i++)
                {
                    hash = hashes[i].split('=');
                    vars.push(hash[0]);
                    vars[hash[0]] = hash[1];
                }
                return vars;
            },
            getUrlVar: function(name){
                return $.getUrlVars()[name];
            }
        });
    </script>
    <script>
        var lat=0;
        var lon=0;
        //radius in km
        var radius=0.5;
        var geoloaded=0;
        var load=0;
        function geoLocationfunction()
        {
            if(navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    // success
                    console.log('<p>Latitude: ' + position.coords.latitude + '<br/>Longitude: ' + position.coords.longitude + '</p>');
                    lat=position.coords.latitude;
                    lon=position.coords.longitude
                    //test params
                    //var lat= 42.26439987447436;
                    //var lon=-83.74433755874634;
                    var url = '${pageContext.request.contextPath}/bus/viewNearByStops?latitude=' + lat + '&longitude='+ lon + '&radius=' + radius;
                    window.location.replace(url);
                }, function(position_error) {
                    console.log('<p>An error occured while determining your location. Details are: <br/>' + position_error.message + '</p>');
                }, {
                    // options
                    enableHighAccuracy: true
                });
            } else {
                console.log("<p>The W3C Geolocation API isn't availble.</p>");
            }

        }//geo

        window.onload=function()
        {
            if( $.getUrlVar('longitude') == null ) {
                geoLocationfunction();
            }
        }
    </script>
    <kme:content>
        <kme:listView id="nearbystopList" filter="false" dataTheme="c" dataInset="false">
            <c:choose>
                <c:when test="${not empty stopsnear}">
                    <c:forEach items="${stopsnear}" var="stopnear">
                        <kme:listItem>
                            <c:url var="url" value="/bus/viewStop">
                                <c:param name="stopId" value="${stopnear.id}"></c:param>
                                <c:param name="campus" value="${campus}"></c:param>
                            </c:url>
                            <c:choose>
                            <c:when test='${stopnear.distance <= 0.1524}'>
                              <fmt:formatNumber value="${stopnear.distance * 3.28 * 1000}" pattern="0.00" var="formatdistance" />
                              <c:set var="unit" value="ft"/>
                           </c:when>
                           <c:otherwise>
                               <fmt:formatNumber value="${stopnear.distance * 0.621371192}" pattern="0.00" var="formatdistance" />
                               <c:set var="unit" value="mi"/>
                           </c:otherwise>
                           </c:choose>
                            <a href="${url}">
                                <c:out value="${formatdistance}"/>
                                <c:out value="${unit}"/>  --
                                <c:out value="${stopnear.name}" />
                            </a>
                        </kme:listItem>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <kme:listItem>
                        <h3 class="ui-li-heading wrap">No nearby stops have been found within 500 meters of your current location.</h3>
                    </kme:listItem>
                </c:otherwise>
            </c:choose>
        </kme:listView>
    </kme:content>
	<c:set var="fileName" value="narbystops"/>
	<%@ include file="footer.jsp" %>
</kme:page>
