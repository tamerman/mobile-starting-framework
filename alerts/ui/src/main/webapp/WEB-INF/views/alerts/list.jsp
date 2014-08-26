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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="alerts.title" var="msgCat_Title"/>
<spring:message code="alerts.noAlerts" var="msgCat_NoAlerts"/>

<kme:page title="${msgCat_Title}" id="campusalerts" backButton="true" homeButton="true" cssFilename="alerts">
	<kme:content>
	    <kme:listView id="alertlist" filter="false" dataTheme="c" dataDividerTheme="b">
	        <script type="text/javascript">
				$('[data-role=page][id=campusalerts]').live('pagebeforeshow', function(event, ui) {
					$('#alertListTemplate').template('alertListTemplate');
					refreshTemplate('services/alerts/byCampus/${campus}?_type=json', '#alertlist', 'alertListTemplate', '<li>${msgCat_NoAlerts}</li>', function() {$('#alertlist').listview('refresh');});
				});
			</script>
			<script id="alertListTemplate" type="text/x-jquery-tmpl">
                {{each alert}}
				<li>
					<h3 class="wrap"><c:out value="\${campus} - \${title}"/></h3>
	            	<p class="wrap"><c:out value="\${mobileText}"/></p>
				</li>
                {{/each}}
			</script>	       	        
	        <%--
	        <c:forEach items="${alerts}" var="alert" varStatus="status">
	            <kme:listItem>
	            	<h3 class="wrap"><c:out value="${alert.campus} - ${alert.title}"/></h3>
	            	<p class="wrap"><c:out value="${alert.mobileText}"/></p>
	            </kme:listItem>
			</c:forEach>
			--%>
	    </kme:listView>
	</kme:content>
</kme:page>
