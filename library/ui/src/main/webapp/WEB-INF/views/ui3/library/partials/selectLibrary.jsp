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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div ng-controller="NoOpCtrl">
<c:choose>
    <c:when test="${fn:length(campusCodes) gt 0}">
        <div class="list-group">
            <div class="list-group-item list-header"><spring:message code="library.contact.us.libraries" /></div>

            <c:forEach items="${campusCodes}" var="campusCode">
                <div class="list-group-item list-header"><c:out value="${campusCode}" /></div>

                <%-- Loop through each library per campus --%>
                <c:forEach items="${libraryMap[campusCode]}" var="library">
                    <a href="#/${actionPage}/${library.id}" class="list-group-item">
                        <h4 class="list-group-item-heading">${library.name}</h4>
                    </a>
                </c:forEach>
            </c:forEach>
        </div>
    </c:when>
    <%-- if there are no libraries --%>
    <c:otherwise>
        <h3><spring:message code="library.noLibrariesAvailable" /></h3>
    </c:otherwise>
</c:choose>
</div>