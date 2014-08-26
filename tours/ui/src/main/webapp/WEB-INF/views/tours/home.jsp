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
