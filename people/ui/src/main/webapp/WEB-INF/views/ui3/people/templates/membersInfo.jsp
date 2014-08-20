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