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
<spring:message code="dining.currencyLabel" var="msgCurrencyLabel"/>
<div class="list-group-item">
    <span class="pull-right" ng-show="thisMenuItem.attribute">
        <span class="menu-item-attribute nutrition nutrition-{{thisAttribute}}" ng-repeat="thisAttribute in thisMenuItem.attribute" ng-click="open()">{{thisAttribute}}</span>
    </span>
    <span class="pull-right" ng-show="thisMenuItem.price">
        <span class="menu-item-price" ng-repeat="thisPrice in thisMenuItem.price">{{thisPrice | currency:"${msgCurrencyLabel}"}}</span>
    </span>
    {{thisMenuItem.name}}
    <p ng-show="thisMenuItem.description">{{thisMenuItem.description}}</p>
</div>
