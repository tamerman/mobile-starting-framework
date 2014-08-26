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

<div class="container" style="max-width: 400px; margin-top: 20px">
	<div style="background-color: #FFFFFF;border-radius: 10px;box-shadow: 0 1px 2px rgba(0, 0, 0, 0.15); margin: 0 -20px; padding: 20px;">
		<form class="form-horizontal" role="form" novalidate name="inputForm">
		<fieldset>
			<div class="form-group">
			<%-- Start date --%>
				<label for="startDate" class="control-label col-xs-4"><spring:message code="grades.startDate" /></label>

				<div class="col-xs-8">
                    <p class="input-group">
                        <input name="startDate" id="startDate" type="text" class="form-control"
                               datepicker-popup="yyyy-MM-dd"
                               is-open="startDateOpen"
                               show-button-bar="false"
                               show-weeks="false"
                               ng-model="gradesSession.startDate"
                               required
                        <%-- ng-pattern="/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/" --%> />
                 <span class="input-group-btn">
                     <button class="btn btn-default" ng-click="open('start', $event)" ><i class="glyphicon glyphicon-calendar"></i></button>
                 </span>
             </p>
             <%--<input name="startDate" id="startDate" type="date" class="form-control" ng-model="gradesSession.startDate" required ng-pattern="/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/">--%>
					<small class="error" ng-show="inputForm.startDate.$invalid && inputForm.startDate.$error.required">
						<spring:message code="grades.isMandatory" />
					</small>
					<small class="error" ng-show="inputForm.startDate.$invalid && inputForm.startDate.$error.pattern">
                        <spring:message code="grades.invalidDateFormat" />
					</small>
					</div>
				</div>
				<div class="form-group">
				<%-- End date --%>
				<label for="endDate" class="control-label col-xs-4"><spring:message code="grades.endDate" /></label>
				<div class="col-xs-8">
					<%--<input name="endDate" id="endDate" type="date" class="form-control" ng-model="gradesSession.endDate" after="startDate" required ng-pattern="/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/">--%>
                    <p class="input-group">
                        <input name="endDate" id="endDate" type="text" class="form-control"
                               datepicker-popup="yyyy-MM-dd"
                               is-open="endDateOpen"
                               show-button-bar="false"
                               show-weeks="false"
                               ng-model="gradesSession.endDate"
                               required
                               after="gradesSession.startDate"
                        <%--ng-pattern="/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/"--%> />
                        <span class="input-group-btn">
                            <button class="btn btn-default" ng-click="open('end', $event)" ><i class="glyphicon glyphicon-calendar"></i></button>
                        </span>
                    </p>
					<small class="error" ng-show="inputForm.endDate.$dirty && inputForm.endDate.$error.required">
						<spring:message code="grades.isMandatory" />
					</small>
					<small class="error" ng-show="inputForm.endDate.$dirty && inputForm.endDate.$error.after">
						<spring:message code="grades.endDateGreater" />
					</small>
					<small class="error" ng-show="inputForm.endDate.$dirty && inputForm.endDate.$error.pattern">
                        <spring:message code="grades.invalidDateFormat" />
					</small>
				</div>
				</div>
				<button type="submit" class="btn btn-primary pull-right" ng-click="submit();" ng-disabled="inputForm.$invalid"><spring:message code="grades.search" /></button>
			</fieldset>
		</form>
	</div>
</div>