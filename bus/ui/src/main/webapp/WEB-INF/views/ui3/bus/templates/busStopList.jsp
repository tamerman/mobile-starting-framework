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
<div ng-controller="BusStopListController" ng-init="init()">
    <div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in BusData.errors.error">{{thisError.name}}</div>
    <div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in infos.info">{{thisInfo.name}}</div>
    <div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in successes.success">{{thisSuccess.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in alerts.alert">{{thisAlert.name}}</div>
    <div ng-switch on="busId" >
        <div class="list-group" ng-switch-when="busId.length>0">
            <div class="list-group-item list-header">{busId}</div>
            <div class="list-group-item" ng-repeat="thisStop in BusData.currentRoute.stops">
                <strong>{{thisStop.name}}</strong>
                <p ng-repeat="theScheduledStop in thisStop.scheduleStop | filter: {bus.id : busId}">
                    <span ng-if="theScheduledStop.timeToArrival>0">{{theScheduledStop.timeToArrival}} mins</span>
                    <span ng-if="theScheduledStop.timeToArrival<=0">arriving</span>
                </p>
            </div>
        </div>
        <div class="list-group" ng-switch-default>
            <a href="#/stopDetail" class="list-group-item" ng-click="busStopClick(thisStop)"
               ng-repeat="thisStop in BusData.currentRoute.stops">
                <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
                {{thisStop.name}}</a>
        </div>
    </div>
</div>