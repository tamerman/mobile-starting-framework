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

<kme:multiPage title="${title}" id="diningPlaces" backButton="true" homeButton="true" cssFilename="dining">
    <!-- campus page -->
    <kme:childPage title="${title}" id="diningPlaces" homeButton="true" backButton="true">
     <kme:content>
          <kme:listView id="campuslist" dataTheme="c" dataDividerTheme="b" filter="false">
			<c:choose>
				<c:when test="${not empty placeGroups}">
					<c:forEach items="${placeGroups}" var="placeGroup" varStatus="index">
					<kme:listItem>
						<a href="#dhall${index.count}" data-transition="none">
					    <h3>
					     <c:out value="${placeGroup.campus}" />
					    </h3>
					    </a>
					 </kme:listItem>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                        No campus found.
                </c:otherwise>
            </c:choose>
            </kme:listView>
        </kme:content>
       </kme:childPage>

           <c:choose>
				<c:when test="${not empty placeGroups}">
			    <c:forEach items="${placeGroups}" var="placeGroup" varStatus="index">
				<kme:childPage title="${placeGroup.campus}" id="dhall${index.count}" homeButton="true" backButton="true">
                     <kme:content>
                     <kme:listView dataTheme="c" dataDividerTheme="b" filter="false">
					   <c:forEach items="${placeGroup.placeByTypes}" var="placeByType" varStatus="status">
					   <kme:listItem dataTheme="b" dataRole="list-divider"  cssClass="contentItem category-${index.count}">
                           <h3 class="wrap"><c:out value="${placeByType.type}"/></h3>
                        </kme:listItem>
                       <c:forEach items="${placeByType.places}" var="place" varStatus="status">

				       <kme:listItem cssClass="contentItem category-${index.count}" dataTheme="c">
						<c:choose>
							<c:when test="${empty place.location}">
							 <a href="${pageContext.request.contextPath}/dining/menu?name=${place.name}">
							  <h3 class="wrap">${place.name}</h3></a>
						    </c:when>
							<c:otherwise>
							 <a href="${pageContext.request.contextPath}/dining/menu?name=${place.name}&location=${place.location}">
							 <h3 class="wrap">${place.name}</h3>
							  <p class="wrap">${place.location}</p></a>
							</c:otherwise>
						 </c:choose>
						</kme:listItem>
			            </c:forEach>
                        </c:forEach>
                      </kme:listView>
                       <kme:listView id="menuList"dataTheme="c" dataDividerTheme="b" filter="false">
                       </kme:listView>
		         </kme:content>
	            </kme:childPage>
	            </c:forEach>
		        </c:when>
		        <c:otherwise>
		        <kme:listItem>
			      No places
		        </kme:listItem>
		       </c:otherwise>
	           </c:choose>


  </kme:multiPage>