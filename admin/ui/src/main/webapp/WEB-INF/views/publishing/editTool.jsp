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
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:message code="edittool.title" var="title"/>
<kme:page title="${title}" id="edittool" backButton="true" homeButton="true" cssFilename="publishing" backButtonURL="${pageContext.request.contextPath}/publishing/tool">
	<kme:content>
		<script type="text/javascript">
			$(function(){
				$('a.deleteButton').click(function() {
					if(!confirm('Are you sure you want to delete this tool?')) {
						return false;
					}
				});
				
			});
		</script>
		<form:form action="${pageContext.request.contextPath}/publishing/tool/edit" commandName="tool" data-ajax="false" method="post">
			<form:hidden path="toolId"/>
	    	<form:hidden path="versionNumber"/>
			<fieldset>
				<kme:listView id="layoutlist" dataTheme="c" dataDividerTheme="b" filter="false">
					<kme:listItem dataRole="list-divider">
						<spring:message code="edittool.presentation.title"/>
					</kme:listItem>
					<kme:listItem>
						<kme:localisedInput labelCode="edittool.tool.title" name="title" code="${tool.title}"/>
						<form:errors path="title" />
						<label for="subtitle"><spring:message code="edittool.tool.subtitle"/>:</label>
						<form:input path="subtitle" size="80" />
						<form:errors path="subtitle" />
						<label for="iconUrl"><spring:message code="edittool.tool.iconurl"/>:</label>
				   		<form:input path="iconUrl"  size="80" />
						<form:errors path="iconUrl" />
					</kme:listItem>
					<kme:listItem dataRole="list-divider">
						<spring:message code="edittool.link.title"/>
					</kme:listItem>
					<kme:listItem>
						<label for="url"><spring:message code="edittool.tool.url"/>:</label>
						<form:input path="url" size="80" />
						<form:errors path="url" />
					</kme:listItem>
					<kme:listItem dataRole="list-divider">
						<spring:message code="edittool.metadata.title"/>
					</kme:listItem>
					<kme:listItem>
						<kme:localisedInput labelCode="edittool.tool.description" name="description" code="${tool.description}" inputType="textarea"/>
						<form:errors path="description" />
						<label for="contacts"><spring:message code="edittool.tool.contacts"/>:</label>
				   		<form:input path="contacts"  size="80" />
						<form:errors path="contacts" />
						<label for="keywords"><spring:message code="edittool.tool.keywords"/>:</label>
				   		<form:input path="keywords"  size="80" />
						<form:errors path="keywords" />
						<label for="alias"><spring:message code="edittool.tool.alias"/>:</label>
						<form:input path="alias" size="80" />
						<form:errors path="alias" />
					</kme:listItem>
                    <kme:listItem dataRole="list-divider">
                        <spring:message code="edittool.tool.requirements"/>
                    </kme:listItem>		
                    <kme:listItem>
<script type="text/javascript">
var NATIVE 	= 1;
var IOS 	= 2;
var ANDROID = 4;
var WINDOWS = 8;
var BLACKBERRY	= 16;
$(function() {

	var requisites = $('#requisites').val();
	if((NATIVE & requisites) == NATIVE){
		console.log("NATIVE Checked");		
		$('#checkbox-native').prop('checked', true).checkboxradio("refresh");
	}else{
		console.log("NATIVE Unchecked");		
	}
	if((IOS & requisites) == IOS){
		console.log("IOS Checked");		
		$('#checkbox-ios').prop('checked', true).checkboxradio("refresh");
	}else{
		console.log("IOS Unchecked");				
	}
	if((ANDROID & requisites) == ANDROID){
		console.log("ANDROID Checked");		
		$('#checkbox-android').prop('checked', true).checkboxradio("refresh");
	}else{
		console.log("ANDROID Unchecked");				
	}
	if((WINDOWS & requisites) == WINDOWS){
		console.log("WINDOWS Checked");		
		$('#checkbox-windows').prop('checked', true).checkboxradio("refresh");
	}else{
		console.log("WINDOWS Unchecked");				
	}
	if((BLACKBERRY & requisites) == BLACKBERRY){
		console.log("BLACKBERRY Checked");		
		$('#checkbox-blackberry').prop('checked', true).checkboxradio("refresh");
	}else{
		console.log("BLACKBERRY Unchecked");				
	}

});

