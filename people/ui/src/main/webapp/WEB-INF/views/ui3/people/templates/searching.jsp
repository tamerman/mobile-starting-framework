<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="people.affiliation" var="affiliation"/>
<spring:message code="people.firstName" var="firstName"/>
<spring:message code="people.groupDescription" var="groupDescription"/>
<spring:message code="people.groupsLabel" var="groupsLabel"/>
<spring:message code="people.lastName" var="lastName"/>
<spring:message code="people.peopleLabel" var="peopleLabel"/>
<spring:message code="people.searchWatermark" var="searchWatermark"/>
<spring:message code="people.isExactly" var="msgCat_IsExactly"/>
<spring:message code="people.username" var="username"/>
<spring:message code="people.submit" var="labelSubmit"/>
<spring:message code="people.matchType" var="labelMatchType"/>
<spring:message code="people.searchTypeSimple" var="searchTypeSimple"/>
<spring:message code="people.searchTypeAdvanced" var="searchTypeAdvanced"/>
<spring:message code="people.startsWith" var="msgCat_StartsWith"/>
<spring:message code="people.resultsShow" var="resultsShow"/>
<spring:message code="people.resultsHide" var="resultsHide"/>
<div ng-controller="searchController" ng-init="init()">
	
	<div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in PeopleData.errors.error">{{thisError.name}}</div>
	<div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in PeopleData.successes.success">{{thisSuccess.name}}</div>
	<div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in PeopleData.infos.info">{{thisInfo.name}}</div>
	<div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in PeopleData.alerts.alert">{{thisAlert.name}}</div>
	
    <div class="inset-content-10px" ng-show="PeopleData.advancedToggle">
        <button id="searchType" type="button" class="btn btn-primary" ng-click="clickSearchType()">${searchTypeAdvanced}</button>
        <button id="searchHide" type="button" class="btn btn-primary" ng-click="clickHideResults()" ng-show="shouldHideResultButton()">${resultsHide}</button>
    </div>
    <div ng-show="PeopleData.showSimpleSearch">
        <form ng-submit="simpleSubmit()" handle-phone-submit>
            <fieldset>
                <div class="input-group search-input">
                    <input id="simpleText" type="text" class="form-control" ng-model="simpleText"
                           placeholder="${searchWatermark}">
                    <span class="input-group-addon glyphicon glyphicon-search" ng-click="simpleSubmit()"></span>
                </div>
            </fieldset>
        </form>
        <div class="list-group" ng-show="PeopleData.simpleResultsPage">
            <people-search-results></people-search-results>
            <group-search-results></group-search-results>
        </div>
    </div>
    <div class="list-group" ng-show="PeopleData.showAdvancedSearch">
        <form ng-show="PeopleData.showAdvancedSearchForm" class='list-group-item'>
            <div class="form-group">
                <label for="firstNameText">${firstName}:</label>
                <input id="firstNameText" type="text" class="form-control" ng-model="firstNameText">
            </div>
            <div class="form-group">
                <label for="lastNameText">${lastName}:</label>
                <input id="lastNameText" type="text" class="form-control" ng-model="lastNameText">
            </div>
            <div class="form-group">
                <label for="matching">${labelMatchType}:</label>
                <select class="form-control" id="matching" name="matching" ng-model="matching"
                        ng-init="matching='starts'">
                    <option value="starts" selected="selected"><spring:message code="people.startsWith"/></option>
                    <option value="is equal"><spring:message code="people.isExactly"/></option>
                </select>
            </div>
            <div class="form-group">
                <select class="form-control" id="status" name="status" ng-model="status" ng-init="status='Any'">
                    <option value="Any" selected="selected"><spring:message code="people.status.any"/></option>
                    <option value="Student"><spring:message code="people.status.student"/></option>
                    <option value="Faculty"><spring:message code="people.status.faculty"/></option>
                    <option value="Employee"><spring:message code="people.status.employee"/></option>
                </select>
            </div>
            <div class="form-group" ng-show="locations">
                <select class="form-control" id="locationSelect" name="locationSelect" ng-model="locationSelect"
                        ng-options="location as location for location in locations"
                        ng-init="locationSelect = locations[0]">
                </select>
            </div>
            <input type="hidden" name="location" value="{{ locationSelect }}"/>

            <div class="form-group">
                <label for="usernameText">${username}</label>
                <input id="usernameText" type="text" class="form-control" ng-model="usernameText">
            </div>
            <div class="centerContent">
                <button type="submit" class="btn btn-kme" ng-click="advancedSubmit()">${labelSubmit}</button>
            </div>
        </form>
        <div class="list-group" ng-show="PeopleData.advancedResultsPage">
            <people-search-results></people-search-results>
            <group-search-results></group-search-results>
        </div>
    </div>
</div>