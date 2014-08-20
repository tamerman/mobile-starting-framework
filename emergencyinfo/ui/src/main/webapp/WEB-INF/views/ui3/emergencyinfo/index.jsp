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


<spring:message code="emergencyinfo.title" var="msgCat_ToolTitle"/>
<spring:message code="emergencyinfo.true" var="msgCat_TrueEmer"/>
<spring:message code="emergencyinfo.phonenumbers" var="msgCat_Phone"/>
<spring:message code="emergencyinfo.noContacts" var="msgCat_NoContacts"/>



<kme3:page title="${msgCat_ToolTitle}" toolName="emergencyinfo" ngAppName="emergencyInfoApp" ngControllerName="EmergencyInfoController" ngInitFunction="init" backFunction="kmeNavLeft"  cssFilename="emergencyinfo" jsFilename="emergencyinfo">

<div id="theContentArea" class="main-view" ng-view="">
	<div class="list-group">
		<a href="tel:911" class="list-group-item">${msgCat_TrueEmer} : <span style="color:darkred;">911</span><div class="glyphicon glyphicon glyphicon-earphone pull-right"></div></a>	
	
	    <div class="list-group-item list-header">${msgCat_Phone}</div>
	    <div class="alert alert-danger" ng-click="clickHideResults()" ng-repeat="thisError in errors.error">{{thisError.name}}</div>
	    <div class="alert alert-info" ng-click="clickHideResults()" ng-repeat="thisInfo in infos.info">test {{thisInfo.name}}</div>
	    <div class="alert alert-success" ng-click="clickHideResults()" ng-repeat="thisSuccess in successes.success">{{thisSuccess.name}}</div>
	    <div class="alert alert-warning" ng-click="clickHideResults()" ng-repeat="thisAlert in alerts.alert">{{thisAlert.name}}</div>
		<a ng-click="emergencyInfoClick(thisEmergencyinfo)" class="list-group-item" ng-repeat="thisEmergencyinfo in emergencyinfos">
	   		<h5 class="list-group-item-heading">{{thisEmergencyinfo.title}}<div class="glyphicon pull-right" ng-class="{'glyphicon-earphone':thisEmergencyinfo.isPhone, 'glyphicon-envelope':thisEmergencyinfo.isEmail}"></div></h5>
	      	<p class="list-group-item-text">{{thisEmergencyinfo.link}}</p>		
		</a>	
	</div>
	
</div>

</kme3:page>