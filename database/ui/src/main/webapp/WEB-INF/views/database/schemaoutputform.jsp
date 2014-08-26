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

<kme:page title="database" id="database-webapp" backButton="true" homeButton="true" cssFilename="database" jsFilename="database">
	<kme:content>

<form:form action="${pageContext.request.contextPath}/database" commandName="form" data-ajax="false" method="post">
<fieldset>

<div data-role="fieldcontain">
	<label for="dialectType" class="select">Database:</label>
	<form:select data-theme="c" path="dialectType" multiple="false" items="${dialectTypes}" class="required"/>
	<form:errors path="dialectType"/>
</div> 

<div data-role="fieldcontain">
    <label for="delimiter">Delimiter:</label>
    <form:input path="delimiter" type="text" value="" />
</div>

<div data-role="fieldcontain">
    <label for="newLineBeforeDelimiter">New Line for Delimiter?:</label>
	<form:checkbox path="newLineBeforeDelimiter" id="newLineBeforeDelimiterCheckbox"/>
</div>

<div data-role="fieldcontain">
    <label for="removeForeignKeys">Remove foreign keys:</label>
	<form:checkbox path="removeForeignKeys" id="removeForeignKeysCheckbox"/>
</div>

<div data-inline="true">
    <div class="ui-grid-a">
        <div class="ui-block-a"><a href="${pageContext.request.contextPath}/database" data-theme="c"  data-role="button">Cancel</a></div>
        <div class="ui-block-b">
            <input data-theme="a" class="submit" type="submit" value="View" />
        </div>
        <div class="ui-block-c">
            <input data-theme="a" class="submit" type="submit" value="Download" name="download" />
        </div>
    </div>
</div>

</fieldset>
</form:form>

	</kme:content>
</kme:page>
