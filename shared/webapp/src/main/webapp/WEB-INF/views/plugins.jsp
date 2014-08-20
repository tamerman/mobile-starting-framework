<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(header['User-Agent'],'iPhone') || fn:contains(header['User-Agent'],'iPad') || fn:contains(header['User-Agent'],'iPod') || fn:contains(header['User-Agent'],'Macintosh')}">
	<c:set var="platform" value="iOS"/>
</c:if>
<c:if test="${fn:contains(header['User-Agent'],'Android')}">
	<c:set var="platform" value="Android"/>
</c:if>

<c:set var="phonegap" value="${cookie['phonegap'].value}"/>


<kme:page title="Plugins" id="plugin" backButton="true" homeButton="true" cssFilename="" backButtonURL="${pageContext.request.contextPath}/home" platform="${platform}" phonegap="${phonegap}">
<script type="text/javascript" charset="utf-8">

document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    showAlert();
}

function showAlert() {
    console.log("showAlert()");	
    navigator.notification.alert(
        'navigator.notification.alert() works!',
        function(){},
        'Plugin Test',
        'Ok');
}

</script>	
	<kme:content>
	    <kme:listView id="list" filter="false">
           	<li data-icon="false" data-theme="c">                
	           	<h3>Native Info</h3>
    	       	<p style="white-space:normal">Platform: ${platform}</p>
    	       	<p style="white-space:normal">Phonegap: ${phonegap}</p>
    	       	<p style="white-space:normal">Native: ${cookie['native'].value}</p>
           	</li>	
           	<li data-icon="false" data-theme="c">                
				<a href="#" data-ajax="false" onClick="showAlert();">
		           	<h3>Alert</h3>
				</a>
           	</li>	



	    </kme:listView>
	</kme:content>

</kme:page>