<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
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


