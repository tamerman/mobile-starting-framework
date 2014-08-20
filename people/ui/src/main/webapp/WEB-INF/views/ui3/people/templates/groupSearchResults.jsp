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
<spring:message code="people.firstName" var="firstName"/>
<spring:message code="people.groupDescription" var="groupDescription"/>
<spring:message code="people.groupsLabel" var="groupsLabel"/>
<spring:message code="people.lastName" var="lastName"/>
<spring:message code="people.peopleLabel" var="peopleLabel"/>
<spring:message code="people.searchWatermark" var="searchWatermark"/>
<spring:message code="people.isExactly" var="msgCat_IsExactly"/>
<spring:message code="people.username" var="username"/>
<spring:message code="people.personNotFound" var="personNotFound"/>
<spring:message code="people.groupNotFound" var="groupNotFound"/>
<spring:message code="people.noMembersFound" var="noMembersFound"/>
<spring:message code="people.noPeopleFound" var="noPeopleFound"/>
<spring:message code="people.noResultsFoundMessage" var="noResultsFoundMessage"/>
<spring:message code="people.errorFindingContact" var="errorFindingContact"/>
<spring:message code="people.noMembersFound" var="noMembersFound"/>
<div>
    <div class="list-group-item list-header">${groupsLabel}</div>
    <a href="#/group/{{simpleGroup.displayName}}" class="list-group-item" ng-click="groupClick(simpleGroup)"
       ng-repeat="simpleGroup in PeopleData.searchResults.groups track by $index" style="position:relative">
        <span class="pull-right glyphicon glyphicon-chevron-right black right_aligned_arrow"></span>
        {{simpleGroup.displayName}}
        <p>
            <b>${groupDescription}: </b>
            <span ng-repeat="description in simpleGroup.descriptions track by $index">{{description}}<span
                    ng-show="$first && !$last || $middle ">, </span></span>
        </p>
    </a>
    <div class="list-group-item" ng-hide="PeopleData.searchResults.groups">
        ${noResultsFoundMessage}
    </div>
</div>
