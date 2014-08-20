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
	backButtonURL="${pageContext.request.contextPath}/writer/${toolInstance}/viewComments?articleId=${article.id}"
	homeButton="true" 
	cssFilename="writer"
    theme="${theme}">
	
	<kme:content>
		<form action="${pageContext.request.contextPath}/writer/${toolInstance}/addComment" method="post" id="commentForm">
		<input type="hidden" name="articleId" value="${article.id}" />
		
		<div data-kme-role="field-group">
			<h3><c:out value="${article.heading}" /></h3>
			
			<%-- Comment title --%>
			<div data-kme-role="fieldcontain">
				<label for="commentTitle"><spring:message code="writer.title.lbl" /></label>
				<input type="text" name="commentTitle" id="commentTitle" />
			</div>
			
			<%-- Comment text --%>
			<div data-kme-role="fieldcontain">
				<label for="commentText"><spring:message code="writer.comment" /></label>
				<textarea name="commentText" id="commentText"></textarea>
			</div>
		</div>
			
		<ul data-role="listview" data-theme="a" data-inset="true">
			<li><a id="btnPlaceComment" data-theme="a" href="javascript:;"><spring:message code="writer.placeComment" /></a></li>
		</ul>
		</form>
	</kme:content>

    <!-- Attach javascripts -->
    <script type="text/javascript">
        // Strings for external scripts
        var validationMessages = {
            // Error messages for invalid title
            commentTitle: {
                required: 	"${msgCat_IsMandatory}",
                minlength: 	"${msgCat_Minimum} 2 ${msgCat_Characters}",
                maxlength: 	"${msgCat_Maximum} 60 ${msgCat_Characters}"
            },
            // Error messages for invalid comment text
            commentText: {
                required: 	"${msgCat_IsMandatory}",
                minlength: 	"${msgCat_Minimum} 2 ${msgCat_Characters}",
                maxlength: 	"${msgCat_Maximum} 250 ${msgCat_Characters}"
            }
        };
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.addComment.js"></script>
</kme:page>
