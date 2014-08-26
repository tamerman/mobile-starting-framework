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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="dining.menuLabel" var="msgMenuLabel"/>
<spring:message code="dining.useMaps" var="useMaps"/>
<spring:message code="dining.noAddress" var="noAddress"/>

<div class="list-group" ng-controller="DiningHallController" ng-init="init()">
	<notification-list></notification-list>
    <div ng-show="DiningData.currentDiningHall.building.name" class="list-group-item list-header">{{DiningData.currentDiningHall.name}}</div>
    <c:choose>
        <c:when test="${useMaps =='true'}">
            <a ng-show="DiningData.currentDiningHall.building.name" href="#/menuList" ng-click="mapViewClick(DiningData.currentDiningHall.building)" class="list-group-item">
        	<span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
        </c:when>
		<c:otherwise><div ng-show="DiningData.currentDiningHall.building.name" class="list-group-item"></c:otherwise>
    </c:choose>
    	<span>{{DiningData.currentDiningHall.building.name}}</span>
        <p ng-show="DiningData.currentDiningHall.building.address">
            <span ng-show="DiningData.currentDiningHall.building.address.street1">{{DiningData.currentDiningHall.building.address.street1}}<br/></span>
            <span ng-show="DiningData.currentDiningHall.building.address.street2">{{DiningData.currentDiningHall.building.address.street2}}<br/></span>
            <span ng-show="DiningData.currentDiningHall.building.address.city">{{DiningData.currentDiningHall.building.address.city}}, </span>
            <span ng-show="DiningData.currentDiningHall.building.address.state">{{DiningData.currentDiningHall.building.address.state}} </span>
            <span ng-show="DiningData.currentDiningHall.building.address.postalCode">{{DiningData.currentDiningHall.building.address.postalCode}}</span>
        </p>
        <p ng-show="!DiningData.currentDiningHall.building.address">
        	${noAddress}
        </p>
    <c:choose>
        <c:when test="${useMaps == 'true'}"></a></c:when>
    	<c:otherwise></div></c:otherwise>
    </c:choose>
    <div class="list-group-item list-header">${msgMenuLabel}</div>
    <div ng-repeat="thisMenu in DiningData.currentDiningHall.menu">
        <a href="#/menu" class="list-group-item" ng-click="diningMenuClick(thisMenu)" ng-show="thisMenu.category">
            <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration" ng-show="thisMenu.category"></span>
            {{thisMenu.name}}<p>{{thisMenu.description}}</p></a>
        <div class="list-group-item" ng-click="diningMenuClick(thisMenu)" ng-hide="thisMenu.category">
            <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration" ng-show="thisMenu.category"></span>
            {{thisMenu.name}}<p>{{thisMenu.description}}</p></div>
    </div>
</div>