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