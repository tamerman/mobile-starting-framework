<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="writer.title" var="msgCat_ToolTitle"/>
<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
        title="${msgCat_ToolTitle}"
	id="${instance}-webapp" 
	backButton="true" 
	homeButton="false"
	loginButton="false" 
	cssFilename="writer"
	jsFilename="writer"
    theme="${theme}">


	<kme:content>
		<script>
		$(document).ready(function(){
			// If we are native this window will be running in a child browser
			if(isNative){
				var link = $("a[data-icon=back]").attr("href");
				$("a[data-icon=back]").attr("href", "../#close");
			}
		});
		
		</script>
		<div style="text-align: center">
			<img src="${pageContext.request.contextPath}/writer/${toolInstance}/media/${mediaId}" style="max-width: 100%">
		</div>
	</kme:content>
</kme:page>
