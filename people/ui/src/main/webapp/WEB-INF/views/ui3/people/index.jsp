<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="people.submit" var="submit"/>
<spring:message code="people.exactMatchLabel" var="exactMatchLabel"/>
<spring:message code="people.noResultsFoundMessage" var="noResultsFoundMessage"/>
<spring:message code="people.serviceErrorMessage" var="serviceErrorMessage"/>


<kme3:page title="Directory" toolName="people" ngAppName="peopleApp" ngControllerName="peopleController" ngInitFunction="init" backFunction="kmeNavLeft"  cssFilename="people" jsFilename="people">
	<div id="theContentArea" class="main-view" ng-view=""></div>
</kme3:page>