<%--
Copyright 2014-${currentYear} The Kuali Foundation Licensed under the Educational
Community License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License
at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
    <div class="alert alert-danger" ng-click="clickHideErrors(thisError)" ng-repeat="thisError in AuthData.errors">{{thisError.name}}</div>
    <div class="alert alert-success" ng-click="clickHideSuccesses(thisSuccess)" ng-repeat="thisSuccess in AuthData.successes">{{thisSuccess.name}}</div>
    <div class="alert alert-info" ng-click="clickHideInfos(thisInfo)" ng-repeat="thisInfo in AuthData.infos">{{thisInfo.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideAlerts(thisAlert)" ng-repeat="thisAlert in AuthData.alerts">{{thisAlert.name}}</div>
</div>
