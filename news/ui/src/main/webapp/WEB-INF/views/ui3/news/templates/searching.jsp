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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="news.search.title" var="newsTitle" />
<spring:message code="news.search.description" var="newsDescription" />
<spring:message code="news.searchWatermark" var="searchWatermark" />
<spring:message code="people.submit" var="labelSubmit" />
<spring:message code="people.searchTypeSimple" var="searchTypeSimple" />
<spring:message code="people.searchTypeAdvanced"
	var="searchTypeAdvanced" />
<spring:message code="people.resultsShow" var="resultsShow" />
<spring:message code="people.resultsHide" var="resultsHide" />
<div ng-controller="NewsSearchController">
	<div class="alert alert-danger" ng-click="clickHideErrors()"
		ng-repeat="thisError in NewsData.errors.error">{{thisError.name}}</div>
	<div class="alert alert-success" ng-click="clickHideSuccesses()"
		ng-repeat="thisSuccess in NewsData.successes.success">{{thisSuccess.name}}</div>
	<div class="alert alert-info" ng-click="clickHideInfos()"
		ng-repeat="thisInfo in NewsData.infos.info">{{thisInfo.name}}</div>
	<div class="alert alert-warning" ng-click="clickHideAlerts()"
		ng-repeat="thisAlert in NewsData.alerts.alert">{{thisAlert.name}}</div>
	<form ng-submit="simpleSubmit()">
		<fieldset>
			<div class="input-group search-input">
				<input id="simpleText" type="text" class="form-control"
					ng-model="simpleText" placeholder="${searchWatermark}"> <span
					class="input-group-addon glyphicon glyphicon-search"
					ng-click="simpleSubmit()"></span>
			</div>
		</fieldset>
	</form>
	<!-- <div class="list-group" ng-show="NewsData.simpleResultsPage"> -->
	<div class="one" ng-show="tempobj.showSearchResult === 1">
		<div class="list-group">
			<a href="#/article" ng-click="newsArticleClick(newsArticle)"
				id="{{newsArticle.articleId}}" class="list-group-item"
				ng-repeat="newsArticle in currentNewsSource.articles | filter:searchFilter "> <span
				class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
				{{newsArticle.title}}
			</a>
		</div>
	</div>
	<!-- </div> -->

</div>