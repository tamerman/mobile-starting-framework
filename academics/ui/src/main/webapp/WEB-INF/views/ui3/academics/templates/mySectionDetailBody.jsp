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
<div class="list-group-item">
    <div>
        <div ng-show="SocData.socCurrentSection.repeatCode">
            <span class="soc-section-left">Repeat</span>
            <span class="soc-section-right">{{SocData.socCurrentSection.repeatCode}}</span>
        </div>
        <div>
            <span class="soc-section-left">Grading</span>
            <span class="soc-section-right">{{SocData.socCurrentSection.gradeStatus}}</span>
        </div>
        <div>
            <span class="soc-section-left">Units</span>
            <span class="soc-section-right">{{SocData.socCurrentSection.creditHours}}</span>
        </div>
        <div ng-show="sectionWaitList(SocData.socCurrentSection)">
            <span class="soc-section-left">Status</span>
            <span class="soc-section-right">
            <span ng-if="sectionWaitList(SocData.socCurrentSection)" class="orange">Wait List / Position: {{SocData.socCurrentSection.waitTotal}}</span>
        </span>
        </div>
    </div>

    <soc-section-detail-meeting ng-repeat="thisMeeting in SocData.socCurrentSection.meeting"></soc-section-detail-meeting>
</div>
