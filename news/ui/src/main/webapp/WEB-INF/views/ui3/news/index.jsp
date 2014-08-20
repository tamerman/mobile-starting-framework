<%--
  Copyright 2014 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="news.title" var="msgCat_ToolTitle"/>
<spring:message code="news.expand" var="msgCat_Expand"/>
<spring:message code="news.collapse" var="msgCat_Collapse"/>
<spring:message code="news.errorMsg" var="news_errorMsg"/>

<kme3:page title="{{NewsData.newsPageTitle}}" toolName="news" ngAppName="newsApp" ngControllerName="NewsController" ngInitFunction="init" backFunction="kmeNavLeft" cssFilename="news" jsFilename="news">
	<div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in NewsData.errors.error">{{thisError.name}}</div>
	<div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in NewsData.successes.success">{{thisSuccess.name}}</div>
	<div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in NewsData.infos.info">{{thisInfo.name}}</div>
	<div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in NewsData.alerts.alert">{{thisAlert.name}}</div>
	
	<div id="theContentArea" class="main-view" ng-view=""></div>
</kme3:page>