function CheckboxChanged(){
	var newRequisites = 0;
	if($('#checkbox-native').prop('checked')){
		newRequisites |= NATIVE;
	}
	if($('#checkbox-ios').prop('checked')){
		newRequisites |= IOS;
	}
	if($('#checkbox-android').prop('checked')){
		newRequisites |= ANDROID;
	}
	if($('#checkbox-windows').prop('checked')){
		newRequisites |= WINDOWS;
	}
	if($('#checkbox-blackberry').prop('checked')){
		newRequisites |= BLACKBERRY;
	}	
	$('#requisites').val(newRequisites);
	console.log("New Requisite is " + $('#requisites').val());
}


</script>                    
						<form:input path="requisites" type="hidden" value=""/>                    
					    <fieldset data-role="controlgroup">
					        <legend><spring:message code="edittool.tool.requisites"/>:</legend>
					        <input type="checkbox" name="checkbox-native" id="checkbox-native" onChange="CheckboxChanged()" value="1">
					        <label for="checkbox-native"><spring:message code="edittool.tool.native"/></label>
					        
					        <input type="checkbox" name="checkbox-ios" id="checkbox-ios" onChange="CheckboxChanged()" value="2">
					        <label for="checkbox-ios"><spring:message code="edittool.tool.ios"/></label>
					        
					        <input type="checkbox" name="checkbox-android" id="checkbox-android" onChange="CheckboxChanged()" value="4">
					        <label for="checkbox-android"><spring:message code="edittool.tool.android"/></label>
					        
					        <input type="checkbox" name="checkbox-windows" id="checkbox-windows" onChange="CheckboxChanged()" value="8">
					        <label for="checkbox-windows"><spring:message code="edittool.tool.windows"/></label>
					        
					        <input type="checkbox" name="checkbox-blackberry" id="checkbox-blackberry" onChange="CheckboxChanged()" value="16">
					        <label for="checkbox-blackberry"><spring:message code="edittool.tool.blackberry"/></label>
					    </fieldset>
                    </kme:listItem>		                    
                    <kme:listItem dataRole="list-divider">
                        <spring:message code="edittool.permissions"/>
                    </kme:listItem>					
                    <kme:listItem>
                        <label for="viewingPermission.expression"><spring:message code="edittool.viewing"/>:</label>
                        <form:textarea path="viewingPermission.expression" rows="8" cols="80" />
                        <form:errors path="viewingPermission.expression" />
                    </kme:listItem>
                    <kme:listItem>
                        <label for="publishingPermission.expression"><spring:message code="edittool.publishing"/>:</label>
                        <form:textarea path="publishingPermission.expression" rows="8" cols="80" />
                        <form:errors path="publishingPermission.expression" />
                    </kme:listItem>
					<kme:listItem>					
						<div data-inline="true">
		                	<div class="ui-grid-a">
		                    	<div class="ui-block-a">
		                    		<input data-theme="a" class="submit" type="submit" id="saveButton" value="<spring:message code="common.save"/>" />
		                    	</div>
		                    	<div class="ui-block-b">
		                    		<a data-theme="c"  href="${pageContext.request.contextPath}/publishing/tool" data-role="button"><spring:message code="common.cancel"/></a>                    		
		                    	</div>
		                	</div>
		                	<a class="deleteButton" href="${pageContext.request.contextPath}/publishing/tool/delete/${tool.toolId}" data-role="button"><spring:message code="tools.delete.title"/></a>
		            	</div>						
					</kme:listItem>
				</kme:listView>
			</fieldset>
		</form:form>		
	</kme:content>
</kme:page>


