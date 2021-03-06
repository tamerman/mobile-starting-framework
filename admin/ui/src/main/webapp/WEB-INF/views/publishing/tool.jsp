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

<spring:message code="tools.title" var="title"/>
<kme:page title="${title}" id="layout" backButton="true" homeButton="true" backButtonURL="${pageContext.request.contextPath}/publishing/index">
	<kme:content>
		<kme:listView id="layoutlist" dataTheme="c" dataDividerTheme="b" filter="false">
			<kme:listItem dataRole="list-divider">
				<spring:message code="tools.definition.creation"/>
			</kme:listItem>
			<kme:listItem>
				<a href="${pageContext.request.contextPath}/publishing/tool/new">
					<h3 class="wrap">
						<spring:message code="tools.new.title"/>
					</h3>
					<p class="wrap">
						<spring:message code="tools.new.instructions"/>
					</p>
				</a>
			</kme:listItem>
			<kme:listItem dataRole="list-divider">
				<spring:message code="tools.existing"/>
			</kme:listItem>
			<c:forEach items="${tools}" var="tool" varStatus="status">
				<kme:listItem>
					<a href="${pageContext.request.contextPath}/publishing/tool/edit/${tool.toolId}">
						<h3 class="wrap">
							<spring:message code="${tool.title}"/>
						</h3>
					</a>	
				</kme:listItem>
			</c:forEach>
		</kme:listView>
	</kme:content>
</kme:page>