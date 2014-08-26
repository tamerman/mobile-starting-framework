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
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="events.title.byRange" var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="events" backButton="true" homeButton="true">
    <kme:content>
        <kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">
            <c:choose>
                <c:when test="${!empty errorMsg}">
                    <kme:listItem><h3 class="wrap">${errorMsg}</h3></kme:listItem>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${empty events}">
                            <kme:listItem><h3 class="wrap"><spring:message code="events.noEvents" arguments="${category.title}"/></h3></kme:listItem>
                        </c:when>
                        <c:otherwise>
                            <c:set var="lastDate" value="NULL" />
                            <c:forEach items="${events}" var="event" varStatus="status">
                                <c:if test="${lastDate != event.displayStartDate}">
                                    <c:set var="lastDate" value="${event.displayStartDate}" />
                                    <kme:listItem dataTheme="b" dataRole="list-divider">
                                        <fmt:formatDate value="${event.startDate}" pattern="EEEE MMMM d, yyyy" />
                                    </kme:listItem>
                                </c:if>
                                <kme:listItem>
                                    <c:url var="url" value="/events/viewEvent">
                                        <c:param name="categoryId" value="${category.categoryId}"></c:param>
                                        <c:param name="campus" value="${campus}"></c:param>
                                        <c:param name="eventId" value="${event.eventId}"></c:param>
                                    </c:url>
                                    <a style="padding-right: 25px !important;" href="${url}">
                                        <h3 class="wrap">
                                            <c:out value="${event.title}" />
                                        </h3>
                                        <p class="wrap">
                                            <fmt:formatDate value="${event.startDate}" pattern="EEEE MMMM d, yyyy @ h:mm a" />
                                        </p>
                                    </a>
                                </kme:listItem>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </kme:listView>
    </kme:content>
</kme:page>