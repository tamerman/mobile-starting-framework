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