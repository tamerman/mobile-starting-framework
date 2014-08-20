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
