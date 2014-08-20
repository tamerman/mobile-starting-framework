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
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<spring:message code="academics.sectionDetailExtraLabel" var="msgAcademicsDetailExtraLabel"/>
<spring:message code="academics.sectionDetailExtraNoInfo" var="msgAcademicsDetailExtraText"/>
<div class="soc-section-description list-group-item">
    <p class="soc-section-description-label"><c:out value="${msgAcademicsDetailExtraLabel}"/></p>
    <p ng-show="SocData.socCurrentSection.additionalInfo" ng-bind-html="SocData.socCurrentSection.additionalInfo"></p>
    <p ng-hide="SocData.socCurrentSection.additionalInfo"><c:out value="${msgAcademicsDetailExtraText}"/></p>
</div>