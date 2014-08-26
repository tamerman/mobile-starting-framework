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
