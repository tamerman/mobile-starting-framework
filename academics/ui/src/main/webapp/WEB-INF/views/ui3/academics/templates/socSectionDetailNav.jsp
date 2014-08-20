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
<spring:message code="academics.sectionDetailBodyTabLabel" var="sectionDetailBodyTabLabel"/>
<spring:message code="academics.sectionDetailDescriptionTabLabel" var="sectionDetailDescriptionTabLabel"/>
<spring:message code="academics.sectionDetailExtraTabLabel" var="sectionDetailExtraTabLabel"/>
<nav class="kme-alt-nav navbar navbar-default navbar-fixed-bottom" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="btn-group btn-group-justified">
    <a class="btn btn-default navbar-btn" role="button" ng-click="socSectionDetailBodyClick()" ng-class="{active:socShowDetailBody}"><c:out value="${sectionDetailBodyTabLabel}"/></a>
    <a class="btn btn-default navbar-btn" role="button" ng-click="socSectionDetailDescriptionClick()" ng-class="{active:socShowDetailDescription}"><c:out value="${sectionDetailDescriptionTabLabel}"/></a>
    <a class="btn btn-default navbar-btn" role="button" ng-click="socSectionDetailExtraClick()" ng-class="{active:socShowDetailExtra}"><c:out value="${sectionDetailExtraTabLabel}"/></a>
    </div>
</nav>