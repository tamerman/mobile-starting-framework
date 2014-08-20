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