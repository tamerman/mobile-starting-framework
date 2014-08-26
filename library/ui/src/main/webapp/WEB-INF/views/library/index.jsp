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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="library.title" var="msgCat_Title" />
<kme:page  title="${msgCat_Title}"
	id="library-ui" 
	backButton="true" 
	homeButton="true" 
	cssFilename="library">
	<kme:content>
		 <kme:listView dataDividerTheme="b" filter="false" id="menu">
			<kme:listItem hideDataIcon="true">
			  <a href="${pageContext.request.contextPath}/library/viewHours" style="background-image: url('${pageContext.request.contextPath}/images/academics/browse-classes.png');">
					<h3><spring:message code="library.viewHours" /></h3>
					<p class="wrap"><spring:message code="library.viewHours.subtitle" /></p>
			  </a>
			</kme:listItem>
			<kme:listItem hideDataIcon="true">
				<a href="${pageContext.request.contextPath}/library/viewContact" style="background-image: url('${pageContext.request.contextPath}/images/academics/browse-classes.png');">
					<h3><spring:message code="library.contact" /></h3>
					<p class="wrap"><spring:message code="library.contact.subtitle" /></p>
				</a>
			</kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>
