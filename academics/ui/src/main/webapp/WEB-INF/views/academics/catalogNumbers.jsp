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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme:page title="${title}" id="academics-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics">
	<kme:content>
	<%@ include file="keywordSearch.inc.jsp" %>
		<kme:listView id="careerList" dataTheme="c" dataDividerTheme="b" filter="false">
			<c:forEach items="${catalogNumbers}" var="myCatalogNumber" varStatus="status">
				<kme:listItem><a href="${pageContext.request.contextPath}/academics/sections?termId=${currentTerm.id}&careerId=${currentCareer.id}&subjectId=${subjectId}&catalogNumber=${myCatalogNumber.number}">
					<h3>${subjectId} ${myCatalogNumber.number}</h3><p>${myCatalogNumber.description}</p>
				</a></kme:listItem>
			</c:forEach>
		</kme:listView>
	</kme:content>
</kme:page>
