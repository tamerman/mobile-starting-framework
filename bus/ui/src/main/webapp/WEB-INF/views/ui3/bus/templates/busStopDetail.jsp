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
<div ng-controller="BusStopDetailController" ng-init="init()">
    <div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in BusData.errors.error">{{thisError.name}}</div>
    <div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in infos.info">{{thisInfo.name}}</div>
    <div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in successes.success">{{thisSuccess.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in alerts.alert">{{thisAlert.name}}</div>
    <div class="list-group">
        <div class="list-group-item list-header">
            {{BusData.currentStop.name}}
            <a ng-click="busToggleFavorite()" class="pull-right"><span class="glyphicon glyphicon-star {{BusData.favoriteColor}}"></span></a>
        </div>
        <div class="list-group-item" ng-hide="BusData.currentStop.scheduledStop">{{BusData.noInfoError}}</div>
        <div class="list-group-item" ng-repeat="nextBus in BusData.currentStop.scheduledStop | orderBy:'timeToArrival'">
            {{nextBus.timeToArrival}} min&nbsp;
            <img ng-if="nextBus.bus.color" ng-src="${pageContext.request.contextPath}/images/bus-icons/bus-{{nextBus.bus.color | uppercase}}-36x36.png"/>
            &nbsp;{{nextBus.bus.name}}
        </div>
    </div>
</div>