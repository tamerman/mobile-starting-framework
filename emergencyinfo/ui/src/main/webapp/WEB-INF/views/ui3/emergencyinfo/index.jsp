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