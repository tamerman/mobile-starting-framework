<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
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


