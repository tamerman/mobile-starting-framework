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


