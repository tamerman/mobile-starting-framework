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

<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme"  uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="people.search" var="msgCat_Search"/>
<spring:message code="people.peopleLabel" var="msgCat_People"/>
<kme:page title="${msgCat_People}" id="people" backButton="true" homeButton="true" cssFilename="people" backButtonURL="${pageContext.request.contextPath}/home" appcacheFilename="iumobile.appcache">
	<kme:content>
		<form:form action="${pageContext.request.contextPath}/people" commandName="search" data-ajax="false" method="post">
			<div data-role="fieldcontain">
				<label for="lastName"<spring:message code="people.lastName" />:</label>
                <form:input path="lastName" type="text" value="" />
                <div class="error"><form:errors path="lastName"/></div>
                
                <div id="peopleSlider">
                <fieldset data-theme="c" data-role="controlgroup" data-type="horizontal" >
	                <label for="slider"></label>
	                <form:select data-theme="c" path="exactness" id="slider" data-role="slider">
						<form:option data-theme="c" value="starts" label="starts with" />
				        <form:option data-theme="c" value="exact" label="is exactly" />
	                </form:select>
                </fieldset>
                </div>
                
				<label for="firstName"><spring:message code="people.firstName" />:</label>
	            <form:input path="firstName" type="text" value=""  />
			</div>
			
			<div data-role="fieldcontain">
                <form:select data-theme="c" path="status" multiple="false" items="${statusTypes}" />
                <form:select data-theme="c" path="location" multiple="false" items="${locations}" />
			</div> 
               
			<div data-role="fieldcontain">
                <label for="userName"><spring:message code="people.username" />:</label>
                <form:input path="userName" type="text" value="" />
			</div>
            
			<div data-inline="true">
                <div class="ui-grid-a">
                    <div class="ui-block-a"><a data-theme="c"  href="${pageContext.request.contextPath}" data-role="button"><spring:message code="people.cancel" /></a></div>
                    <div class="ui-block-b">
                        <input data-theme="a" class="submit" type="submit" value="${msgCat_Search}" />
                    </div>
                </div>
            </div>
        </form:form>
	</kme:content>
</kme:page>
