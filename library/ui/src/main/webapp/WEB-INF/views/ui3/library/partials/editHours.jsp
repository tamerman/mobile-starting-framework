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
<div ng-controller="LibraryEditHoursCtrl">
	<div class="panel panel-primary">
		<div class="panel-heading">${library.name}</div>
        <div class="panel-body">

            <form method="POST" id="libraryHoursForm" class="form-horizontal" action="${pageContext.request.contextPath}/library/editHours/${libraryId}" novalidate ng-init="libraryId = '${libraryId}'">
                 <c:forEach var="libraryHours" items="${hourSets}" varStatus="hs_status">
                    <spring:message code="${libraryHours.period.label}" var="msgCat_hourSetName"/>
                    <h2>${msgCat_hourSetName}</h2>
                    <div style="max-width: 800px">

                        <c:forEach var="libraryHours" items="${libraryHours.hours}" varStatus="h_status">
                            <spring:message code="library.hours.${libraryHours.dayOfWeek }" var="msgCat_dayName"/>
                            <div>
                                <h4>${msgCat_dayName}</h4>
                                <c:set var="hourGroupStyle" value=""/>
                                <c:set var="closedAttributes" value=""/>
                                <c:if test="${libraryHours.closed}">
                                    <c:set var="hourGroupStyle" value="display: none"/>
                                    <c:set var="closedAttributes" value="checked=\"checked\""/>
                                </c:if>
                                    <%-- From --%>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" for="s${hs_status.count}h${h_status.count}fromTime">
                                        <spring:message code="library.hours.from" />
                                    </label>
                                    <div class="col-sm-4">
                                            <input type="time"
                                                   class="form-control"
                                                   ng-model="s${hs_status.count}h${h_status.count}fromTime"
                                                   ng-init="s${hs_status.count}h${h_status.count}fromTime = '<fmt:formatDate pattern="HH:mm" value="${libraryHours.fromTime}" />'"
                                                   id="s${hs_status.count}h${h_status.count}fromTime"
                                                   name="s${hs_status.count}h${h_status.count}fromTime"
                                                   ng-disabled="s${hs_status.count}h${h_status.count}closed"
                                                   ng-required="!s${hs_status.count}h${h_status.count}closed"/>

                                    </div>

                                    <%-- To --%>
                                    <label class="col-sm-1 control-label" for="s${hs_status.count}h${h_status.count}toTime">
                                        <spring:message code="library.hours.to" />
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="time"
                                               class="form-control"
                                               ng-model="s${hs_status.count}h${h_status.count}toTime"
                                               ng-init="s${hs_status.count}h${h_status.count}toTime = '<fmt:formatDate pattern="HH:mm" value="${libraryHours.toTime}" />'"
                                               id="s${hs_status.count}h${h_status.count}toTime"
                                               name="s${hs_status.count}h${h_status.count}toTime"
                                               ng-disabled="s${hs_status.count}h${h_status.count}closed"
                                               ng-required="!s${hs_status.count}h${h_status.count}closed">

                                    </div>


                                    <div class="col-sm-1">
                                        <div class="checkbox" >
                                            <label>
                                                <input type="checkbox"
                                                ng-model="s${hs_status.count}h${h_status.count}closed"
                                                ng-init="s${hs_status.count}h${h_status.count}closed = ${libraryHours.closed}"
                                                name="s${hs_status.count}h${h_status.count}closed"
                                                id="s${hs_status.count}h${h_status.count}closed"
                                                value="<spring:message code="library.hours.closed" /> ?"/>
                                                <spring:message code="library.hours.closed" />
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                     <hr>
                </c:forEach>
                <div class="alert alert-info">
                    <button type="submit" class="btn btn-primary" role="button" ><spring:message code="library.save" /></button>
                    <a class="btn btn-default" href="#/viewContact/${libraryId}"><spring:message code="library.hours.cancel" /></a>
                </div>
            </form>
    </div>
</div>
</div>

