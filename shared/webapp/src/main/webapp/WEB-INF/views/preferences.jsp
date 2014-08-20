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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="localeCode" value="${pageContext.response.locale}" />
<c:set var="myCampusCode" value="${cookie['campusSelection'].value}" />
<c:set var="isNative" value="${cookie['native'].value}" />

<spring:message code="preferences.title" var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="preferences" backButton="true" homeButton="true">
	<kme:content>
		<kme:listView id="campuslist" dataTheme="c" dataDividerTheme="b">
		
			<%-- Show user authentication status --%>
			<kme:listItem dataRole="list-divider" dataTheme="b">
			   <spring:message code="preferences.authenticationStatus" />
			</kme:listItem>
			<c:if test="${user.publicUser}">
			<kme:listItem >
				<a href="mocklogin"><spring:message code="shared.login" /> [<spring:message code="preferences.unathenticated" />]</a>
			</kme:listItem>
			</c:if>
			<c:if test="${not user.publicUser}">
			<kme:listItem >
				<a href="logout"><spring:message code="shared.logout" /> [${user.loginName}]</a>
			</kme:listItem>
			</c:if>
			
			<%-- Select Home screen --%>
			<kme:listItem dataRole="list-divider" dataTheme="b">
				<spring:message code="preferences.selectHomeScreen" />
			</kme:listItem>
			<c:forEach items="${homeScreens}" var="homeScreen" varStatus="status">
				<kme:listItem>
					<c:url var="campusUrl" value="/campus/select">
						<c:param name="toolName" value="${toolName}" />
						<c:param name="campus" value="${homeScreen.alias}" />
					</c:url>
					<a href="${campusUrl}"> <c:out value="${homeScreen.title}" />
					<c:if test="${homeScreen.alias == myCampusCode }">
						 <span class="ui-check-char">✓</span>
					</c:if>					
					</a>
				</kme:listItem>
			</c:forEach>
			
			<%-- Select language --%>
			<kme:listItem dataRole="list-divider" dataTheme="b">
				<spring:message code="preferences.languages" />
			</kme:listItem>
			<c:forEach items="${supportedLanguages}" var="lang">
				<kme:listItem >
				<a href="?lang=${lang}">
					<spring:message code="preferences.language.${lang}"/>
					<c:if test="${localeCode == lang}">
						 <span class="ui-check-char">✓</span>
					</c:if>
				</a>
				</kme:listItem>
			</c:forEach>
			
			<%-- Select home screen layout - if allowed to --%>
			<c:if test="${allowLayoutChange == true}">
				<kme:listItem dataRole="list-divider" dataTheme="b">
					<spring:message code="preferences.layout"/>
				</kme:listItem>
				<c:forEach items="${availableLayouts}" var="layoutName" >
					<kme:listItem >
						<a href="?homeLayout=${layoutName}">
							<spring:message code="preferences.layout.${layoutName}" />
							<c:if test="${layoutName eq currentLayout}">
								 <span class="ui-check-char">✓</span>
							</c:if>
						</a>
					</kme:listItem>
				</c:forEach>
				
			</c:if>
			
			
			<%-- Push Opt Out --%>
			<c:if test="${not empty senders}">	
				<kme:listItem dataRole="list-divider" dataTheme="b">
					<spring:message code="preferences.push.optout" />
				</kme:listItem>
		        <c:forEach items="${senders}" var="sender" varStatus="status">	            
					<kme:listItem >
						<a href="#" onClick="toggle('${sender.id}');">
							${sender.name}								
							<span id="check${sender.id}" class="ui-check-char">✓</span>
						</a>
					</kme:listItem>
				</c:forEach>             
				<script type="text/javascript" charset="utf-8">				
					var username = "${user.loginName}";
					function updatePreference(id, enabled){
						console.log("Will set " + username + "'s preference for " + id + " to " + (enabled ? "true" : "false") );
						if(username != ""){
							var registerHost = '${pageContext.request.contextPath}/pushprefs/save?data={username:"' + username + '",senderId:"' + id + '",enabled:' + enabled + '}';
							console.log(registerHost);
							$.get(registerHost, function(data){
								console.log("PREF SENT to SERVER: " + data);			
							});
						}
					}
					function toggle(id){
						if(username != ""){	
							var current = $("#check"+id+"").text();
							if(current == "✓"){
								$("#check"+id+"").text("✗");
								updatePreference(id, false);		
							}else{
								$("#check"+id+"").text("✓");
								updatePreference(id, true);		
							}
						}
					}
					$(function() {
						console.log("Did fire ready.");
						if(username != ""){		
							var registerHost = "${pageContext.request.contextPath}/pushprefs/get?username=" + username;
							$.get(registerHost, function(data){
								var prefs =	jQuery.parseJSON(data);		
								var length = prefs.preferences.length;
								for(var i = 0; i < length; i++){
									if(!prefs.preferences[i].enabled){
										$("#check"+prefs.preferences[i].pushSenderID+"").text("✘");					
									}				
								}
							});
						}
					});				
				</script>
			</c:if>	
			
			
			<%-- Install application if using web --%>
			<c:if test="${cookie['native'].value != 'yes'}">
				<kme:listItem dataRole="list-divider" dataTheme="b" cssClass="installLink">
					<spring:message code="preferences.nativeapp" />
				</kme:listItem>
				<kme:listItem cssClass="installLink">
					<a id="install" href="#"><spring:message code="preferences.installnativeapp" /></a> 
					<div class="result"></div>
					<div class="log"></div>
				</kme:listItem>
			<script type="text/javascript">			
					$('#install').click(function(){
						var apps = "";
						$.get("${pageContext.request.contextPath}/testdata/nativeMobileAppURL.json", function(jdata) {	
							apps = jQuery.parseJSON(jdata);
							if (/iPhone|iPad|iPod|Apple/i.test(navigator.userAgent)){
								window.location = apps.iPhoneURL;
							}else if (/Blackberry|RIM\sTablet/i.test(navigator.userAgent)){		
								window.location = apps.BlackberryURL;
							}else if (/Android/i.test(navigator.userAgent)){
								window.location = apps.AndroidURL;
	   						}
						});
					});	
			</script>
			</c:if>			
		</kme:listView>
	</kme:content>
</kme:page>


