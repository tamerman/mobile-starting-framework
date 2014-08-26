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

<spring:message code="dining.title" var="title"/>

<kme:page title="${title}" id="dining" backButton="true" homeButton="true" cssFilename="dining">
	<kme:content>
		<kme:listView id="menulist" dataTheme="c" dataDividerTheme="b" filter="false">
			<script type="text/javascript">
				$('[data-role=page][id=dining]').live('pagebeforeshow', function(event, ui) {
					$('#menuListTemplate').template('menuListTemplate');
					refreshTemplate('dining', '#menulist', 'menuListTemplate', '<li>No Menus</li>', function() {$('#menulist').listview('refresh');});
				});
			</script>
			<script id="menuListTemplate" type="text/x-jquery-tmpl">
				<li data-role="list-divider">\${dateFormatted}</li>
				{{each(i,item) items}}
      				<li>
        				<h3 class="wrap">\${name}</h3>
						<p class="wrap">\${priceFormatted}</p>
      				</li>
				{{/each}}
			</script>			
			<%--
			<c:choose>
				<c:when test="${not empty menus}">
					<c:forEach items="${menus}" var="menu" varStatus="status">
						<kme:listItem dataTheme="b" dataRole="list-divider">${menu.dateFormatted}</kme:listItem>
						<c:forEach items="${menu.items}" var="item" varStatus="status">
							<kme:listItem>
									<h3 class="wrap">${item.name}</h3>
									<p class="wrap">${item.priceFormatted}</p>
							</kme:listItem>
						</c:forEach>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<kme:listItem>
						No menus
					</kme:listItem>
				</c:otherwise>
			</c:choose>
			--%>
		</kme:listView>
	</kme:content>
</kme:page>
