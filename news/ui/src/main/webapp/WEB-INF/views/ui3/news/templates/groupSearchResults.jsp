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
       ng-repeat="simpleGroup in PeopleData.searchResults.groups track by $index">
        <span class="pull-right glyphicon glyphicon-chevron-right black"></span>
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
