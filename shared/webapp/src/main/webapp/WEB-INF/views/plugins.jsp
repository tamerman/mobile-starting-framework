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