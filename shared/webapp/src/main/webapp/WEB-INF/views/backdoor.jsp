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

<spring:message code="backdoor.title" var="msgCat_ToolTitle"/>
<spring:message code="backdoor.submit" var="msgCat_Submit"/>


<kme:page title="${msgCat_ToolTitle}" id="backdoor" backButton="true" homeButton="true" backButtonURL="home">
	<kme:content>
		<form:form action="${pageContext.request.contextPath}/backdoor" commandName="backdoor" data-ajax="false" method="post">
		      <c:if test="${empty backdoor.userId}">
			  <fieldset>
				<div data-role="fieldcontain">
					<label for="userId"><spring:message code="backdoor.username" />:</label>
			        <form:input path="userId" /><br/>
			        <form:errors path="userId" />
				</div>
		      </fieldset>
		      <div data-inline="true">
		        <div class="ui-grid-a">
		          <div class="ui-block-a"><a data-theme="c" href="${pageContext.request.contextPath}/home" data-role="button"><spring:message code="backdoor.cancel" /></a></div>
		          <div class="ui-block-b"><input data-theme="a" class="submit" type="submit" value="${msgCat_Submit}" /></div>
		        </div>
		      </div>
		      </c:if>
		      <c:if test="${not empty backdoor.userId}">
	          <a data-theme="c" href="${pageContext.request.contextPath}/backdoor/remove" data-role="button"><spring:message code="backdoor.removebackdoor" /></a>
	          </c:if>
	    </form:form>
	</kme:content>
</kme:page>


