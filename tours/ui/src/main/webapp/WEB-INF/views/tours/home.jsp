<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<kme:page title="Tours" id="tours" backButton="true" homeButton="true" cssFilename="tours">
    <kme:content>
        <kme:listView filter="false">
            <c:forEach items="${tours}" var="tour" varStatus="status">
                <kme:listItem>
                	<a href="${pageContext.request.contextPath}/tours/view/${tour.tourId}">
                		<c:if test="${not empty tour.imageUrl}">
                			<img src="${tour.imageUrl}"/>
                		</c:if>
                		<h3>${tour.name}</h3>
                		<c:if test="${not empty tour.distance}">
                			<fmt:formatNumber var="kilometers" value="${tour.distance / 1000}" maxFractionDigits="2" />
                			<fmt:formatNumber var="miles" value="${kilometers * 0.621371192}" maxFractionDigits="2" />
                			<p class="wrap">${miles} Miles / ${kilometers} Kilometers</p>
                		</c:if>
                		<c:if test="${not empty tour.description}">
                			<p class="wrap">${tour.description}</p>
                		</c:if>
               	 	</a>
                </kme:listItem>
            </c:forEach>
        </kme:listView>
    </kme:content>
</kme:page>
