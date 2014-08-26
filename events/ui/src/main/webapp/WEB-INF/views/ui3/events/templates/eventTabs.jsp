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