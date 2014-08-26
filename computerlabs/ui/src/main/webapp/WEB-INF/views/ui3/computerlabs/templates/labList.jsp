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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="computerlabs.seats" var="msgCat_Seats"/>
<div>
	<div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in LabData.errors.error">{{thisError.name}}</div>
    <div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in LabData.infos.info">{{thisInfo.name}}</div>
    <div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in LabData.successes.success">{{thisSuccess.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in LabData.alerts.alert">{{thisAlert.name}}</div>
    <div class="list-group">
        <div ng-repeat="thisLocation in LabData.currentLabGroup.locations">
            <div class="list-group-item list-header">{{thisLocation.name}}</div>
            <div ng-repeat="lab in thisLocation.labs">
            <c:choose>
                <c:when test="${useDetail == 'true' || useMaps =='true'}">
                    <a href="#/detail" ng-click="labDetailClick(lab)" class="list-group-item">
                    <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
                </c:when>
                <c:otherwise><div class="list-group-item"></c:otherwise>
            </c:choose>
                <strong>{{lab.lab}}</strong>
            <c:choose>
                <c:when test="${groupSeats == 'true'}"><p>{{lab.availability}}<c:out value="${msgCat_Seats}"/></p></c:when>
                <c:otherwise>
                <p>Availability:
                    <span ng-show="lab.windowsAvailability">Windows - {{lab.windowsAvailability}}</span>
                    <span ng-show="lab.linuxAvailability">Linux - {{lab.linuxAvailability}}</span>
                    <span ng-show="lab.macAvailability">Mac - {{lab.macAvailability}}</span>
                </p>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${useDetail == 'true'}"></a></c:when>
                <c:otherwise></div></c:otherwise>
            </c:choose>
            </div>
        </div>
    </div>
</div>