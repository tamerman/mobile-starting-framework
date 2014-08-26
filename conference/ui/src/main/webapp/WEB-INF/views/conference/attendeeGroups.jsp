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

<spring:message code="attendeelist.title" var="msgCat_Title"/>

<kme:page title="${msgCat_Title}" id="conference" backButton="true" homeButton="true">
	<kme:content>
		<kme:listView>
			<kme:listItem>
				<a href="attendees?start=A&end=C">A - C</a>
			</kme:listItem>
			<kme:listItem>
				<a href="attendees?start=D&end=F">D - F</a>
			</kme:listItem>
			<kme:listItem>
				<a href="attendees?start=G&end=I">G - I</a>
			</kme:listItem>
			<kme:listItem>
				<a href="attendees?start=J&end=L">J - L</a>
			</kme:listItem>
			<kme:listItem>
				<a href="attendees?start=M&end=O">M - O</a>
			</kme:listItem>
			<kme:listItem>
				<a href="attendees?start=P&end=R">P - R</a>
			</kme:listItem>
			<kme:listItem>
				<a href="attendees?start=S&end=U">S - U</a>
			</kme:listItem>
			<kme:listItem>
				<a href="attendees?start=V&end=Z">V - Z</a>
			</kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>
