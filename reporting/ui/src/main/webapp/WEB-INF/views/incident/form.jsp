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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="reporting.incident.title" var="msgCat_ToolTitle"/>
<spring:message code="reporting.incident.call" var="msgCat_Call"/>
<spring:message code="reporting.incident.trueEmergency" var="msgCat_TrueEmergency"/>
<spring:message code="reporting.incident.camera" var="msgCat_Camera"/>
<spring:message code="reporting.incident.album" var="msgCat_Album"/>
<spring:message code="reporting.incident.summary" var="msgCat_Summary"/>
<spring:message code="reporting.incident.file" var="msgCat_File"/>
<spring:message code="reporting.incident.email" var="msgCat_Email"/>
<spring:message code="reporting.incident.affiliation" var="msgCat_Affiliation"/>
<spring:message code="reporting.incident.contactMe" var="msgCat_ContactMe"/>
<spring:message code="reporting.incident.followUp" var="msgCat_FollowUp"/>
<spring:message code="reporting.affiliation.student" var="msgCat_Student"/>
<spring:message code="reporting.affiliation.faculty" var="msgCat_Faculty"/>
<spring:message code="reporting.affiliation.staff" var="msgCat_Staff"/>
<spring:message code="reporting.affiliation.other" var="msgCat_Other"/>
<spring:message code="reporting.incident.anonymous" var="msgCat_Anonymous" />
<spring:message code="reporting.incident.reportIncident" var="msgCat_ReportIncident" />
<spring:message code="reporting.incident.submit" var="msgCat_Submit"/>
<spring:message code="shared.no" var="msgCat_No"/>


<c:set var="native" value="${cookie['native'].value}"/>

<kme:page title="${msgCat_ToolTitle}" id="incident" backButton="true" homeButton="true" cssFilename="incident">

	<script type="text/javascript">
    var pictureSource;   // picture source
    var destinationType; // sets the format of returned value 

    // Wait for PhoneGap to connect with the device
    //
    document.addEventListener("deviceready",onDeviceReady,false);

    // PhoneGap is ready to be used!
    function onDeviceReady() {
    	//alert("OnDeviceReady");
		// Setup variable names shorter than the stock phonegap ones.  
        pictureSource	= navigator.camera.PictureSourceType;
        destinationType	= navigator.camera.DestinationType;
    }

    // Called when a photo is successfully retrieved
    function onPhotoURISuccess(imageURI) {
		console.log(imageURI);
		uploadPhoto(imageURI);
		var largeImage = document.getElementById('largeImage');
		console.log("largeImage:" + largeImage);
		largeImage.src = imageURI;
		console.log("post" + largeImage.src);
    }

    function uploadPhoto(imageURI) {
    	console.log("Attemping to Upload File: " + imageURI);
    	var options = new FileUploadOptions();
        options.fileKey="file";											// This is the form element name, had this been submitted by an
        																// 		actual form. 
        options.fileName=imageURI.substr(imageURI.lastIndexOf('/')+1);	// FileName
        options.mimeType="image/jpeg";									// mimeType

        var params = new Object();
        params.value1 = "test";
        params.value2 = "param";

        options.params = params;

		var server	= "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
        var save 	= server + "/files/save"; 
        var get 	= server + "/files/get/";
        
        var ft = new FileTransfer();
        ft.upload(	imageURI, 
        			save, 
        			function(r){
        	            var response = jQuery.parseJSON(r.response);
        				var largeImage = document.getElementById('largeImage');
        				var fileId = document.getElementById('fileId');
        				largeImage.src = get + response.fileid;
						largeImage.style.display = 'block';
        			}, 
        			function(r){
        	            alert("An error has occurred: Code = " + error.code);
        	            console.log("upload error source " + error.source);
        	            console.log("upload error target " + error.target);
        			}, 
        			options);
    }

    // A button will call this function
    function getPhoto(source) {
		navigator.camera.getPicture(onPhotoURISuccess, onFail, {quality: 50, 
																destinationType: destinationType.FILE_URI,
																sourceType: source, 
																targetWidth: 420,
																targetHeight: 420});

    }
    
    // Called if something bad happens or fail silently...
    function onFail(message) {
		alert('Failed because: ' + message);
    }
   </script>

    <kme:content>
    	<kme:listView>
		    <kme:listItem cssClass="link-phone">
			    <a href="tel:911">
			    	<h3>${msgCat_Call} 911</h3>
			    	<p class="wrap">${msgCat_TrueEmergency}</p>
			    </a>
		    </kme:listItem>
		    <kme:listItem dataRole="list-divider">
		    	${msgCat_ReportIncident}
		    </kme:listItem>
			<kme:listItem>
		        <form:form action="${pageContext.request.contextPath}/reporting/incidentPost" commandName="incident" data-ajax="false" method="post"> 
					<form:hidden path="userAgent" value="${header['User-Agent']}"/>
					
		            <fieldset>            
		                <label style="margin-top:10px; font-weight:normal; font-size:14px;" for="summary">${msgCat_Summary}:</label>
		                <form:textarea path="summary" cols="40" rows="8" class="required" />
		                <form:errors path="summary" />


						<c:if test="${cookie['native'].value == 'yes'}">
							<fieldset class="ui-grid-a">
								<legend>Photo</legend>
									<div class="ui-block-a"><button onclick="getPhoto(pictureSource.CAMERA);return false;">${msgCat_Camera}</button></div>
				    				<div class="ui-block-b"><button onclick="getPhoto(pictureSource.PHOTOLIBRARY);return false;">${msgCat_Album}</button></div>
							</fieldset>
							<img style="border:1px solid black; display:none; width:100px; height:100px;" id="largeImage" src="" />
						</c:if>
						<c:if test="${fn:contains(header['User-Agent'],'Windows') || fn:contains(header['User-Agent'],'Macintosh')}">
							<label style="margin-top:10px; font-weight:normal; font-size:14px;" for="file">${msgCat_File}:</label>
							<input type="file" name="somename" size="40"> 
						</c:if>

					    											    						
		                <label style="margin-top:10px; font-weight:normal; font-size:14px;" for="email">${msgCat_Email}:</label>
		                <form:input path="email" type="text" value="" placeholder="${msgCat_Anonymous}" class="email" />
						
						<label style="margin-top:10px; font-weight:normal; font-size:14px;" for="affiliation">&nbsp;<br/>${msgCat_Affiliation}:</label>
			            <fieldset data-role="controlgroup" data-theme="c">
			    	    					
				            <form:checkbox data-theme="c" path="affiliationStudent" value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Student}" />
				            <form:checkbox data-theme="c" path="affiliationFaculty" value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Faculty}" />
				            <form:checkbox data-theme="c" path="affiliationStaff"   value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Staff}" />
				            <form:checkbox data-theme="c" path="affiliationOther"   value="YES" style="left:0; width:25px; height:25px;" label="${msgCat_Other}" />
						</fieldset>
						
						<label style="margin-top:10px; font-weight:normal; font-size:14px;" for="contactMe">${msgCat_ContactMe}:</label>
			            <fieldset data-role="controlgroup" data-theme="c">			    	    					
			                <form:radiobutton data-theme="c" path="contactMe" value="true" label="${msgCat_FollowUp }" />
			                <form:radiobutton data-theme="c" path="contactMe" value="false"  label="${msgCat_No}" />
						</fieldset>
		            </fieldset>
		            
		            <div data-inline="true">
		            	<input data-theme="a" class="submit" type="submit" value="${msgCat_Submit}" />
		            </div>
		        </form:form>
	        </kme:listItem>
        </kme:listView>
    </kme:content>
</kme:page>	
