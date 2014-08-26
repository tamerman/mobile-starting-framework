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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Calendar</title>
<link href="${pageContext.request.contextPath}/css/jquery.mobile-1.0b1.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/jquery.mobile.datebox.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/jquery-mobile-fluid960.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.1.min.js"></script>
<script type="text/javascript">
    $( document ).bind( "mobileinit", function(){ $.mobile.page.prototype.options.degradeInputs.date = 'text'; });	
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mobile-1.0b1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mobile.datebox.min.js"></script>
</head>

<body>
<div data-role="page" id="Calendar-Events-edit">
  <div data-role="header"><a href="${pageContext.request.contextPath}/calendar/options" data-icon="back" data-iconpos="notext">options</a>
    <h1>Event</h1>
    <a href="${pageContext.request.contextPath}/home" data-icon="home" data-iconpos="notext">home</a>
  </div>
  <div data-role="content" >
    <form:form action="${pageContext.request.contextPath}/calendar/selectFilter" commandName="filter" data-ajax="false">
        <fieldset>
            <label for="title">Filter by:</label>
             <form:select path="filterId" cssClass="ui-widget-content ui-corner-all" data-native-menu="false">
              	<form:option value="">---</form:option>
             	<form:options items="${filters}" itemLabel="filterName" itemValue="filterId" />
             </form:select>
        </fieldset>
        
        
        <div class="container_12">
        <div class="grid_6"><a href="${pageContext.request.contextPath}/calendar" data-role="button">Cancel</a></div>
        <div class="grid_6">   <input name="select" type="image" value="Select"  data-theme="a" src="${pageContext.request.contextPath}/images/btn-select.gif" alt="select" /></div>
        </div>
        
        
     
    </form:form>
  </div>







</div>
<!-- /page -->

</body>
</html>