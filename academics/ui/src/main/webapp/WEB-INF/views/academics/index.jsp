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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="academics.title" var="title"/>

<kme:page title="${title}" id="academics" backButton="true" homeButton="true" backButtonURL="${pageContext.request.contextPath}/" cssFilename="academics" jsFilename="academics">
    <kme:content>
        <kme:listView dataDividerTheme="b" filter="false">
            <c:forEach items="${homeScreenTools}" var="toolName">
                <c:choose>
                    <c:when test="${toolName == 'browseclasses'}">
                        <kme:listItem hideDataIcon="true">
                            <a href="${pageContext.request.contextPath}/academics/terms" style="background-image: url('${pageContext.request.contextPath}/images/academics/browse-classes.png');">
                                <h3>Browse Classes</h3>
                                <p class="wrap">Browse classes by subject, course number and section</p>
                            </a>
                        </kme:listItem>
                    </c:when>
                    <c:when test="${toolName == 'myschedule'}">
                        <kme:listItem dataIcon="custom" id="lockbadge">
                            <a href="${pageContext.request.contextPath}/myAcademics/" style="background-image: url('${pageContext.request.contextPath}/images/academics/my-class-schedule.png');">
                                <h3>My Class Schedule</h3>
                                <p class="wrap">View enrolled classes and grades</p>
                            </a>
                        </kme:listItem>
                    </c:when>
                    <c:when test="${toolName == 'advancedsearch'}">
                        <kme:listItem hideDataIcon="true">
                            <a href="${pageContext.request.contextPath}/academics/search" style="background-image: url('${pageContext.request.contextPath}/images/academics/course-search.png');">
                                <h3>Search Classes</h3>
                                <p class="wrap">Search classes by term, subject, number and instructor</p>
                            </a>
                        </kme:listItem>
                    </c:when>
                    <c:when test="${toolName == 'gradealerts' and (platform == 'Android' or platform == 'iOS')}">
                        <kme:listItem dataIcon="custom" id="lockbadge">
                            <a href="${pageContext.request.contextPath}/myAcademics/gradeAlerts" style="background-image: url('${pageContext.request.contextPath}/images/academics/grade-alert.png');">
                                <h3>Grade Alerts</h3>
                                <p class="wrap">Receive notifications when grades are posted</p>
                            </a>
                        </kme:listItem>
                    </c:when>
                </c:choose>
            </c:forEach>

        </kme:listView>
    </kme:content>
</kme:page>
