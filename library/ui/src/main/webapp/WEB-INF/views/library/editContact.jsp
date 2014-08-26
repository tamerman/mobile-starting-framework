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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="library.title" var="msgCat_Title" />
<% pageContext.setAttribute("linefeed", "\n"); %> 

<kme:page  title="${msgCat_Title}"
	id="library-ui" 
	backButton="true" 
	homeButton="true"
	jsFilename="editContactDetailsScreen">
	<kme:content>
	
		<form id="editDetailsForm" action="${pageContext.request.contextPath}/library/editContact/${libraryId}" method="POST">
		
			<%-- Telephone number --%>
			<div data-role="fieldcontain">
				<label for="telephone" ><spring:message code="library.contact.telephone" /></label>
				<input type="tel" id="telephone" name="telephone" value="${contactDetail.telephone}">
			</div>
			
			<%-- Fax number --%>
			<div data-role="fieldcontain">
				<label for="fax"><spring:message code="library.contact.fax" /></label>
				<input type="tel" id="fax" name="fax" value="${contactDetail.fax}">
			</div>
			
			<%-- General Information desk number --%>
			<div data-role="fieldcontain">
				<label for="generalInfoDesk"><spring:message code="library.contact.general.info.desk" /></label>
				<input type="tel" id="generalInfoDesk" name="generalInfoDesk" value="${library.contactDetail.generalInfoDesk}">
			</div>
			
			<%-- Email --%>
			<div data-role="fieldcontain">
				<label for="email"><spring:message code="library.contact.email" /></label>
				<input type="email" id="email" name="email" value="${contactDetail.email}">
			</div>
			
			<%-- Postal Address --%>
			<div data-role="fieldcontain">
				<label for="postalAddress"><spring:message code="library.contact.postal.addr" /></label>
				<textarea id="postalAddress" name="postalAddress" rows="10" cols="20">${fn:replace(contactDetail.postalAddress, "<br/>", linefeed)}</textarea>
			</div>
			
			<%-- Physical Address --%>
			<div data-role="fieldcontain">
				<label for="physicalAddress"><spring:message code="library.contact.physical.addr" /></label>
				<textarea id="physicalAddress" name="physicalAddress" rows="10" cols="20">${fn:replace(contactDetail.physicalAddress, "<br/>", linefeed)}</textarea>
			</div>
			
			<%-- Latitude Address --%>
			<div data-role="fieldcontain">
				<label for="latitude"><spring:message code="library.contact.latitude" /></label>
				<input type="text" id="latitude" name="latitude" value="${contactDetail.latitude}"/>
			</div>
			
			<%-- Longitude Address --%>
			<div data-role="fieldcontain">
				<label for="longitude"><spring:message code="library.contact.longitude" /></label>
				<input type="text" id="longitude" name="longitude" value="${contactDetail.longitude}"/>
			</div>
			
		</form>
		
		<kme:listView dataInset="true" dataTheme="a" id="actionButtons">
			<kme:listItem><a href="#" id="btnSave"><spring:message code="library.save" /></a></kme:listItem>
			<kme:listItem dataIcon="back"><a href="${pageContext.request.contextPath}/library/viewContact/${libraryId}"><spring:message code="library.hours.cancel" /></a></kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>