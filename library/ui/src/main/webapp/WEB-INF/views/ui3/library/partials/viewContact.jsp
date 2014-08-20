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