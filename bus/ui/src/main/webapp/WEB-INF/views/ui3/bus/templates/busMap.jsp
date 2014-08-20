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
<div ng-controller="BusMapController" ng-init="init()" id="busContainer">
    <div class="list-group" ng-show="loadingRoutes" ng-init="loadingRoutes=true">
        <div class="list-group-item">Building route list....</div>
    </div>
    <form role="form" ng-submit="changeBusRoute()" ng-hide="loadingRoutes">
        <select name="select-route" id="select-route" class="form-control">
            <%--<option value="{{thisRoute.id}}" ng-repeat="thisRoute in BusData.routes.busRoute">{{thisRoute.name}}</option>--%>
        </select>
    </form>
    <div class="fixed-top">
	    <div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in BusData.errors.error">{{thisError.name}}</div>
	    <div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in BusData.infos.info">{{thisInfo.name}}</div>
	    <div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in BusData.successes.success">{{thisSuccess.name}}</div>
	    <div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in BusData.alerts.alert">{{thisAlert.name}}</div>
    </div>
    <div id="map_canvas"></div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bus/js/busMap.js"></script>
    <bus-tabs></bus-tabs>
</div>