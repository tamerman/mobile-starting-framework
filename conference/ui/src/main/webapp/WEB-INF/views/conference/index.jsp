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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="conference.noMenu" var="msgCat_NoMenu"/>

<c:set var="localeCode" value="${pageContext.response.locale}" />

<spring:message code="conference.title" var="title"/>
<kme:page title="${title}" id="conference" backButton="true" homeButton="true">
	<kme:content>
		<kme:listView id="menulist" dataTheme="c" dataDividerTheme="b" filter="false">
			<script type="text/javascript">
				$('[data-role=page][id=conference]').live('pagebeforeshow', function(event, ui) {
					$('#menuListTemplate').template('menuListTemplate');
					refreshTemplate('${pageContext.request.contextPath}/conference?lang=${localeCode}', '#menulist', 'menuListTemplate', '<li>${msgCat_NoMenu}</li>', function() {$('#menulist').listview('refresh');});
				});
			</script>
			<script id="menuListTemplate" type="text/x-jquery-tmpl">
      			<li>
					<a href="${pageContext.request.contextPath}/\${linkURL}">
        				<h3 class="wrap">\${title}</h3>
						<p class="wrap">\${description}</p>
					</a>
      			</li>
			</script>	
		</kme:listView>
	<%-- accesses a json file for the menu items
		<kme:listView>
			<c:forEach items="${menuItems}" var="menuItem" varStatus="status">
				<kme:listItem>
					<a href="${menuItem.linkURL}">
						<h3 class="wrap">
							${menuItem.title}
						</h3>
						<p>${menuItem.description}</p>
					</a>
				</kme:listItem>
			</c:forEach>
		</kme:listView>
	--%>
	<%--  hard coded
		<kme:listView>
			<kme:listItem>
	    		<a href="conference/welcome">
	    			<h3 class="wrap">
	    				<spring:message code="conference.welcome"/>
	    			</h3>
	    			<p class="wrap"><spring:message code="conference.welcome.sub"/></p>
	    		</a>
	    	</kme:listItem>
			<kme:listItem>
	    		<a href="conference/sessions?date=${today}">
	    			<h3 class="wrap">
	    				<spring:message code="conference.schedule"/>
	    			</h3>
	    			<p class="wrap"><spring:message code="conference.schedule.sub"/></p>
	    		</a>
	    	</kme:listItem>
	    	<kme:listItem>
	    		<a href="conference/featuredSpeakers">
	    			<h3 class="wrap">
	    				<spring:message code="conference.featuredspeakers"/>
	    			</h3>
	    			<p class="wrap"><spring:message code="conference.featuredspeakers.sub"/></p>
	    		</a>
	    	</kme:listItem>
	    	<kme:listItem>
	    		<a href="conference/attendeeGroups">
	    			<h3 class="wrap">
	    				<spring:message code="conference.attendeelist"/>
	    			</h3>
	    			<p class="wrap"><spring:message code="conference.attendeelist.sub"/></p>
	    		</a>
	    	</kme:listItem>
	    	<kme:listItem>
	    		<a href="http://localhost:9999/mdot/testdata/imumap10.jpg">
	    			<h3 class="wrap">
	    				<spring:message code="conference.IMUmap"/>
	    			</h3>
	    			<p class="wrap"><spring:message code="conference.IMUmap.sub"/></p>
	    		</a>
	    	</kme:listItem>
		</kme:listView>
		--%>
	</kme:content>
</kme:page>
