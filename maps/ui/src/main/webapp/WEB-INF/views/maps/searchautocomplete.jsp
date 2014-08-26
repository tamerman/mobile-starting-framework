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

<spring:message code="maps.searchNoResultsMessage" var="searchNoResultsMessage"/>

<kme:listView id="mapsearchresults" dataTheme="c" dataInset="true" dataDividerTheme="b" filter="false">
	<c:forEach items="${container.results}" var="item" varStatus="status">
	<kme:listItem>
	<script type="text/javascript">	
	$(function(){
		var location = JSON.parse('${item.info}');
		for (point in location) {
		    if (location[point]==="null" || location[point]===null || location[point]==="" || typeof location[point] === "undefined"){
		    	location[point] = " ";
		    }		    	
		}
		
		var info =	"<div style='width: 200px; height: 150px; overflow: auto'> " +
					"  <div style='align: center'> " +
					"    <b>" + location.name + "</b> " +
					"    <p>" + location.streetNumber + " ," + location.streetDirection + " ," + location.street + "</p>" +
					"    <p>" + location.city + " ," + location.state + " " + location.zip + "</p>" +
					"  </div> " +
					" </div> ";
		
		$('#${item.code}').attr("kmeinfo",info);
	 });
	</script>
	
	<a id="${item.code}" href="#" kmetype="quicksearch" kmecode="${item.code}" kmelatitude="${item.latitude}" kmelongitude="${item.longitude}" kmename="${item.name}" kmeinfo=""><p class="wrap">${item.name}</p></a>
	</kme:listItem>
	</c:forEach>
	<c:if test="${empty container.results}">
		<kme:listItem>${searchNoResultsMessage}</kme:listItem>
	</c:if>
</kme:listView>