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