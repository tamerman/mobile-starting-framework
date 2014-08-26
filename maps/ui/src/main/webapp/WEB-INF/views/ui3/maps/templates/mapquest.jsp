<%--

    The MIT License
    Copyright (c) 2011 Kuali Mobility Team

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

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
    </div>
