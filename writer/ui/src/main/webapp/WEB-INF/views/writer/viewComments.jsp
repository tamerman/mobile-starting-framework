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
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="writer.back" 				var="msgCat_Back"/>
<spring:message code="writer.confirmDeleteComment" var="msgCat_ConfirmDeleteComment"/>
<spring:message code="shared.yes" 				var="msgCat_Yes"/>
<spring:message code="shared.no" 				var="msgCat_No"/>
<spring:message code="shared.dateTimeFormatFull" var="msgCat_DateTimeFormatFull"/>
<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
    title="${msgCat_ToolTitle}"
	id="${toolInstance}-webapp" 
	backButton="true" 
	backButtonURL="${pageContext.request.contextPath}/writer/${toolInstance}/viewArticle?articleId=${articleId}"
	homeButton="true" 
	cssFilename="jquery.confirm,writer,writer.viewComments"
    theme="${theme}">
	
	<kme:content>

        <c:if test="${fn:length(comments) eq 0}">
		    <h3 id="noComments"><spring:message code="writer.noComments"/></h3>
        </c:if>
		<c:if test="${fn:length(comments) gt 0}">
			<!-- Start list of comments -->
			<c:forEach var="comment" items="${comments}">
				<kme:listView dataInset="true" id="comment_${comment.id}">
					<li data-role="list-divider" style="text-align: center" data-kme-hints="static">
						<span class="commentTitle"><c:out value="${comment.title}" /></span>
						<c:if test="${allowDeleteComment}">
							<span class="deleteComment"><a class="deleteComment" href="javascript:confirmDelete(${comment.id});" data-role="button" data-icon="delete" data-iconpos="notext" ></a></span>
						</c:if>
					</li>
					<li data-icon="hidden" data-kme-hints="static" data-theme="c">
							<c:choose>
								<c:when test="${fn:startsWith(comment.userDisplayName, 'writer.') == true}">
									<spring:message code="${comment.userDisplayName}" var="varDisplayName"/>
							 	</c:when>
								<c:otherwise>
									<c:set var="varDisplayName" value="${comment.userDisplayName}"/>
								</c:otherwise>
							</c:choose>
							<p><b><c:out value="${varDisplayName}" /></b></b> (<fmt:formatDate pattern="${msgCat_DateTimeFormatFull}"  value="${comment.timestamp}" />)</p>
						<p class="wrap"><c:out value="${comment.text}" /></p>
					</li>
				</kme:listView>
			</c:forEach>
		<!-- end list of comments -->
		</c:if>
		<c:if test="${allowPlaceComment == true }">
		<ul data-role="listview" data-inset="true" data-theme="a">
			<li>
				<a href="${pageContext.request.contextPath}/writer/${toolInstance}/addComment?articleId=${articleId}">
					<spring:message code="writer.placeComment" />
				</a>
			</li>
		</ul>
		</c:if>
	</kme:content>

    <script>
        // Messages
        var msgCat_ConfirmDeleteComment = "${msgCat_ConfirmDeleteComment}";
        var msgCat_Yes					= "${msgCat_Yes}";
        var msgCat_No					= "${msgCat_No}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.confirm.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.viewComments.js"></script>
</kme:page>
