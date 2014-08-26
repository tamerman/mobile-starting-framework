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

<spring:message code="campus.title" var="msgCat_ToolTitle"/>
<kme:page title="${msgCat_ToolTitle}" id="campus" backButton="true" homeButton="true">
	<kme:content>
		<kme:listView id="campuslist" filter="false">
			<c:forEach items="${campuses}" var="campus" varStatus="status">
				<kme:listItem>
					<c:url var="campusUrl" value="/campus/select">
						<c:param name="toolName" value="${toolName}" />
						<c:param name="campus" value="${campus.code}" />
					</c:url>
					<a href="${campusUrl}" onclick="saveDataToLocalStorage()"> <c:out value="${campus.name}" />
					<input type="hidden" name="campuscode" id="campuscode" value="${campus.code}"/>
					</a>
				</kme:listItem>
			</c:forEach>
		</kme:listView>
	</kme:content>

	<script type="text/javascript">
    	function saveDataToLocalStorage() {
    	    var myCampus = document.getElementById("campuscode").value;
    	    try {
    	        localStorage.setItem("myCampus", myCampus);
    	         myCampus.value = "";
    	    }
    	    catch (e) {
    	        if (e == QUOTA_EXCEEDED_ERR) {
    	            console.log("Error: Local Storage limit exceeds.");
    	        }
    	        else {
    	            console.log("Error: Saving to local storage.");
    	        }
    	    }
    	}
    </script>

</kme:page>


