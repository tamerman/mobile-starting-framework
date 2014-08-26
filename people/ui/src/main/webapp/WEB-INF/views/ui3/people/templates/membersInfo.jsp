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
<spring:message code="people.groupMembers" var="groupMembers"/>
<spring:message code="people.groupOwners" var="groupOwners"/>
<div class="list-group" ng-controller="membersController" ng-init="init()">
	<div class="list-group-item list-header">${groupOwners}</div>
	<a href="#/person" class="list-group-item" ng-repeat="owner in PeopleData.currentGroup.owners" ng-click="clickPerson(owner)">
        <span class="pull-right glyphicon glyphicon-chevron-right black"></span>
        {{owner.displayName}}<p>{{owner.userName}}</p>
    </a>
	<div class="list-group-item list-header">${groupMembers}</div>
	<a href="#/person" class="list-group-item" ng-repeat="member in PeopleData.currentGroup.members" ng-click="clickPerson(member)">
        <span class="pull-right glyphicon glyphicon-chevron-right black"></span>
        {{member.displayName}}<p>{{member.userName}}</p>
    </a>
</div>