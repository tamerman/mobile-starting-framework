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

<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme"  uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="people.searchResults" var="msgCat_SearchResults"/>
<spring:message code="people.noPeopleFound" var="msgCat_NoPeopleFound"/>


<kme:page title="${msgCat_SearchResults}" id="people" backButton="true" homeButton="true" appcacheFilename="iumobile.appcache">
	<kme:content>
		<%--<c:choose>
			<c:when test="${not empty people}">--%>
				<kme:listView id="peopleList" filter="false" dataTheme="c" dataInset="false">
		            <script type="text/javascript">
						$('[data-role=page][id=people]').live('pagebeforeshow', function(event, ui) {
							$('#peopleTemplate').template('peopleTemplate');
							refreshTemplate('people', '#peopleList', 'peopleTemplate', '<li>${msgCat_NoPeopleFound}</li>', function() {$('#peopleList').listview('refresh');});
						});
					</script>
					<script id="peopleTemplate" type="text/x-jquery-tmpl">
						<li>
        					<a href="${pageContext.request.contextPath}/people/\${hashedUserName}">
								<h3>\${lastName}, \${firstName}</h3>
								<p><strong><spring:message code="people.location" /> :</strong>
								{{each(i,location) locations}}
									\${location}{{if i+1 < locations.length}}, {{/if}}
								{{/each}}
								</p>
								<p><strong><spring:message code="people.affiliation" />:</strong>
								{{each(i,affiliation) affiliations}}
									\${affiliation}{{if i+1 < affiliations.length}}, {{/if}}
								{{/each}}
								</p>
				    		 </a>
      					</li>
					</script>
		            
		            <%--
		            <c:forEach items="${people}" var="person" varStatus="status">
		                <kme:listItem>
		                	<c:url value="/people/${person.hashedUserName}" var="url">
		                		<%--<c:param name="lName" value="${search.lastName}" />
		                		<c:param name="fName" value="${search.firstName}" />
		                		<c:param name="uName" value="${search.userName}" />
		                		<c:param name="exact" value="${search.exactness}" />
		                		<c:param name="status" value="${search.status}" />
		                		<c:param name="location" value="${search.location}" /> --%>
		                	<%--</c:url>
							<a href="${url}">
								<h3><c:out value="${person.lastName}" />, <c:out value="${person.firstName}" /></h3>
								<p><strong>Location:</strong>
						       		<c:forEach items="${person.locations}" var="location" varStatus="status">
						       			<c:out value="${location}" /><c:if test="${not status.last}">, </c:if>
						       		</c:forEach>
								</p>
								<p><strong>Affiliation:</strong>
						       		<c:forEach items="${person.affiliations}" var="affiliation" varStatus="status">
						       			<c:out value="${affiliation}" /><c:if test="${not status.last}">, </c:if>
						       		</c:forEach>
								</p>
							</a>
		                </kme:listItem>
		            </c:forEach>--%>
		        </kme:listView>
			<%--</c:when>
			<c:otherwise>
				<p>No people found.</p>
			</c:otherwise>
		</c:choose>--%>
	</kme:content>
</kme:page>
