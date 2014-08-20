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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<spring:message code="events.title.byCategory"  var="eventsByCategory"/>
<spring:message code="events.title.byDate"      var="eventsByDate"/>
<spring:message code="events.title.byRange"     var="eventsByDateRange"/>
<c:if test="${showCategoryTab == 'true' || showDateRangeTab == 'true'}">
<nav class="events-footer kme-alt-nav navbar navbar-default navbar-fixed-bottom" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="btn-group btn-group-justified">
        <a class="btn btn-default navbar-btn" ng-class="{ active: isActive('/byDate') || isActive('/') }" role="button" href="#/byDate"><c:out value="${eventsByDate}"/></a>
    <c:if test="${showCategoryTab == 'true'}">
        <a class="btn btn-default navbar-btn" ng-class="{ active: isActive('/category') }" role="button" href="#/category"><c:out value="${eventsByCategory}"/></a>
    </c:if>
    <c:if test="${showDateRangeTab == 'true'}">
        <a class="btn btn-default navbar-btn" ng-class="{ active: isActive('/byDateRange') }" role="button" href="#/byDateRange"><c:out value="${eventsByDateRange}"/></a>
    </c:if>
    </div>
</nav>
</c:if>