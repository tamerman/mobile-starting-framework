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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="tools.title" var="title"/>
<kme:page title="${title}" id="layout" backButton="true" homeButton="true" backButtonURL="${pageContext.request.contextPath}/publishing/index">
	<kme:content>
		<kme:listView id="layoutlist" dataTheme="c" dataDividerTheme="b" filter="false">
			<kme:listItem dataRole="list-divider">
				<spring:message code="tools.definition.creation"/>
			</kme:listItem>
			<kme:listItem>
				<a href="${pageContext.request.contextPath}/publishing/tool/new">
					<h3 class="wrap">
						<spring:message code="tools.new.title"/>
					</h3>
					<p class="wrap">
						<spring:message code="tools.new.instructions"/>
					</p>
				</a>
			</kme:listItem>
			<kme:listItem dataRole="list-divider">
				<spring:message code="tools.existing"/>
			</kme:listItem>
			<c:forEach items="${tools}" var="tool" varStatus="status">
				<kme:listItem>
					<a href="${pageContext.request.contextPath}/publishing/tool/edit/${tool.toolId}">
						<h3 class="wrap">
							<spring:message code="${tool.title}"/>
						</h3>
					</a>	
				</kme:listItem>
			</c:forEach>
		</kme:listView>
	</kme:content>
</kme:page>