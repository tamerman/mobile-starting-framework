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