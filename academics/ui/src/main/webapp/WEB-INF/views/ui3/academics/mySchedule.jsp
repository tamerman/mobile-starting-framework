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
