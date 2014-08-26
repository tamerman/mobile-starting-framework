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
