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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:message code="writer.noNews" 	var="msgCat_NoNews"/>
<spring:message code="writer.loadMoreNews" var="msgCat_LoadMoreNews"/>
<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
        title="${msgCat_ToolTitle}"
	id="${toolInstance}-home" 
	backButton="${showHome}" 
	homeButton="${showHome}" 
	backButtonURL="${pageContext.request.contextPath}"
	preferencesButton="${showPreferences}"
	preferencesButtonURL="${pageContext.request.contextPath}/preferences"
	cssFilename="jquery.pagingWidget,writer,writer.news"
    theme="${theme}">
	
	<kme:content>

		<div data-role="content" id="toolbar">
			<div style="float: left">
			<select data-inline="true" data-theme="a" data-native-menu="false" id="topicSelect">
					<c:set var="sel" value="${0 == topicId ? 'selected=\"selected\"' : ''}" />
					<option ${sel} value="0"><spring:message code="writer.topStories" /></option>
					<c:forEach var="topic" items="${topics}">
						<spring:message code="${topic.label}" var="label"/>
						<c:set var="sel" value="${topic.id == topicId ? 'selected=\"selected\"' : ''}" />
						<option ${sel} value="${topic.id}"><c:out value="${label}" /></option>
					</c:forEach>
				</select>
			</div>
			<div data-role="controlgroup" data-type="horizontal" data-mini="true" id="tools">
			<a href="${pageContext.request.contextPath}/writer/${toolInstance}/searchArticle" data-role="button" data-icon="search" data-iconpos="notext" data-theme="a" data-inline="true" >
				<spring:message code="writer.search" />
			</a>
			<c:if test="${showAdmin == true }">
				<a href="${pageContext.request.contextPath}/writer/${toolInstance}/admin" data-role="button" data-icon="gear" data-iconpos="notext" data-theme="a" data-inline="true" >
					<spring:message code="writer.admin" />
				</a>
			</c:if>
			</div>
		</div>
		<!-- End navigation bars -->
		<div data-role="content" id="articlesList" ></div>
	</kme:content>

    <!-- Attach javascripts -->
    <script type="text/javascript">
        // Variables for javascripts
        var topicId = ${topicId};
        var msgCat_LoadMoreNews = "${msgCat_LoadMoreNews}";
        var msgCat_NoNews = "${msgCat_NoNews}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagingWidget.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.news.js"></script>
</kme:page>
