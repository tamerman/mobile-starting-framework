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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:message code="computerlabs.title" var="msgCat_ToolTitle"/>
<spring:message code="computerlabs.seats" var="msgCat_Seats"/>
<spring:message code="computerlabs.noLabsFound" var="msgCat_NoLabsFound"/>

<kme:page title="${pageTitle == null ? msgCat_ToolTitle : pageTitle}" id="computerlabs" homeButton="true" backButton="true" cssFilename="computerlabs">
	<kme:content>
	
	
	<script type="text/javascript">
            function adjustSreenSize() {
                screenHeight = window.screen.availHeight;
                document.getElementById('googledocsURL').height = screenHeight;
            }
        </script>

        <c:choose>
            <c:when test="${feedStatus == 'true'}">
                <h2>Timeline</h2>
                <div>
                    <iframe onload="adjustSreenSize()" id="googledocsURL"
                            src='http://embed.verite.co/timeline/?source=0Ag78k10N9NQWdC1GSDIxWUQtTkx4c0Uyb0Jrck0wY0E&font=Bevan-PotanoSans&maptype=toner&lang=en'
                            width='100%' frameborder='0'>
                    </iframe>
                </div>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
	
		
		<kme:listView id="computerlablist" dataTheme="c" dataDividerTheme="b"
			filter="false">
<%--
			<script type="text/javascript">
				$('[data-role=page][id=computerlabs]').live(
						'pagebeforeshow',
						function(event, ui) {
							$('#clListTemplate').template('clListTemplate');
							refreshTemplate('${pageContext.request.contextPath}/services/computerlabs/getLocations?groupId=${group.name}',
							'#computerlablist', 'clListTemplate',
							'<li>${msgCat_NoLabsFound}</li>', function() {
								$('#computerlablist').listview(
										'refresh');
							});
						});
			</script>
			<script id="clListTemplate" type="text/x-jquery-tmpl">
				<li data-role="list-divider" data-theme="b" data-icon="listview" >\${name}</li>
    			{{each labs}}
    				<li>
						<a href="${pageContext.request.contextPath}/maps?id=\${buildingCode}">
						   	<h3>\${lab}</h3><p>Availability: Windows - \${windowsAvailability}; Linux - \${linuxAvailability}; Mac - \${macAvailability}</p>
 						</a>
					</li>
				{{/each}}
		</script>
--%>
 			<c:choose>
			<c:when test="${not empty group}">
				<c:forEach items="${group.locations}" var="location">
					<kme:listItem dataRole="list-divider" dataTheme="b" dataIcon="listview">${location.name}</kme:listItem>
					<c:forEach items="${location.labs}" var="lab">
						<c:choose>
							<c:when test="${useMaps == 'true'}">
								<c:url var="url" value="${pageContext.request.contextPath}/maps">
									<c:param name="id" value="${lab.buildingCode}"></c:param>
								</c:url>
							</c:when>
							<c:when test="${useDetail == 'true'}">
								<c:url var="url" value="details">
									<c:param name="labUid" value="${lab.labUID}"></c:param>
								</c:url>
							</c:when>
						</c:choose>
					   <li data-theme="c">
						   <c:if test="${useMaps =='true' || useDetail == 'true'}">
				          <a href="${url}">
						   </c:if>
							  <h3>${lab.lab}</h3>
							  <c:choose>
								  <c:when test="${groupSeats == false}">
									  <p>Availability: Windows - ${lab.windowsAvailability}; Linux - ${lab.linuxAvailability}; Mac - ${lab.macAvailability}</p>
								  </c:when>
								  <c:otherwise>
									<p>${lab.availability} ${msgCat_Seats}</p>
								  </c:otherwise>
							  </c:choose>
						   <c:if test="${useMaps =='true' || useDetail == 'true'}">
						  </a>
						   </c:if>
					   </li>
			        </c:forEach>
				</c:forEach>
            </c:when>
			<c:otherwise>
    		     <kme:listItem>${msgCat_NoLabsFound}</kme:listItem>
			</c:otherwise>
			</c:choose>
		</kme:listView>
	</kme:content>
</kme:page>
