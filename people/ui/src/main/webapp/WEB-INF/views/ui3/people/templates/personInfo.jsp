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
<spring:message code="people.affiliation" var="affiliation"/>
<spring:message code="people.department" var="department"/>
<spring:message code="people.address" var="address"/>
<div class="list-group" ng-controller="personController" ng-init="init()">
	<div class="list-group-item list-header">{{PeopleData.currentPerson.displayName}}</div>
	<div class="list-group-item" >
        <div ng-show="PeopleData.currentPerson.departments">
            <strong>${department}:</strong>
            <span ng-repeat="departments in PeopleData.currentPerson.departments track by $index">{{departments}}<span ng-show="$first && !$last || $middle ">, </span></span>
        </div>
        <div ng-show="PeopleData.currentPerson.affiliations">
            <strong>${affiliation}:</strong>
            <span ng-repeat="affiliations in PeopleData.currentPerson.affiliations track by $index">{{affiliations}}<span ng-show="$first && !$last || $middle ">, </span></span>
        </div>
        <div ng-show="PeopleData.currentPerson.address">
            <strong>${address}:</strong>
            <span>{{PeopleData.currentPerson.address}}</span>
        </div>
    </div>
	<div><a class="list-group-item" ng-show="PeopleData.currentPerson.email" href="mailto:{{PeopleData.currentPerson.email}}" >{{PeopleData.currentPerson.email}}<span class="pull-right glyphicon glyphicon-envelope black"></span></a></div>
    <div><a class="list-group-item" ng-show="PeopleData.currentPerson.phone" href="tel:{{PeopleData.currentPerson.phone}}" >{{PeopleData.currentPerson.phone}}<span class="pull-right glyphicon glyphicon-earphone black"></span></a></div>
</div>