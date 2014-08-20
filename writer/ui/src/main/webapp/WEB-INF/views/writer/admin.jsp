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
