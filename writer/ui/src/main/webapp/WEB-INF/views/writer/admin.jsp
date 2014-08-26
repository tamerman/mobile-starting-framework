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
<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
        title="${msgCat_ToolTitle}"
	id="${toolInstance}-webapp" 
	backButton="true" 
	backButtonURL="${pageContext.request.contextPath}/writer/${toolInstance}"
	homeButton="true" 
	cssFilename="writer"
    theme="${theme}">
	<kme:content>
		<ul data-role="listview" data-inset="true" data-theme="a">
			<c:if test="${(isEditor || isJournalist) == true }">
			<li data-icon="plus">
				<a href="${pageContext.request.contextPath}/writer/${toolInstance}/editArticle">
					<spring:message code="writer.createArticle" />
				</a>
			</li>
			</c:if>
			<li><a href="${pageContext.request.contextPath}/writer/${toolInstance}/savedArticles">
				<span><spring:message code="writer.savedArticles" /></span>
				<span class="ui-li-count">${noSavedArticles}</span>
			</a>
			
			</li>
			<c:if test="${isEditor == true }">
			<li><a href="${pageContext.request.contextPath}/writer/${toolInstance}/submittedArticles">
				<span><spring:message code="writer.submittedArticles" /></span>
				<span class="ui-li-count">${noSubmittedArticles}</span>
			</a></li>
			</c:if>
			<c:if test="${isJournalist == true }">
				<li data-icon="alert"><a href="rejectedArticles">
					<span><spring:message code="writer.rejectedArticles" /></span>
					<span class="ui-li-count">${noRejectedArticles}</span> 
				</a></li>
			</c:if>
		</ul>
	</kme:content>
</kme:page>
