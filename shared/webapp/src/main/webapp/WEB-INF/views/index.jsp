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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="mdot.pageTitle" var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="home" 
		cssFilename="home,${currentLayout}Home"
		jsFilename="store+json2.min" 
		backButton="false" 
		homeButton="false" 
		preferencesButton="true" 
		preferencesButtonURL="preferences" 
		platform="${param.platform}" 
		jqmHeader="${param['header']}" 
		phonegap="${param.phonegap}">
		
	<kme:content>
		<script type="text/javascript">
			header = $.cookie('jqmHeader');
			if(header == "" || header == null){
				$.cookie('jqmHeader', '${param["header"]}', {expires: 365, path: '/'});
			}
		</script>

		<script type="text/javascript">

			if( store.enabled ) {
				if(!store.get("defaultCampus")){
					store.set("defaultCampus","${defaultCampus}");
				}
			} else {
				// do fall back
			}
		
			// Must call after DOM is ready.
		    // This is shortcut for $(document).ready(...);
		    $(function(){
/* 		    	var list = $("<p>").append($("#homeserviceslist").clone()).html();
				list = list.find('ul').replaceWith("<ul>" + $(this).html() + "</ul>");
		    	console.log(listPage);
		    	$("#homeserviceslist").empty().listview("refresh");
 */
 				document.addEventListener("deviceready",onDeviceReady,false);
		    });


/* 		    $('#homeserviceslist li').live('click', function() {
				var href = $(this).find('a').attr('href');
		    	alert(href); // id of clicked li by directly accessing DOMElement property
		    });
 */
			function onDeviceReady(){
				//navigator.notification.alert('Did onDeviceReady()', function(){}, 'IU Mobile', 'OK');
			}
		</script>

		<!-- <p><a id="manualUpdate" href="#">Check for an updated Cache</a></p> -->
		<div id="cacheProgressModal">
			<div id="cacheProgressMessage">
			<h3><spring:message code="mdot.appCache.message" /></h3>
			<p><span id="cacheProgress">&nbsp;</span></p>

			<input id="reloadButton" type="button" value="Reload" onClick="window.location.reload()">

			<!-- <ul id="applicationEvents">
			</ul> -->
			</div>
	 	</div>
 		<c:set var="bCount" value="0"/>

		<%-- Include the required home layout --%>
		<jsp:include page="${currentLayout}Home.jsp">
			<jsp:param value="${tools}" name="tools"/>
		</jsp:include>
		
	    <c:if test="${not empty ipAddress}">${ipAddress}</c:if>

	</kme:content>

	<script type="text/javascript">
		function otherDeviceReadyFunctions(){
			window.plugins.badge.set(${bCount});
		}
	</script>

	<c:if test="${showCacheUpdate == true}">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/appcacheMonitor.js"></script>
	</c:if>
</kme:page>


