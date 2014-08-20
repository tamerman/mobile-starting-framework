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

<spring:message code="mdot.pageTitle" var="msgCat_ToolTitle" />

<kme3:page title="${msgCat_ToolTitle}" toolName="home"
	ngAppName="homeApp" ngControllerName="homeController"
	ngInitFunction="init" backFunction="" cssFilename="home" jsFilename="home">

	<div id="theContentArea" class="main-view" ng-view="">
		<div class="alert alert-danger" ng-repeat="error in errors.error">
			{{error.name}}</div>
		<c:choose>
			<c:when test="${currentLayout == 'tiles'}">
				<div class="home-tile-container">
					<a ng-href="{{a.tool.url}}" class="home-tool-tile ng-cloak"
						ng-repeat="a in HomeTools.tools | orderBy:'order'"
						ng-hide="a.hideData"> <img class="home-tool-icon"
						src="${pageContext.request.contextPath}/images/pixel.png"
						style="background-image: url('${pageContext.request.contextPath}/{{a.tool.iconUrl}}');"
						ng-if="a.tool.iconUrl" alt="{{a.tool.description}}" />
						<p>{{a.tool.title}}</p>
					</a>
					<c:choose>
						<c:when test="${showAbout == true}">
							<a href="about" class="about home-tool-tile home-tool-icon"> <span
								class="glyphicon glyphicon-info-sign base-color"></span>&nbsp; <spring:message
									code="mdot.aboutLabel" />
							</a>
						</c:when>
					</c:choose>
				</div>
			</c:when>
			<c:otherwise>
				<div class="list-group">
					<a ng-href="{{a.tool.url}}" class="list-group-item home-tool-item ng-cloak"
						ng-repeat="a in HomeTools.tools | orderBy:'order'"
						ng-hide="a.hideData"> <span class="home-tool-icon pull-left"
						style="background-image: url('${pageContext.request.contextPath}/{{a.tool.iconUrl}}');"
						ng-if="a.tool.iconUrl"></span>  
						<span
						class="countBadge_count ui-btn-up-c ui-btn-corner-all" ng-hide="{{a.tool.badgeCount == 'null'?true:false}}">{{a.tool.badgeCount}}</span>
						<span class="countBadge ui-btn-up-c ui-btn-corner-all" ng-hide="{{a.tool.badgeText == 'null'?true:false}}">{{a.tool.badgeText}}</span>
						<h3>{{a.tool.title}}</h3>
						<p class="wrap">{{a.tool.description}}</p>
					</a>
					<c:choose>
						<c:when test="${showAbout == true}">
							<a href="about" class="about list-group-item"> <span
								class="glyphicon glyphicon-info-sign base-color"></span>&nbsp; <spring:message
									code="mdot.aboutLabel" />
							</a>
						</c:when>
					</c:choose>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

</kme3:page>