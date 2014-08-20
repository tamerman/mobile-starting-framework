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