<%--
Copyright 2014 The Kuali Foundation Licensed under the Educational
Community License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License
at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
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