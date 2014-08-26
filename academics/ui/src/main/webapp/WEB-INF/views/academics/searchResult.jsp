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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="academics.searchResult" var="title"/>
<kme:page title="${title}" id="sections-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics">
    <kme:content>           
        <kme:listView id="sectionlist" dataTheme="c" dataDividerTheme="b" filter="false">
            <c:if test="${not empty searchResult.messages}">
                <c:forEach var="message" items="${searchResult.messages}">
                    <c:choose>
                        <c:when test="${message.typeCode==3}">
                            <kme:listItem hideDataIcon="true" cssClass="yellowNotifaction">
                                ${message.description}
                            </kme:listItem>
                        </c:when>
                        <c:otherwise>
                            <kme:listItem hideDataIcon="true" cssClass="redNotification">
                                ${message.description}                              
                            </kme:listItem>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
            
            <c:if test="${not empty searchResult.sections}">
                <c:forEach var="thisSection" items="${searchResult.sections}">
                    <kme:listItem>
                            <a href="${pageContext.request.contextPath}/${toolContext}/sectionsDetail?sectionUID=${thisSection.sectionUID}">
                                    <h3><c:out value="${thisSection.subjectId}"/>&nbsp;<c:out value="${thisSection.catalogNumber}"/>&nbsp;<c:out value="${thisSection.number}"/>&nbsp;<c:out value="${thisSection.type}"/></h3>
                                    <c:if test="${not empty thisSection.courseTitle}">
                                            <p class="academics-courseDescription">${thisSection.courseTitle}</p>
                                    </c:if>
                                    <c:if test="${not empty thisSection.classTopic}">
                                            <p class="academics-courseDescription">${thisSection.classTopic}</p>
                                    </c:if>
                                    <c:choose>
                                            <c:when test="${fn:toLowerCase(thisSection.enrollmentStatus) == 'open'}">
                                                    <p><span class="academics-sectionSeats font-green"><c:out value="${thisSection.availableSeats}"/>  <c:out value="${thisSection.enrollmentStatus}"/></span></p>
                                            </c:when>
                                            <c:when test="${fn:toLowerCase(thisSection.enrollmentStatus) == 'closed'}">
                                                    <p><span class="academics-sectionSeats font-red"><c:out value="${thisSection.enrollmentStatus}"/></span></p>
                                                    </c:when>
                                                    <c:when test="${fn:toLowerCase(thisSection.enrollmentStatus) == 'wait list'}">
                                                    <p><span class="academics-sectionSeats font-orange"><c:out value="${thisSection.enrollmentStatus}"/></span></p>
                                            </c:when>
                                   </c:choose>
                                   <c:if test="${fn:length(thisSection.meetings) == 0}">
                                            <p><span class="academics-sectionLeft">Time</span>
                                                    <span class="academics-sectionRight">TBA</span>
                                   </c:if>
                                   <c:if test="${fn:length(thisSection.meetings) > 0}">
                                            <p><span class="academics-sectionLeft">Time</span>
                                                    <span class="academics-sectionRight">
                                                            <c:forEach items="${thisSection.meetings}" var="meeting" varStatus="ctrMts">
                                                                    <c:choose>
                                                                            <c:when test="${ctrMts.index eq 1 && fn:length(thisSection.meetings) gt 2}">
                                                                                    <c:set var="moreText" value="and ${fn:length(thisSection.meetings) - 2} more" />
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                    <c:set var="moreText" value="" />
                                                                            </c:otherwise>
                                                                    </c:choose>
                                                                <c:if test="${ctrMts.index lt 2}">
                                                                    <c:choose>
                                                                        <c:when test="${fn:toUpperCase(meeting.days)=='TBA'}">TBA</c:when>
                                                                        <c:otherwise>
                                                                            ${meeting.days}&nbsp;${meeting.times}&nbsp${moreText}<br/>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:if>
                                                            </c:forEach>
                                                    </span></p>
                                            <c:if test="${not empty thisSection.meetings[0].instructors}" >
                                                <p><span class="academics-sectionLeft">Instructor</span>
                                                <span class="academics-sectionRight">
                                                    <c:forEach var="instructor" items="${thisSection.meetings[0].instructors}">
                                                        <c:out value="${fn:replace(instructor,',',', ')}"/><br/>
                                                    </c:forEach>
                                                </span></p>        
                                            </c:if>     
                                                    
                                    </c:if>
                                      
                            </a>
                    </kme:listItem>
		</c:forEach>
            </c:if>
	
        </kme:listView>
    </kme:content>
</kme:page>