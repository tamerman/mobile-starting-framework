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
<div ng-controller="EventsDetailController" data-ng-init="init()">
    <div class="list-group-item">
        <h4 class="wrap">{{currentEvent.title}}</h4>
        <p>{{currentEvent.displayStartDate}}</p>
        <p>{{currentEvent.displayStartTime}} - {{currentEvent.displayEndTime}}</p>
    </div>

    <div class="list-group-item" ng-show="{{!!currentEvent.location}}">
        <h4 class="wrap">Location</h4>
        <p>{{currentEvent.location}}</p>
    </div>

    <div class="list-group-item" ng-show="{{currentEvent.description && currentEvent.description.length}}">
        <h4 class="wrap">Description</h4>
        <p ng-repeat="desc in currentEvent.description">{{desc}}</p>
    </div>

    <div class="list-group-item" ng-show="{{!!currentEvent.link}}">
        <h4 class="wrap">Website</h4>
        <a ng-href="{{currentEvent.link}}">{{currentEvent.link}}</a>
    </div>

    <div class="list-group-item" ng-show="{{currentEvent.contact && currentEvent.contact.length}}">
        <h4 class="wrap">Sponsor</h4>
        <p ng-repeat="contact in currentEvent.contact">{{contact.name}}</p>
    </div>
</div>
