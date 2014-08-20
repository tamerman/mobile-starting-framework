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
<div ng-controller="BusNearbyStopController" data-ng-init="init()">
    <div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in BusData.errors.error">{{thisError.name}}</div>
    <div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in infos.info">{{thisInfo.name}}</div>
    <div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in successes.success">{{thisSuccess.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in alerts.alert">{{thisAlert.name}}</div>
    <div class="list-group">
        <div class="list-group-item" ng-show="isLoading" ng-init="isLoading=true">Searching for stops.</div>
        <div class="list-group-item" ng-hide="!isLoading && BusData.nearbyStops.stop.length > 0">
            <span ng-switch on="BusData.units">
                <span ng-switch-when="imperial">No stops found within <span ng-if="radius < 300">{{radius * 3.28 | number:2}} ft</span>
                    <span ng-if="radius >= 300">{{radius * 3.28 / 5280 | number:2}} mi</span></span>
                <span ng-switch-default>No stops found within <span ng-if="radius < 300">{{radius | number:2}} m</span>
                    <span ng-if="radius >= 300">{{radius / 1000 | number:2}} km</span></span>
            </span>
        </div>
        <a href="#/stopDetail" class="list-group-item" ng-click="busStopClick(thisStop)"
           ng-repeat="thisStop in BusData.nearbyStops.stop">
            <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
            <img ng-if="nextBus.color" ng-src="${pageContext.request.contextPath}/images/bus-icons/bus-{{nextBus.color | uppercase}}-36x36.png"/>
            {{thisStop.name}}
            <span ng-switch on="BusData.units">
                <span ng-switch-when="imperial">
                    <span class="pull-right" ng-if="thisStop.distance < 300">{{thisStop.distance * 3.28 | number:2}} ft</span>
                    <span class="pull-right" ng-if="thisStop.distance >= 300">{{thisStop.distance * 3.28 / 5280 | number:2}} mi</span>
                </span>
                <span ng-switch-default>
                    <span class="pull-right" ng-if="thisStop.distance < 300">{{thisStop.distance | number:2}} m</span>
                    <span class="pull-right" ng-if="thisStop.distance >= 300">{{thisStop.distance / 1000 | number:2}} km</span>
                </span>
            </span>
        </a>
    </div>
    <bus-tabs></bus-tabs>
</div>