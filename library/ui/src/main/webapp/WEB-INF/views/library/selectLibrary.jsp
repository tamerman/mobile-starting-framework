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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:message code="library.contact.us.libraries" var="msgCat_ChooseALibrary" />
<spring:message code="library.title" var="msgCat_Title" />
<kme:page title="${msgCat_Title}" id="library-ui" backButton="true" homeButton="true">
	<kme:content>
		<c:choose>
			<%-- if there are libraries --%>
			<c:when test="${fn:length(campusCodes) gt 0}">
				<kme:listView filter="false">
					<kme:listItem dataRole="list-divider" dataTheme="b">${msgCat_ChooseALibrary}</kme:listItem>
					<%-- Loop though campus codes --%>
					<c:forEach items="${campusCodes}" var="campusCode">

						<kme:listItem dataRole="list-divider" dataTheme="a">
							<c:out value="${campusCode}" />
						</kme:listItem>

						<%-- Loop through each library per campus --%>
						<c:forEach items="${libraryMap[campusCode]}" var="library">
							<kme:listItem>
								<a href="${pageContext.request.contextPath}/library/${actionPage}/${library.id}">${library.name}</a>
							</kme:listItem>
						</c:forEach>
					</c:forEach>
				</kme:listView>
			</c:when>
			<%-- if there are no libraries --%>
			<c:otherwise>
				<h3><spring:message code="library.noLibrariesAvailable" /></h3>
			</c:otherwise>
		</c:choose>
	</kme:content>
</kme:page>