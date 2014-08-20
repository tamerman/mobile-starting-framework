<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<kme:page title="Tours Publishing" id="toursMessage" backButton="true" homeButton="true" cssFilename="tours">
    <kme:content>
		<h2 style="display:inline-block;margin:0 30px 0 0;">Tours</h2><a href="${pageContext.request.contextPath}/tours/new" data-role="button" data-inline="true" data-theme="c">new</a><br /><br />
		<table class="toursList">
			<c:forEach items="${tours}" var="tour" varStatus="status">
				<tr>
					<td><h3 style="margin:0 10px 0 0;padding:0;">${tour.name}</h3></td>
					<td>
						<div data-role="controlgroup" data-type="horizontal">
							<a href="${pageContext.request.contextPath}/tours/edit/${tour.tourId}" data-role="button" data-theme="c">edit</a>
							<a href="${pageContext.request.contextPath}/tours/copy/${tour.tourId}" data-role="button" data-theme="c">duplicate</a>
							<a href="${pageContext.request.contextPath}/tours/delete/${tour.tourId}" data-role="button" data-theme="c">delete</a>
							<a href="${pageContext.request.contextPath}/tours/kml/${tour.tourId}" data-role="button" data-theme="c">kml</a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<h2 style="display:inline-block;margin:0 30px 0 0;">Common Points of Interest</h2><a href="${pageContext.request.contextPath}/tours/poi/new" data-role="button" data-inline="true" data-theme="c">new</a><br /><br />
		<table class="toursList">
			<c:forEach items="${pois}" var="poi" varStatus="status">
				<tr>
					<td><h3 style="margin:0 10px 0 0;padding:0;">${poi.name}</h3></td>
					<td>
						<div data-role="controlgroup" data-type="horizontal">
							<a href="${pageContext.request.contextPath}/tours/poi/edit/${poi.poiId}" data-role="button" data-theme="c">edit</a>
							<a href="${pageContext.request.contextPath}/tours/poi/copy/${poi.poiId}" data-role="button" data-theme="c">duplicate</a>
							<a href="${pageContext.request.contextPath}/tours/poi/delete/${poi.poiId}" data-role="button" data-theme="c">delete</a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
    </kme:content>
</kme:page>