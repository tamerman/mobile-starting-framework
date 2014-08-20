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