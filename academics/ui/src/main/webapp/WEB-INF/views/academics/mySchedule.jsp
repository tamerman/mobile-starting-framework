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
