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
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<kme:page title="Event Detail" id="eventdetail" backButton="true" homeButton="true">
	<kme:content>
	<kme:listView id="detail" dataTheme="c" dataDividerTheme="b" filter="false">
		<script type="text/javascript">
			$('[data-role=page][id=eventdetail]').live('pagebeforeshow', function(event, ui) {

				$('#detailTemplate').template('detailTemplate');
				refreshTemplate('viewEvent?eventId=${event}', '#detail', 'detailTemplate', '<li>No detail available</li>',
					function() {$('#detail').listview('refresh');
						});
			});
		</script>
		<script id="detailTemplate" type="text/x-jquery-tmpl">
			<li><h3 class="wrap">\${title}</h3>
				<p class="wrap">\${displayStartDate}<br />\${displayStartTime} - \${displayEndTime}</p></li>
			<li><h3 class="wrap"><spring:message code="events.location" /></h3>
				<p class="wrap">\${location}</p></li>
			<li><h3 class="wrap"><spring:message code="events.description" /></h3>
				{{each description}}
					<p class="wrap">\${$value}</p>
				{{/each}}</li>
			<li><h3 class="wrap"><spring:message code="events.website" /></h3>
				{{if link}}
					<p class="wrap"><a href="\${link}">\${link}</a></p>
				{{else}}
					<p class="wrap">N/A</p>
				{{/if}}</li>
			<li><h3 class="wrap"><spring:message code="events.sponsor" /></h3>
				{{each contact}}
					<p class="wrap">\${$value.name}</p>
				{{/each}}</li>
		</script>
	</kme:listView>
	</kme:content>
</kme:page>
