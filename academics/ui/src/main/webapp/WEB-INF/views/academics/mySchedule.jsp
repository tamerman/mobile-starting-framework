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

<spring:message code="academics.terms" var="terms"/>
<kme:page title="${terms}" id="academics-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics" backButtonURL="${backButtonURL}">
	<kme:content>
            <c:choose>
                <c:when test="${empty term}" >
                    <kme:listView id="termList" dataTheme="c" dataDividerTheme="b" filter="false">
                        <kme:listItem><h3 class="ui-li-heading wrap">You are not registered for classes in any current terms.</h3></kme:listItem>
                    </kme:listView>
                </c:when>
                <c:otherwise>
                    <kme:listView id="termList" dataTheme="c" dataDividerTheme="b" filter="false">             
			<c:forEach items="${term}" var="myTerm" varStatus="status">
                            <c:choose>
                                <c:when test="${empty myTerm.careers}" >
                                    <kme:listView id="termList" dataTheme="c" dataDividerTheme="b" filter="false">
                                        <kme:listItem><h3 class="ui-li-heading wrap">You are not registered for classes in ${myTerm.description}.</h3></kme:listItem>
                                    </kme:listView>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${myTerm.careers}" var="myCareer" varStatus="innerStatus">
                                        <kme:listItem><a href="${pageContext.request.contextPath}/myAcademics/mySections?termId=${myTerm.id}&careerId=${myCareer.id}">
                                                <h3 class="ui-li-heading">${myTerm.description}</h3><p class="wrap ui-li-desc">${myCareer.description}</p>
                                            </a></kme:listItem>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
			</c:forEach>
                    </kme:listView>
                </c:otherwise>
            </c:choose>

	</kme:content>
</kme:page>
