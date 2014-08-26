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

<spring:message code="emergencyinfo.title" var="msgCat_ToolTitle"/>
<spring:message code="emergencyinfo.true" var="msgCat_TrueEmer"/>
<spring:message code="emergencyinfo.phonenumbers" var="msgCat_Phone"/>
<spring:message code="emergencyinfo.noContacts" var="msgCat_NoContacts"/>


<kme:page title="${msgCat_ToolTitle}" id="emergencyinfo" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="emergencyinfo">
    <kme:content>
        <kme:listView id="emergencylist" filter="false">
        	<kme:listItem cssClass="link-phone">
	        	<a href="tel:911">
	        		<h3>${msgCat_TrueEmer}: <span style="color:darkred;">911</span></h3>
	        	</a>
        	</kme:listItem>
        	<kme:listItem dataTheme="b" dataRole="list-divider">${msgCat_Phone}</kme:listItem>
        </kme:listView>
        <kme:listView id="emergencylistdata" filter="false">
            <script type="text/javascript">
				$('[data-role=page][id=emergencyinfo]').live('pagebeforeshow', function(event, ui) {
					$('#emergencyListTemplate').template('emergencyListTemplate');
					refreshTemplate('${pageContext.request.contextPath}/services/emergency/information/bycampus/${campus}?_type=json', '#emergencylistdata', 'emergencyListTemplate', '<li data-theme=\"c\">${msgCat_NoContacts}</li>', function() {$('#emergencylistdata').listview('refresh');});
				});
			</script>
			<script id="emergencyListTemplate" type="text/x-jquery-tmpl">
				{{each emergencyInfo}}
				<kme:listItem cssClass="link-phone" dataTheme="c">
					<a href="tel:\${link}">
                		<h3>\${title}</h3>
                    	<p>\${link}</p>
               	 	</a>
				</kme:listItem>
				{{/each}}
			</script>
            <%--
            <c:forEach items="${emergencyinfos}" var="emergencyinfo" varStatus="status">
                <kme:listItem cssClass="link-phone">
                	<a href="tel:${emergencyinfo.link}">
                		<h3>${emergencyinfo.title}</h3>
                    	<p>${emergencyinfo.link}</p>
               	 	</a>
                </kme:listItem>
            </c:forEach>
            --%>
        </kme:listView>
    </kme:content>
</kme:page>
