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

<spring:message code="writer.isMandatory" var="msgCat_IsMandatory"/>
<spring:message code="writer.maximum" var="msgCat_Maximum"/>
<spring:message code="writer.minimum" var="msgCat_Minimum"/>
<spring:message code="writer.characters" var="msgCat_Characters"/>
<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
        title="${msgCat_ToolTitle}"
	id="${toolInstance}-webapp" 
	backButton="true"
	backButtonURL="${pageContext.request.contextPath}/writer/${toolInstance}/editArticle/${article.id}"
	homeButton="true" 
	cssFilename="writer"
    theme="${theme}">
	<kme:content>
		<form action="${pageContext.request.contextPath}/writer/${toolInstance}/rejectArticle" method="post" id="rejectForm">
			<input type="hidden" name="articleId" value="${article.id}" />
			<div data-kme-role="field-group">
				<h3><c:out value="${article.heading}" /></h3>
			
				<%-- Journalist the article is being rejected to --%>
				<div data-kme-role="fieldcontain">
					<label class="itemLabel"><spring:message code="writer.journalist" /></label>
					<label class="itemValue"><c:out value="${article.journalist}" /></label>
				</div>
				
				<%-- Reason for rejecting the article --%>
				<div data-kme-role="fieldcontain">
					<label for="reason"><spring:message code="writer.reason" /></label>
					<textarea name="reason" id="reason"></textarea>
				</div>
			</div>
		</form>
		<ul data-role="listview" data-inset="true" data-theme="a">
			<li data-icon="back"><a href="${pageContext.request.contextPath}/writer/${toolInstance}/editArticle/${article.id}" ><spring:message code="writer.cancel" /></a></li>
			<li data-icon="alert"><a href="javascript:;" id="rejectButton"><spring:message code="writer.reject" /></a></li>
		</ul>
	</kme:content>

    <script type="text/javascript" >
        // Strings for external scripts
        var validationMessages = {
            // Error messages for invalid reason
            reason: {
                required: 	"${msgCat_IsMandatory}",
                minlength: 	"${msgCat_Minimum} 2 ${msgCat_Characters}",
                maxlength: 	"${msgCat_Maximum} 500 ${msgCat_Characters}"
            }
        };
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.rejectArticle.js"></script>
</kme:page>