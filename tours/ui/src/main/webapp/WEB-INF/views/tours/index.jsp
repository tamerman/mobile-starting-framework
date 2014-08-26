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