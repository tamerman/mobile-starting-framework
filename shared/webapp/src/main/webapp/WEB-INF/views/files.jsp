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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<kme:page title="Files" id="file-webapp" backButton="true" homeButton="true" cssFilename="file" jsFilename="file">
	<kme:content>	
			<kme:listView id="fileslist" filter="false" dataTheme="c" dataInset="false">
				<li data-role="list-divider">${fileCount} Files</li>
		        <c:forEach items="${files}" var="file" varStatus="status">	            
	    	        <kme:listItem hideDataIcon="false">
						<a href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/files/get/${file.id}">
							<c:if test="${fn:contains(file.contentType,'image/')}">
								<img src="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/files/get/${file.id}" />
							</c:if>
							<h3>${file.fileName}</h3>
							<p><strong>${file.contentType}</strong></p>
							<p>${file.fileSize / 1000}KB</p>
							<p class="ui-li-aside">
								<strong>
									${file.postedTimestamp}
                  				</strong>
                  			</p>
						</a>
	            	</kme:listItem>
				</c:forEach>
			</kme:listView>              

	</kme:content>
</kme:page>
