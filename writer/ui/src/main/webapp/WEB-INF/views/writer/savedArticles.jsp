<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="writer" uri="http://kuali.org/mobility/tags/writer" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="shared.dateTimeFormatFull" var="msgCat_DateTimeFormatFull"/>
<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
        title="${msgCat_ToolTitle}"
	id="${toolInstance}-webapp" 
	backButton="true"
	backButtonURL="${pageContext.request.contextPath}/writer/${toolInstance}/admin"
	homeButton="true" 
	cssFilename="writer"
	jsFilename="writer"
    theme="${theme}">
	
	<kme:content>
		<c:choose>
			<c:when test="${fn:length(articles) > 0}">
				<!-- Start list of articles -->
				<kme:listView>
				<c:forEach var="article" items="${articles}">
					<spring:message code="${article.topic.label}" 	var="msgCat_TopicValue"/>
					<li id="article-${article.id}" data-icon="false" data-theme="c" >
					<a href="${pageContext.request.contextPath}/writer/${toolInstance}/editArticle/${article.id}">
						<div class="leftArticle">
							<writer:articleImage article="${article}" wrapLink="false" instance="${toolInstance}"/>
						</div>
						<div class="rightArticle">
							<h3 class="wrap"><c:out value="${article.heading}" /></h3>
							<p class="wrap">
								<b><spring:message code="writer.topic" />: </b><c:out value="${msgCat_TopicValue}" /><br>
								<b><spring:message code="writer.by" />: </b>${article.journalist}<br>
								<span><fmt:formatDate pattern="${msgCat_DateTimeFormatFull}"  value="${article.timestamp}" /></span>
								<c:if test="${article.rejection.id > 0}">
									<br><p class="wrap">
										<b><spring:message code="writer.reason" /></b>
										<c:out value="${article.rejection.reason}" /></p>
								</c:if>
							</p>
						</div>
					</a>
					</li>
				</c:forEach>
				</kme:listView>
				<!-- end list of articles -->
			</c:when>
			<c:otherwise>
				<h3><spring:message code="${emptyMessage}" /></h3>
			</c:otherwise>
		</c:choose>
	</kme:content>
</kme:page>
