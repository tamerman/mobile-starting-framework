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
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<spring:message code="dining.title" var="msgToolTitle"/>
<spring:message code="dining.noDataAvailable" var="msgNoDataAvailable"/>
<spring:message code="dining.campusLabel" var="msgCampusLabel"/>
<spring:message code="dining.menuLabel" var="msgMenuLabel"/>
<kme:page title="${msgToolTitle}" id="diningPlaces" backButton="true" homeButton="true" cssFilename="dining">
    <kme:listView dataTheme="c" dataDividerTheme="b" filter="false">
        <c:forEach items="${diningHalls}" var="diningHall" varStatus="index">
        <kme:listItem><a href="${pageContext.request.contextPath}/dining/hall/${diningHall.name}" data-transition="none"><h3>${diningHall.name}</h3></a></kme:listItem>
        </c:forEach>
    </kme:listView>
</kme:page>