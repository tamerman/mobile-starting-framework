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