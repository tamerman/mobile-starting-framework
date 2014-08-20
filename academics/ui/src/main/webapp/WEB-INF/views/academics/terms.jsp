<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="academics.title" var="title"/>
<spring:message code="academics.terms" var="terms"/>
<kme:page title="${terms}" id="academics-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics">
	<kme:content>
		<kme:listView id="termList" dataTheme="c" dataDividerTheme="b" filter="false">
			<c:forEach items="${term}" var="myTerm" varStatus="status">
				<kme:listItem><a href="${pageContext.request.contextPath}/academics/careers?termId=${myTerm.id}">${myTerm.description}</a></kme:listItem>
			</c:forEach>
		</kme:listView>
	</kme:content>
</kme:page>
