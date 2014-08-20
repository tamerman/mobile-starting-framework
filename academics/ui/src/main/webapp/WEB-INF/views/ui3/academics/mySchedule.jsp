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

<spring:message code="academics.title" var="msgAcademicsTitle"/>
<spring:message code="academics.terms" var="msgAcademicsTerms"/>
<spring:message code="academics.careers" var="msgAcademicsCareers"/>
<spring:message code="academics.subjects" var="msgAcademicsSubjects"/>
<spring:message code="academics.catalogNumbers" var="msgAcademicsCatalogNumbers"/>

<kme3:page title="{{myScheduleData.pageTitle}}" toolName="myAcademics" ngAppName="myScheduleApp" ngControllerName="MyScheduleController" ngInitFunction="init" backFunction="kmeNavLeft" cssFilename="academics" jsFilename="mySchedule, errorsHandler">
    <div class="http-error-messages" http-error-messages></div>
    <div id="theContentArea" class="main-view" ng-view="">
    </div>
</kme3:page>
