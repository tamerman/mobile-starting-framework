<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational
  Community License, Version 2.0 (the "License"); you may not use this file
  except in compliance with the License. You may obtain a copy of the License
  at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:message code="campus.title" var="msgCat_Title"/>
<spring:message code="computerlabs.noLabInfo" var="msgCat_NoLabInfo"/>

<kme:page title="${msgCat_Title}" id="campus" homeButton="true" backButton="true" cssFilename="computerlabs">
	<kme:content>
		<kme:listView id="campuslist" dataTheme="c" dataDividerTheme="b" filter="false">
 			<c:choose>
			<c:when test="${not empty labGroups}">
					<c:forEach items="${labGroups}" var="current">
				    	<c:url var="url" value="computerlabs/list">
				    	   <c:param name="groupId" value="${current.name}"></c:param>
					   </c:url>
					   <li data-theme="c">
				          <a href="${url}"><h3>${current.name}</h3></a>
					   </li>
			        </c:forEach>
            </c:when>
			<c:otherwise>
    		     <kme:listItem>
				  ${msgCat_NoLabInfo}
			    </kme:listItem>
			</c:otherwise>
			</c:choose>
		</kme:listView>
	</kme:content>
</kme:page>
