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