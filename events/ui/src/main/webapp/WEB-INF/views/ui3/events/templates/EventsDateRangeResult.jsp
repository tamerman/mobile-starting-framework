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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div>

<a href="#/detail" class="list-group-item" ng-hide="event.children" ng-click="eventClick(event)" ng-repeat="event in EventsData.eventsForDateRange.event">
    <p>{{event.title}}</p>
    <p>{{event.displayStartTime}} - {{event.displayEndTime}} | {{event.location}}</p>
</a>

</div>