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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<spring:message code="bus.label.favorites"      var="busFavoriteLabel"/>
<spring:message code="bus.label.map"            var="busMapLabel"/>
<spring:message code="bus.label.nearbystops"    var="busNearbyStopsLabel"/>
<spring:message code="bus.label.routes"         var="busRouteLabel"/>
<nav class="kme-alt-nav navbar navbar-default navbar-fixed-bottom" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="btn-group btn-group-justified">
        <c:forEach var="tab" items="${tabOrder}">
            <c:choose>
                <c:when test="${tab == 'map'}">
        <a class="btn btn-default navbar-btn" role="button" href="#/map" ng-class="{active:BusData.tabNumber==0}"><c:out value="${busMapLabel}"/></a>
                </c:when>
                <c:when test="${tab == 'routes'}">
        <a class="btn btn-default navbar-btn" role="button" href="#/routes" ng-class="{active:BusData.tabNumber==1}"><c:out value="${busRouteLabel}"/></a>
                </c:when>
                <c:when test="${tab == 'favorites'}">
        <a class="btn btn-default navbar-btn" role="button" href="#/favorites" ng-class="{active:BusData.tabNumber==2}"><c:out value="${busFavoriteLabel}"/></a>
                </c:when>
                <c:when test="${tab == 'nearbystops'}">
        <a class="btn btn-default navbar-btn" role="button" href="#/nearbyStops" ng-class="{active:BusData.tabNumber==3}"><c:out value="${busNearbyStopsLabel}"/></a>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>
</nav>