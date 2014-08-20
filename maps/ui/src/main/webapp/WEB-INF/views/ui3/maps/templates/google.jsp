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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:message code="maps.search" var="msgCat_Search"/>
<spring:message code="maps.searchErrorMessage" var="searchErrorMessage"/>


    <div ng-controller="MapController" ng-init="init()">
        <form role="form" ng-submit="searchForMapLocations()">
            <fieldset>
                <div class="input-group map-search-input">
                    <input type="text" class="form-control" id="searchText" placeholder="${msgCat_Search}" ng-model="searchText">
                    <span class="input-group-addon glyphicon glyphicon-search"></span>
                </div>
            </fieldset>
        </form>
        <div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in errors.error">{{thisError.name}}</div>
    <div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in infos.info">{{thisInfo.name}}</div>
    <div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in successes.success">{{thisSuccess.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in alerts.alert">{{thisAlert.name}}</div>
        <div id="searchresults" ng-show="showResults" ng-init="showResults=false" class="list-group">
            <div class="list-group-item" ng-repeat="thisLocation in locations.location" ng-click="clickLocation(thisLocation)">{{thisLocation.name}}</div>
        </div>
        <div id="map_canvas"></div>
        <%--<script src="${pageContext.request.contextPath}/js/arcgislink.js" type="text/javascript"></script>--%>
    </div>
