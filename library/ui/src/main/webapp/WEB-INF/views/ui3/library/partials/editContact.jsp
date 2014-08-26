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
<div ng-controller="LibraryEditContactCtrl" ng-init="libraryId = '${libraryId}'">
	<div class="panel panel-primary" >
		<div class="panel-heading">${library.name}</div>
        <div class="panel-body">

            <form name="contactForm" id="contactForm" class="form-horizontal" role="form" action="${pageContext.request.contextPath}/library/editContact/${libraryId}" method="POST">
			<%-- Telephone number --%>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.telephone" /></label>
                <div class="col-sm-8">
                     <input class="form-control" type="tel" id="telephone" name="telephone" ng-model="contactDetail.telephone">
                </div>
            </div>

			<%-- Fax number --%>
             <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.fax" /></label>
                <div class="col-sm-8">
                    <input class="form-control" type="tel" id="fax" name="fax" ng-model="contactDetail.fax">
                </div>
            </div>

			<%-- General Information desk number --%>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.general.info.desk" /></label>
                <div class="col-sm-8">
                    <input class="form-control" type="tel" id="generalInfoDesk" name="generalInfoDesk" ng-model="contactDetail.generalInfoDesk">
                </div>
            </div>

			<%-- Email --%>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.email" /></label>
                <div class="col-sm-8">
                    <input class="form-control" type="email" id="email" name="email" ng-model="contactDetail.email">
                </div>
            </div>

			<%-- Postal Address --%>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.postal.addr" /></label>
                <div class="col-sm-8">
                    <textarea class="form-control" id="postalAddress" name="postalAddress" rows="10" cols="20" ng-model="contactDetail.postalAddress"></textarea>
                </div>
            </div>

			<%-- Physical Address --%>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.physical.addr" /></label>
                <div class="col-sm-8">
                    <textarea class="form-control" id="physicalAddress" name="physicalAddress" rows="10" cols="20" ng-model="contactDetail.physicalAddress"></textarea>
                </div>
            </div>

			<%-- GPS coordinates --%>

            <%-- Latitude Address --%>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.latitude" /></label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" id="latitude" name="latitude"  ng-model="contactDetail.latitude"/>
                </div>
            </div>

            <%-- Longitude Address --%>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="library.contact.longitude" /></label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" id="longitude" name="longitude" ng-model="contactDetail.longitude"/>
                </div>
            </div>
                <div class="alert alert-info">
                    <button class="btn btn-primary" role="button" type="submit"><spring:message code="library.save" /></button>
                    <a class="btn btn-default" href="#/viewContact/${libraryId}"><spring:message code="library.hours.cancel" /></a>
                </div>
            </form>
    </div>
</div>


</div>