<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
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

<spring:message code="preferences.title" var="msgCat_ToolTitle" />

<kme3:page title="${msgCat_ToolTitle}" toolName="home"
	ngAppName="sharedPreferenceApp"
	ngControllerName="SharedPreferencesController" ngInitFunction="init"
	backFunction="kmeNavLeft" cssFilename="" jsFilename="preferences">

	<div id="theContentArea" class="main-view" ng-view="">
		<div class="alert alert-danger" ng-repeat="error in errors.error">
			{{error.name}}</div>
		<div id="mainCampusPage" class="list-group"
			ng-show="showPreferencePage" ng-init="showPreferencePage=true">
			<%-- Show user authentication status --%>
			<div class="list-group-item list-header">
				<spring:message code="preferences.authenticationStatus" />
			</div>
			<c:if test="${user.publicUser}">
                <a class="list-group-item" href="${pageContext.request.contextPath}/auth"><spring:message code="shared.login" /> [<spring:message
                        code="preferences.unathenticated" />] </a>
			</c:if>
			<c:if test="${not user.publicUser}">
                <a class="list-group-item" href="${pageContext.request.contextPath}/auth/logout"><spring:message code="shared.logout" />
                    [${user.loginName}]</a>
			</c:if>

			<%-- Select Home screen --%>
			<div class="list-group-item list-header">
				<spring:message code="preferences.selectHomeScreen" />
			</div>
			<c:forEach items="${homeScreens}" var="homeScreen" varStatus="status">
                <a class="list-group-item"
                    href="${pageContext.request.contextPath}/campus/select?toolName=${toolName}&campus=${homeScreen.alias}">
                    <c:out value="${homeScreen.title}" /> <c:if
                        test="${homeScreen.alias == cookie['campusSelection'].value}">
                        <span class="ui-check-char" >✓</span>
                    </c:if>
                </a>
			</c:forEach>
			<%-- Customize Home screen --%>
			<div class="list-group-item list-header">
				<spring:message code="preferences.customizeHomeScreen" />
			</div>
			<a class="list-group-item"
                    href="customizeHome">
                    <spring:message code="preferences.customizeHomeScreen" />
                </a>
			<%-- Select language --%>
			<div class="list-group-item list-header">
				<spring:message code="preferences.languages" />
			</div>
			<c:forEach items="${supportedLanguages}" var="lang">
                <a class="list-group-item" href="?lang=${lang}"> <spring:message
                        code="preferences.language.${lang}" /> <c:if
                        test="${pageContext.response.locale == lang}">
                        <span class="ui-check-char">✓</span>
                    </c:if>
                </a>
			</c:forEach>
			<%-- Select home screen layout - if allowed to --%>
			<c:if test="${allowLayoutChange == true}">
				<div class="list-group-item list-header">
					<spring:message code="preferences.layout" />
				</div>
				<c:forEach items="${availableLayouts}" var="layoutName">
                    <a class="list-group-item" href="?homeLayout=${layoutName}"> <spring:message
                            code="preferences.layout.${layoutName}" /> <c:if
                            test="${layoutName eq currentLayout}">
                            <span class="ui-check-char">✓</span>
                        </c:if>
                    </a>
				</c:forEach>
			</c:if>


			<%-- Push Opt Out --%>
			<c:if test="${not empty senders}">
				<div class="list-group-item list-header">
					<spring:message code="preferences.push.optout" />
				</div>
				<c:forEach items="${senders}" var="sender" varStatus="status">
                    <a class="list-group-item" href="#" ng-click="toggle('${sender.id}');"> ${sender.name}
                        <span id="check${sender.id}" ng-if="pushEnabled_${sender.id}" class="ui-check-char">✓</span>
                    </a>
				</c:forEach>
			</c:if>

			<%-- Install application if using web --%>
			<c:if test="${cookie['native'].value != 'yes'}">
				<div class="list-group-item list-header">
					<spring:message code="preferences.nativeapp" />
				</div>
                <a class="list-group-item" href="#" ng-click="installClick()"> <spring:message
                        code="preferences.installnativeapp" /></a>
					<%--<div class="result"></div>--%>
					<%--<div class="log"></div>--%>
			</c:if>
		</div>
	</div>

</kme3:page>


