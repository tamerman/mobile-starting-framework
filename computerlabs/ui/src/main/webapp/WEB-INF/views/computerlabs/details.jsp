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
<spring:message code="computerlabs.noSeatsFound" var="msgCat_NoSeatsFound"/>
<spring:message code="computerlabs.all" var="msgCat_All"/>

<kme:page title="${msgCat_ToolTitle}" id="computerlabs" homeButton="true" backButton="true" cssFilename="computerlabs">
	<kme:content>
		<kme:listView id="computerlablist" dataTheme="c" dataDividerTheme="b"
			filter="false">

			<script type="text/javascript">
				$('[data-role=page][id=computerlabs]').live(
						'pagebeforeshow',
						function(event, ui) {
							$('#clListTemplate').template('clListTemplate');
							refreshTemplate('${pageContext.request.contextPath}/services/computerlabs/getLab?labUID=${lab.labUID}',
									'#computerlablist', 'clListTemplate',
									'<li>${msgCat_NoSeatsFound}</li>', function() {
										$('#computerlablist').listview(
												'refresh');
									});
						});
			</script>
			<script id="clListTemplate" type="text/x-jquery-tmpl">
				<li data-role="list-divider" data-theme="b" data-icon="listview" >\${name}</li>
				<li>
					<h3>\${lab.lab}</h3>
					<p>\${lab.availability} - ${msgCat_All} ${msgCat_Seats}</p>
					<p>\${lab.windowsAvailability} - windows  ${msgCat_Seats}</p>
					<p>\${lab.linuxAvailability} - linux ${msgCat_Seats}</p>
					<p>\${lab.macAvailability} - macintosh ${msgCat_Seats}</p>
				</li>
			</script>

		</kme:listView>
	</kme:content>
</kme:page>
