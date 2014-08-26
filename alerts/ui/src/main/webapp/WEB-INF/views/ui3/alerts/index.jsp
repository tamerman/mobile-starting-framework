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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:message code="alerts.title" var="msgCat_Title"/>
<spring:message code="alerts.noAlerts" var="msgCat_NoAlerts"/>



<kme3:page title="${msgCat_Title}" toolName="alerts" ngAppName="alertsApp" ngControllerName="AlertsController" ngInitFunction="init" backFunction="kmeNavLeft"  cssFilename="alerts" jsFilename="alerts">

<div id="theContentArea" class="main-view" ng-view="">
	<div class="alert alert-danger" ng-repeat="error in errors.error">
		{{error.name}}
	</div>
	
	<div style="padding: 10px 10px 10px 10px;">
		<div ng-if="{!alerts.length}">	
			<div class="panel panel-success" ng-cloak ng-show="!alerts.length">
			  	<div class="panel-heading">
				    <h3 class="panel-title"><c:out value="${msgCat_NoAlerts}"/><div class="glyphicon glyphicon glyphicon-ok-sign pull-right"></div></h3>
			  	</div>
			  	<div class="panel-body">
				    <c:out value="${msgCat_NoAlerts}"/>
			  	</div>
			</div>
		</div>

		<div class="panel panel-default" ng-repeat="thisAlert in alerts" ng-class="{'panel-warning':thisAlert.isCaution, 'panel-danger':thisAlert.isEmergency, 'panel-info':thisAlert.isInformation, 'panel-success':thisAlert.isNormal}">
		  	<div class="panel-heading">
			    <h3 class="panel-title">{{thisAlert.campus}} - {{thisAlert.title}} <div class="glyphicon pull-right" ng-class="{'glyphicon-exclamation-sign':thisAlert.isCaution, 'glyphicon-warning-sign':thisAlert.isEmergency, 'glyphicon-info-sign':thisAlert.isInformation, 'glyphicon glyphicon-ok-sign':thisAlert.isNormal}"></h3>			   
		  	</div>
		  	<div class="panel-body">
			    {{thisAlert.mobileText}}
		  	</div>
		</div>
	</div>
	
</div>

</kme3:page>