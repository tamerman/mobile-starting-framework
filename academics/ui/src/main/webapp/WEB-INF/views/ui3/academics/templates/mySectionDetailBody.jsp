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
