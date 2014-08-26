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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div ng-controller="NoOpCtrl">
	<div class="panel panel-primary" >
		<div class="panel-heading">${library.name}</div>
        <div class="panel-body">

			<%-- Telephone number --%>
			<c:if test="${not empty libraryContactDetail.telephone}">
				<div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.telephone" /></b></div>
                    <div class="col-xs-6 col-md-8"><a href="tel:${libraryContactDetail.telephone}">${libraryContactDetail.telephone}</a></div>
                </div>
			</c:if>

			<%-- Fax number --%>
			<c:if test="${not empty libraryContactDetail.fax}">
				<div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.fax" /></b></div>
                    <div class="col-xs-6 col-md-8">${libraryContactDetail.fax}</div>
                </div>
			</c:if>

			<%-- General Information desk number --%>
			<c:if test="${not empty libraryContactDetail.generalInfoDesk}">
				<div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.general.info.desk" /></b></div>
                    <div class="col-xs-6 col-md-8"><a href="tel:${libraryContactDetail.generalInfoDesk}">${libraryContactDetail.generalInfoDesk}</a></div>
                </div>
			</c:if>

			<%-- Email --%>
			<c:if test="${not empty libraryContactDetail.email}">
				<div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.email" /></b></div>
                    <div class="col-xs-6 col-md-8"><a href="mailto:${libraryContactDetail.email}">${libraryContactDetail.email}</a></div>
                </div>
			</c:if>

			<%-- Postal Address --%>
			<c:if test="${not empty libraryContactDetail.postalAddress}">
				<div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.postal.addr" /></b></div>
                    <div class="col-xs-6 col-md-8">${libraryContactDetail.postalAddress}</div>
                </div>
			</c:if>

			<%-- Physical Address --%>
			<c:if test="${not empty libraryContactDetail.physicalAddress}">
				<div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.physical.addr" /></b></div>
                    <div class="col-xs-6 col-md-8">${libraryContactDetail.physicalAddress}</div>
                </div>
			</c:if>

			<%-- GPS coordinates --%>
			<c:if test="${not empty libraryContactDetail.latitude and not empty libraryContactDetail.longitude}">

				<%-- Latitude Address --%>
                <div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.latitude" /></b></div>
                    <div class="col-xs-6 col-md-8">${libraryContactDetail.latitude}</div>
                </div>

                <%-- Longitude Address --%>
                <div class="row">
                    <div class="col-xs-6 col-md-4"><b><spring:message code="library.contact.longitude" /></b></div>
                    <div class="col-xs-6 col-md-8">${libraryContactDetail.longitude}</div>
                </div>
			</c:if>
    </div>
</div>
    <c:if test="${isAdmin}">
   	<div class="alert alert-info">
        <a href="#/editContact/${library.id}" class="btn btn-primary"><spring:message code="library.edit" /></a>
    </div>
    </c:if>
</div>