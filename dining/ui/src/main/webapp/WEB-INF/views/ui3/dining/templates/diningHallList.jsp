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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<spring:message code="dining.diningHalls.displayWithGroups" var="diningHallDisplay"/>
<div ng-controller="DiningController" ng-init="init()">

    <notification-list></notification-list>
    <div class="list-group">
        <div ng-repeat="diningHallGroup in DiningData.diningHallGroups.diningHallGroup">
            <div ng-switch on="${diningHallDisplay}">
                <div class="list-group-item list-header" ng-switch-when="true">{{diningHallGroup.name}}</div>
                <a href="#/menuList" ng-repeat="diningHall in diningHallGroup.diningHall" class="list-group-item"  ng-click="diningHallClick(diningHall)">
                    <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
                    {{diningHall.name}}</a>
            </div>
        </div>
    </div>
</div>
