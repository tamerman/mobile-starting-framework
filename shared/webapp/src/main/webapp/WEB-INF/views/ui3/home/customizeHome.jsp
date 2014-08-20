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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="customize.home.title" var="msgCat_ToolTitle" />

<kme3:page title="${msgCat_ToolTitle}" toolName="home"
	ngAppName="customizehomeApp" ngControllerName="customizeHomeController"
	ngInitFunction="init" backFunction="" cssFilename="home"
	jsFilename="customizeHome">
	<div id="theContentArea" class="main-view" ng-view="" ng-controller="customizeHomeController">
		<div class="alert alert-danger" ng-repeat="error in errors.error">
			{{error.name}}</div>
        <div class="list-group-header">
            <div class="pull-left">
                <button type="button" class="btn btn-success"
                    ng-click="toggleCustomizationSwitch();init()"
                    ng-show="toggleCustomization"
                    btn-checkbox>On</button>
                <button type="button" class="btn btn-danger"
                    ng-click="toggleCustomizationSwitch();init()"
                    ng-hide="toggleCustomization"
                    btn-checkbox>Off</button>
                <%--<div class="onoffswitch">--%>
                    <%--<input type="checkbox" name="onoffswitch"--%>
                        <%--class="onoffswitch-checkbox" id="myonoffswitch"--%>
                        <%--ng-click="toggleCustomizationSwitch();init()"--%>
                        <%--ng-model="toggleCustomization"> <label--%>
                        <%--class="onoffswitch-label" for="myonoffswitch">--%>
                        <%--<div class="onoffswitch-inner"></div>--%>
                        <%--<div class="onoffswitch-switch"></div>--%>
                    <%--</label>--%>
                <%--</div>--%>
            </div>
            <div class="pull-right">
                <input type="button" value="Reset to Defaults"
                    ng-click="resetToDefault()" class="btn">
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="list-group">
            <div class="list-group-item tool-tile-container" ng-repeat="tool in HomeTools.tools | orderBy:'order'">
                <span
                    class="pull-left glyphicon glyphicon-arrow-up black tool-order-nav"
                    ng-click="upArrowClick(HomeTools.tools.indexOf(tool));init()"></span>
                <span
                    class="pull-left glyphicon glyphicon-arrow-down black tool-order-nav"
                    ng-click="downArrowClick(HomeTools.tools.indexOf(tool));init()"></span>
                <img class="home-tool-icon pull-left"
                     style="background-image: url('${pageContext.request.contextPath}/{{tool.tool.iconUrl}}');"
                     ng-if="tool.tool.iconUrl" alt="{{tool.tool.description}}"/>
                <button type="button" class="tool-toggle btn btn-success pull-right"
                    ng-click="showHideTool(tool.homeToolId)"
                    ng-show="showHideTools[tool.homeToolId]"
                    btn-checkbox>On</button>
                <button type="button" class="tool-toggle btn btn-danger pull-right"
                    ng-click="showHideTool(tool.homeToolId)"
                    ng-hide="showHideTools[tool.homeToolId]"
                    btn-checkbox>Off</button>
                <span class="tool-title">{{tool.tool.title}}</span>
            </div>
            <c:choose>
                <c:when test="${showAbout == true}">
                    <a href="about" class="about list-group-item"> <span
                        class="glyphicon glyphicon-info-sign base-color"></span>&nbsp; <spring:message
                            code="mdot.aboutLabel" />
                    </a>
                </c:when>
            </c:choose>
        </div>
	</div>
	<c:if test="${showCacheUpdate == true}">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/appcacheMonitor.js"></script>
	</c:if>
</kme3:page>