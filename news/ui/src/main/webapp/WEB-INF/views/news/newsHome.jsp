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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="news.title" var="msgCat_ToolTitle"/>
<spring:message code="news.expand" var="msgCat_Expand"/>
<spring:message code="news.collapse" var="msgCat_Collapse"/>
<spring:message code="news.errorMsg" var="news_errorMsg"/>

<kme:multiPage title="${msgCat_ToolTitle}" id="news" homeButton="true" backButton="true" backButtonURL="${pageContext.request.contextPath}/home" cssFilename="news" jsFilename="news">
    <kme:childPage title="${msgCat_ToolTitle}" id="page0" homeButton="true" backButton="true" backButtonURL="${pageContext.request.contextPath}/home">
        <kme:content>
        <c:choose>
        <c:when test="${not empty newsSources}">
            <kme:listView dataTheme="c" dataDividerTheme="b" filter="false">
                <c:forEach items="${newsSources}" var="source" varStatus="status">
                    <kme:listItem>
                        <c:choose>
                            <c:when test="${empty source.children}">
                        		<a href="${pageContext.request.contextPath}/news/source?id=${source.id}">
                        			<c:choose>
                        				<c:when test="${source.title == null}">
                        					No Articles - Please try again later
                        				</c:when>
                        				<c:otherwise>
                        					${source.title}
                        				</c:otherwise>
                        			</c:choose>
                        		</a>
                            </c:when>
                            <c:otherwise>
                        		<a href="#page${source.id}" data-transition="none">
                        			<c:choose>
                        				<c:when test="${source.title == null}">
                        					No Articles - Please try again later
                        				</c:when>
                        				<c:otherwise>
                        					${source.title}
                        				</c:otherwise>
                        			</c:choose>
                        		</a>
                            </c:otherwise>
                        </c:choose>
                    </kme:listItem>
                </c:forEach>
            </kme:listView>
            </c:when>
            <c:otherwise>
            	<h2 align="center" style="color: red">${news_errorMsg}</h2>
            </c:otherwise>
            </c:choose>
        </kme:content>
    </kme:childPage>
    <c:forEach  items="${allNewsSources}" var="source" varStatus="status">
        <c:choose>
            <c:when test="${not empty source.children}">
                <kme:childPage title="${source.title}" id="page${source.id}" homeButton="true" backButton="true">
                    <kme:content>
                        <kme:listView dataTheme="c" dataDividerTheme="b" filter="false">
                            <c:forEach items="${source.children}" var="child" varStatus="childStatus">
                                <kme:listItem>
                                    <c:choose>
                                        <c:when test="${not empty child.children}">
                                    		<a href="#page${child.id}" data-transition="none">
                                    			<c:choose>
			                        				<c:when test="${child.title == null}">
			                        					No Articles - Please try again later
			                        				</c:when>
			                        				<c:otherwise>
			                        					${child.title}
			                        				</c:otherwise>
			                        			</c:choose>
                                    		</a>
                                        </c:when>
                                        <c:otherwise>
                                    		<a href="${pageContext.request.contextPath}/news/source?id=${child.id}">
                                    			<c:choose>
			                        				<c:when test="${child.title == null}">
			                        					No Articles - Please try again later
			                        				</c:when>
			                        				<c:otherwise>
			                        					${child.title}
			                        				</c:otherwise>
			                        			</c:choose>
                                    		</a>
                                        </c:otherwise>
                                    </c:choose>
                                </kme:listItem>
                            </c:forEach>
                        </kme:listView>
                    </kme:content>
                </kme:childPage>
            </c:when>
        </c:choose>
    </c:forEach>
</kme:multiPage>