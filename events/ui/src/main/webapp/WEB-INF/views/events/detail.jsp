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
