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
