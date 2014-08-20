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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="library.title"							var="msgCat_ToolTitle"/>

<kme3:page title="${msgCat_ToolTitle}" toolName="library" ngAppName="libraryApp" cssFilename="library">

    <div class="alert alert-danger" ng-click="clickHideMessages()" ng-repeat="thisError in errors">{{thisError.name}}</div>
    <div class="alert alert-info" ng-click="clickHideMessages()" ng-repeat="thisInfo in infos">{{thisInfo.name}}</div>
    <div class="alert alert-success" ng-click="clickHideMessages()" ng-repeat="thisSuccess in successes">{{thisSuccess.name}}</div>
    <div class="alert alert-warning" ng-click="clickHideMessages()" ng-repeat="thisAlert in alerts">{{thisAlert.name}}</div>

    <div ng-view class="view-frame"></div>

    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ServerDetails.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui3/library/library.app.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui3/library/library.services.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui3/library/library.controllers.js" ></script>

</kme3:page>
