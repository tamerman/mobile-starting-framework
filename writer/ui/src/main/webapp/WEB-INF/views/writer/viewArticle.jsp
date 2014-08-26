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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="writer" uri="http://kuali.org/mobility/tags/writer" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="shared.dateTimeFormatFull" 	var="msgCat_DateTimeFormatFull"/>

<spring:message code="shared.yes" 					var="msgCat_Yes"/>
<spring:message code="shared.no" 					var="msgCat_No"/>
<spring:message code="shared.ok" 					var="msgCat_Ok"/>
<spring:message code="writer.confirmDeleteArticle"	var="msgCat_ConfirmDeleteArticle" />
<spring:message code="writer.articleDeleted"			var="msgCat_ArticleDeleted" />
<spring:message code="writer.articleDeleteFailed"	var="msgCat_ArticleDeleteFailed" />
<spring:message code="writer.articleDeleteFailedNoRight"	var="msgCat_ArticleDeleteFailedNoRight" />
<spring:message code="writer.articleNotFound"		var="msgCat_ArticleNotFound" />
<spring:message code="writer.title" var="msgCat_ToolTitle"/>
<spring:message code="writer.delete" var="msgCat_Delete"/>


<kme:page
    title="${msgCat_ToolTitle}"
	id="${toolInstance}-webapp" 
	backButton="true" 
	backButtonURL="${pageContext.request.contextPath}/writer/${toolInstance}"
	homeButton="true" 
	cssFilename="jquery.confirm,writer"
    theme="${theme}">

	<kme:content>

	<ul data-role="listview" data-inset="true" data-theme="c">
		<li data-role="fieldcontain" data-kme-hints="static">
				<c:if test="${allowDelete == true}">
				<div style="float: right">
					<a href="javascript:deleteArticle(${article.id});" style="border: none; background: red"
                       class="ui-btn-right" data-role="button" data-icon="delete" data-iconpos="notext"
                       title="${msgCat_Delete}"><spring:message code="writer.delete" /></a>
				</div>
				</c:if>
			<table style="border: 0px;">
				<tr>
					<td width="110px">
						<writer:articleImage article="${article}" wrapLink="true" instance="${toolInstance}" style="max-width: 80px; max-height: 80px;" />
					</td>
					<td valign="top">
						<table style="border: 0px">
							<tr><td><b class="wrap"><c:out value="${article.heading}" /></b></td></tr>
							<tr><td><c:out value="${article.journalist}" /></td></tr>
							<tr><td><fmt:formatDate pattern="${msgCat_DateTimeFormatFull}"  value="${article.timestamp}" /></td></tr>
						</table>
					</td>
				</tr>
			</table>
			</li>
			<li data-role="fieldcontain" data-kme-hints="static">
				<div class="linkUrl_ele"><c:out value="${article.text}" />
					<c:if test="${!empty article.linkUrl}" >
						<br/><br/>
						<spring:message code="writer.linkUrl"  />:&nbsp;<a href="javascript:;" id="linkUrl">${article.linkUrl}</a>
					</c:if>
				</div>
			</li>
			<c:if test="${article.video.id > 0}">
				<li data-role="fieldcontain">
					<div>
						<a href="${pageContext.request.contextPath}/writer/${toolInstance}/media/view/${article.video.id}" data-inline="true" data-role="button">
							<spring:message code="writer.video" />
						</a>
					</div>
				</li>
			</c:if>
		</ul>
		<ul data-role="listview" data-inset="true" data-theme="a">
			<li>
				<a href="${pageContext.request.contextPath}/writer/${toolInstance}/viewComments?articleId=${articleId}"data-inline="true">
					<span><spring:message code="writer.comments" /></span>
					<span class="ui-li-count">${noComments}</span>
				</a>
			</li>
		</ul>
	</kme:content>
    <script>
        var msgCat_Yes = "${msgCat_Yes}";
        var msgCat_No = "${msgCat_No}";
        var msgCat_Ok = "${msgCat_Ok}";
        var msgCat_ConfirmDeleteArticle = "${msgCat_ConfirmDeleteArticle}";
        var msgCat_ArticleDeleted = "${msgCat_ArticleDeleted}";
        var msgCat_ArticleDeleteFailed = "${msgCat_ArticleDeleteFailed}";
        var msgCat_ArticleDeleteFailedNoRight = "${msgCat_ArticleDeleteFailedNoRight}";
        var msgCat_ArticleNotFound = "${msgCat_ArticleNotFound}";
        var msgCat_LinkUrl = "${article.linkUrl}";
        var serverInstanceURL = window.kme.serverDetails.getServerPath() + "/writer/${toolInstance}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.confirm.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.article.view.js"></script>
</kme:page>
