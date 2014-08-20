<%--
  Copyright 2011-2013 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme:page title="${title}" id="sections-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics">
    <kme:content>

        <kme:listView dataTheme="c" dataDividerTheme="b" filter="false" cssClass="paddingBottom15px">
            <kme:listItem>
                <h3 class="wrap">
                    <c:out value="${thisSection.subjectId}"/>&nbsp;<c:out value="${thisSection.catalogNumber}"/>&nbsp;<c:out value="${thisSection.number}"/>&nbsp;<c:out value="${thisSection.type}"/>
                </h3>
                <c:if test="${not empty thisSection.courseTitle}">
                    <p class="academics-courseTitle">${thisSection.courseTitle}</p>
                </c:if>
                <c:if test="${not empty thisSection.classTopic}">
                    <p>${thisSection.classTopic}</p>
                </c:if>
                <c:if test="${toolContext == 'myAcademics'}">
                    <p><span class="academics-grade">${thisSection.grade}</span></p> 
                </c:if>    
            </kme:listItem>
        </kme:listView>
        <div id="tab1">
            <kme:listView dataTheme="c" dataDividerTheme="b" filter="false">
                <kme:listItem>
                    <p class="ui-li-desc-academics-override">&nbsp;</p>    
                <c:choose>
                    <c:when test="${toolContext == 'myAcademics'}">
                        <c:if test="${not empty thisSection.repeatCode}">
                            <p><span class="academics-sectionLeftWider">Repeat</span><span class="academics-sectionRight">${thisSection.repeatCode}</span></p>
                        </c:if>
                        <p><span class="academics-sectionLeftWider">Grading</span><span class="academics-sectionRight">${thisSection.gradeStatus}</span></p>      
                        <p><span class="academics-sectionLeftWider">Units</span><span class="academics-sectionRight">${thisSection.creditHours}</span></p>
                        <c:if test="${fn:toLowerCase(thisSection.enrollmentStatus) == 'waiting'}">
                            <p><span class="academics-sectionLeftWider">Status</span><span class="academics-sectionRight font-orange">Wait List / Position: ${thisSection.waitTotal}</span></p>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${fn:toLowerCase(thisSection.enrollmentStatus) == 'open'}">
                                <p><span class="academics-sectionLeftWider">Status</span><span
                                        class="academics-sectionRight font-green"><c:out
                                        value="${thisSection.availableSeats}"/>&nbsp;<c:out
                                        value="${thisSection.enrollmentStatus}"/></span></p>
                            </c:when>
                            <c:when test="${fn:toLowerCase(thisSection.enrollmentStatus) == 'closed'}">
                                <p><span class="academics-sectionLeftWider">Status</span><span
                                        class="academics-sectionRight font-red"><c:out
                                        value="${thisSection.enrollmentStatus}"/></span></p>
                            </c:when>
                            <c:when test="${fn:toLowerCase(thisSection.enrollmentStatus) == 'wait list'}">
                                <p><span class="academics-sectionLeftWider">Status</span><span
                                        class="academics-sectionRight font-orange"><c:out
                                        value="${thisSection.enrollmentStatus}"/></span></p>
                            </c:when>
                        </c:choose>
                        <p><span class="academics-sectionLeftWider">Units</span><span class="academics-sectionRight"><c:out value="${fn:replace(thisSection.creditHours,'Units','')}"/></span></p>
                        <c:if test="${not empty thisSection.sessionDescription && fn:toLowerCase(thisSection.sessionDescription) != 'regular'}">
                            <p><span class="academics-sectionLeftWider">Session</span><span class="academics-sectionRight"><c:out value="${thisSection.sessionDescription}"/></span></p>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${fn:length(thisSection.meetings) == 1}">
                        <c:forEach var="meeting" items="${thisSection.meetings}">
                            <p><span class="academics-sectionLeftWider">Start/End</span><span class="academics-sectionRight">${meeting.startDate} - ${meeting.endDate}</span></p>
                            <p><span class="academics-sectionLeftWider">Time</span><span class="academics-sectionRight">${meeting.days} ${meeting.times}</span></p>
                            <p><span class="academics-sectionLeftWider">Location</span><span class="academics-sectionRight">${meeting.location}</span></p>
                            <p><span class="academics-sectionLeftWider">Instructor</span><span class="academics-sectionRight wrap">
                                    <c:choose>
                                        <c:when test="${not empty meeting.instructors}">
                                            <c:forEach items="${meeting.instructors}" var="instruct" varStatus="instrStatus"><c:out value="${fn:replace(instruct,',',', ')}"/><br/></c:forEach>
                                        </c:when>
                                        <c:otherwise>TBA</c:otherwise>
                                    </c:choose>
                                </span></p>
                            <c:if test="${not empty meeting.topic}">
                                <p><span class="academics-sectionLeftWider">Topic</span><span class="academics-sectionRight wrap">${meeting.topic}</span></p>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:when test="${fn:length(thisSection.meetings) > 1}">
                        <c:set var="singleStartEnd" value="true"/>
                        <c:set var="oldStartEnd" value="NONE"/>
                        <c:forEach items="${thisSection.meetings}" var="meeting" varStatus="count">
                            <c:if test="${oldStartEnd eq 'NONE'}">
                                <c:set var="oldStartEnd" value="${meeting.startDate} - ${meeting.endDate}"/>
                            </c:if>
                            <c:set var="newStartEnd" value="${meeting.startDate} - ${meeting.endDate}"/>
                            <c:if test="${oldStartEnd != newStartEnd}">
                                <c:set var="singleStartEnd" value="false"/>
                            </c:if>
                            <c:set var="oldStartEnd" value="${newStartEnd}"/>
                        </c:forEach>
                        <c:if test="${singleStartEnd eq true}">
                            <p><span class="academics-sectionLeftWider">Start/End</span><span class="academics-sectionRight">${oldStartEnd}</span></p>
                        </c:if>
                        <c:forEach items="${thisSection.meetings}" var="meeting" varStatus="ctrMts">
                            <c:if test="${singleStartEnd eq false}">
                            <p><span class="academics-sectionLeftWider">Start/End</span><span class="academics-sectionRight">${meeting.startDate} - ${meeting.endDate}</span></p>
                            </c:if>
                            <p><span class="academics-sectionLeftWider">Time ${ctrMts.index +1}</span><span class="academics-sectionRight">${meeting.days} ${meeting.times}</span></p>
                            <p><span class="academics-sectionLeftWider">Location ${ctrMts.index +1}</span><span class="academics-sectionRight">${meeting.location}</span></p>
                            <p><span class="academics-sectionLeftWider">Instructor ${ctrMts.index +1}</span><span class="academics-sectionRight wrap">
                                    <c:choose>
                                        <c:when test="${not empty meeting.instructors}">
                                            <c:forEach items="${meeting.instructors}" var="instruct" varStatus="instrStatus"><c:out value="${fn:replace(instruct,',',', ')}"/><br/></c:forEach>
                                        </c:when>
                                        <c:otherwise>TBA</c:otherwise>
                                    </c:choose>
                               </span></p>
                            <c:if test="${not empty meeting.topic}">
                                <p><span class="academics-sectionLeftWider">Topic ${ctrMts.index +1}</span><span class="academics-sectionRight wrap">${meeting.topic}</span></p>
                            </c:if>
                            <c:if test="${ctrMts.index +1 < fn:length(thisSection.meetings)}">
                                <p class="smallSpace">&nbsp;</p>
                            </c:if>
                        </c:forEach>
                    </c:when>            
                    <c:otherwise>
                        <p><span class="academics-sectionLeftWider">Start/End</span><span class="academics-sectionRight">${thisSection.startDate} - ${thisSection.endDate}</span></p>
                        <p><span class="academics-sectionLeftWider">Time</span><span class="academics-sectionRight">TBA</span></p>
                        <p><span class="academics-sectionLeftWider">Location</span><span class="academics-sectionRight">TBA</span></p>
                        <p><span class="academics-sectionLeftWider">Instructor</span><span class="academics-sectionRight">TBA</span></p>
                    </c:otherwise>
                </c:choose>
                </kme:listItem>            
            </kme:listView>
        </div>
        <div id="tab2"class="ui-screen-hidden">
            <kme:listView dataTheme="c" dataDividerTheme="b" filter="false">
                <kme:listItem cssClass="academics-sectionCourseDescr">
                    <p class="academics-courseDetailLabel">Course Description</p>
                    <p class="wrap"><c:out value="${thisSection.courseDescription}" escapeXml="false"/></p>
                </kme:listItem>
            </kme:listView>
        </div>
        <div id="tab3"class="ui-screen-hidden">
            <kme:listView dataTheme="c" dataDividerTheme="b" filter="false">
                <kme:listItem cssClass="academics-sectionCourseDescr">
                    <p class="academics-courseDetailLabel">Course Detail</p>
                    <c:choose>
                        <c:when test="${not empty thisSection.additionalInfo}">
                            <p class="wrap"><c:out value="${thisSection.additionalInfo}" escapeXml="false"/></p>
                        </c:when>
                        <c:otherwise>
                            <p class="wrap"><c:out value="No additional details are available for the course at this time"/></p>
                        </c:otherwise>
                    </c:choose>
                </kme:listItem>
            </kme:listView>
        </div>

        <script>

            var prevSelection = "#tab1";
            $("#tabNav ul li").live("click", function() {
                var newSelection = $(this).children("a").attr("href");
                //console.log("newSelection=" + newSelection);
                //tab un-highlight and highlight only the buttons in the same navbar widget
                $(this).closest('ul').find('.ui-btn-active').removeClass('ui-btn-active');
                $(this).closest('ul').find('.ui-state-persist').removeClass('ui-state-persist');
                $(this).children("a").addClass("ui-btn-active");
                $(this).children("a").addClass("ui-state-persist");

                //tab content div
                $(prevSelection).addClass("ui-screen-hidden");
                $(newSelection).removeClass("ui-screen-hidden");

                prevSelection = newSelection;
            });
        </script>
    </kme:content>

    <kme:tabBar id="tabNav" showIcons="true" footer="true">
        <kme:tabBarItem url="#tab1" selected="true">Section</kme:tabBarItem>
        <kme:tabBarItem url="#tab2" >Description</kme:tabBarItem>
        <kme:tabBarItem url="#tab3" >Details</kme:tabBarItem>
    </kme:tabBar>

</kme:page>
