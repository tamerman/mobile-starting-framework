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

<spring:message code="people.noMembersFound" var="msgCat_NoMembersFound"/>

<kme:page title="${groupName}" id="groupmembers" homeButton="true" backButton="true" cssFilename="people">
	<kme:content>
		<kme:listView id="groupmemberslist" dataTheme="c" dataDividerTheme="b"
			filter="false">
			<script type="text/javascript">
				$('[data-role=page][id=groupmembers]').live(
						'pagebeforeshow',
						function(event, ui) {
							$('#gmListTemplate').template('gmListTemplate');
							
							refreshTemplate('${pageContext.request.contextPath}/services/directory/group/lookup?_type=json&dn=cn=${groupName},ou=User%20Groups,ou=Groups,dc=umich,dc=edu',
									'#groupmemberslist', 'gmListTemplate',
									'<li>${msgCat_NoMembersFound}</li>', function() {
										$('#groupmemberslist').listview(
												'refresh');
									});
						});
			</script>
			<script id="gmListTemplate" type="text/x-jquery-tmpl">
				<li data-role="list-divider" data-theme="b" data-icon="listview"><spring:message code="people.groupOwners" /></li>
				{{each group.owners}}
					<li>
						<h3>\${displayName}</h3>
						<h6>\${userName}</h6>
					</li>
				{{/each}}
				
				<li data-role="list-divider" data-theme="b" data-icon="listview"><spring:message code="people.groupMembers" /></li>
				{{each group.members}}
					<li>
						<h3>\${displayName}</h3>
						<h6>\${userName}</h6>
					</li>
				{{/each}}
		        </script>

		</kme:listView>
	</kme:content>
</kme:page>
