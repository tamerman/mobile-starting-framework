<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="writer.noResultsFound" var="msgCat_NoResultsFound"/>
<spring:message code="writer.loadMoreNews" 	var="msgCat_LoadMoreNews"/>
<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
	title="${msgCat_ToolTitle}"
	id="${toolInstance}-webapp" 
	backButton="true" 
	homeButton="true" 
	cssFilename="jquery.pagingWidget,writer"
    theme="${theme}">
	
	<kme:content>
		<div data-role="content" style="margin-top: -15px"><input type="search" id="searchText" /></div>
		<div data-role="content" id="searchResultsList" style="margin-left: -15px; margin-right: -15px;" ></div>
	</kme:content>

    <script>
        // Messages for external scripts
        var msgCat_noResultsFound 	= "${msgCat_NoResultsFound}";
        var msgCat_LoadMoreNews 	= "${msgCat_LoadMoreNews}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagingWidget.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.search.js"></script>
</kme:page>
