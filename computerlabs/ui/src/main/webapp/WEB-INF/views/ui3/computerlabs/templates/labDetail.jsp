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
<div>
	<div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in LabData.errors.error">{{thisError.name}}</div>
    <div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in LabData.infos.info">{{thisInfo.name}}</div>
    <div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in LabData.successes.success">{{thisSuccess.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in LabData.alerts.alert">{{thisAlert.name}}</div>
    <div class="list-group">
        <div class="list-group-item list-header">{{LabData.currentLab.lab}}</div>
        <div class="list-group-item">
            <p ng-show="LabData.configGroupSeats">{{LabData.currentLab.availability}} - {{LabData.labAll}} {{LabData.labSeats}}</p>
            <p ng-show="LabData.currentLab.windowsAvailability">{{LabData.currentLab.windowsAvailability}} - Windows {{LabData.labSeats}}</p>
            <p ng-show="LabData.currentLab.linuxAvailability">{{LabData.currentLab.linuxAvailability}} - Linux {{LabData.labSeats}}</p>
            <p ng-show="LabData.currentLab.macAvailability">{{LabData.currentLab.macAvailability}} - Mac {{LabData.labSeats}}</p>
        </div>
    </div>
</div>