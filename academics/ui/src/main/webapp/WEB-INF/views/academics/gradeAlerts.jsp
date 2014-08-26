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

<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<spring:message code="academics.title" var="title"/>

<kme:page title="Grade Alerts" id="academics" backButton="true" homeButton="true" cssFilename="academics"
          jsFilename="academics" backButtonURL="${backButtonURL}">
    <kme:content>
        <div class="wrap">When Grade Alerts are enabled, you will
        be sent a notification when your end of term grade is posted in Wolverine Access.</div>
        <br>
        <div style="text-align:center">
            <form>
                <label for="gradeAlertOpt" style="font-weight:bold">Enable Grade Alerts?</label>
                <select name="gradeAlertOpt" id="gradeAlertOpt" data-role="slider" data-mini="true" data-autosubmit="true">
                    <option value="off">No</option>
                    <c:choose>
                        <c:when test="${gradeAlertOpt=='on'}"><option value="on" selected>Yes</option></c:when>
                        <c:otherwise><option value="on">Yes</option></c:otherwise>
                    </c:choose>
                </select>
            </form>

            <span class="wrap" id="updateResult"></span>
        </div>

        <div id="divTestPush" class="ui-screen-hidden">
            <c:if test="${isNative and (platform == 'Android' or platform == 'iOS')}">
                <input type="button" value="Test Grade Alert" id="submitButton"/>
                <span class="wrap ui-screen-hidden" id="testResult0">Something wrong, not able to test.</span>
                <span class="wrap ui-screen-hidden" id="testResult1">Your device has not been registered yet.
                    Because registration sometimes may take several minutes to complete, we
                    suggest you try again in 5 minutes. If you continue to see this message,
                    you may visit the Knowledge Base for <a
                        href="https://umichprod.service-now.com/kb_view.do?sysparm_article=KB0015726">Android</a> or <a
                        href="https://umichprod.service-now.com/kb_view.do?sysparm_article=KB0015721">iOS</a> support articles.
                </span>
                <span class="wrap ui-screen-hidden" id="testResult2">Thank you - you should receive a test grade
                    alert within 1-2 minutes. If you do not receive an alert, you may visit the Knowledge Base for <a
                            href="https://umichprod.service-now.com/kb_view.do?sysparm_article=KB0015726">Android</a> or <a
                            href="https://umichprod.service-now.com/kb_view.do?sysparm_article=KB0015721">iOS</a> support articles.
                </span>

                <script type="text/javascript">
                    $("#submitButton").click(function() {
                        $.ajax( {
                            type: 'Get',
                            url: '${pageContext.request.contextPath}/services/myAcademics/testGradeAlert' ,
                            dataType: 'json',
                            success:function(data) {
                                if (data == "1") {
                                    $("#testResult1").removeClass("ui-screen-hidden");
                                }
                                else if (data == "2") {
                                    $("#testResult2").removeClass("ui-screen-hidden");
                                }
                             },
                            failure:function(data) {
                                $("#testResult0").removeClass("ui-screen-hidden");
                                $("#testResult1").addClass("ui-screen-hidden");
                                $("#testResult2").addClass("ui-screen-hidden");
                            }
                        } );

                    });
                </script>

            </c:if>

        </div>

        <br>

        <div class="wrap"><em>You must allow push notifications in your device settings for notifications to be sent to
            this device.<br><br>You must have the latest version of the
            Michigan app installed on your device to properly receive notifications. Please see the <a
                    href="https://play.google.com/store/apps/details?id=edu.umich.michigan">Android</a> or <a
                    href="https://itunes.apple.com/us/app/university-of-michigan/id380339596">iOS</a> store.</em>
        </div>

        <script type="text/javascript">
            $(document).ready( function() {
                // display only from native iOS and Android
                if ( $("select#gradeAlertOpt").val() == "on" ) {
                    $("#divTestPush").removeClass("ui-screen-hidden");
                }
                else {
                    $("#divTestPush").addClass("ui-screen-hidden");
                }


                $("select#gradeAlertOpt").change( function() {
                    var optValue = this.value;
                    var updateResult = "Failed to update, please try again later.";

                    $.ajax( {
                        type: 'Get',
                        url: '${pageContext.request.contextPath}/services/myAcademics/updateGradeAlertOpt?opt='+optValue+'&_type=json' ,
                        dataType: 'json',
                        success:function(data) {
                            if (data==true || data=="true") {
                                // Not display the successful result
                                if ( optValue == "on" ) {
                                    $("#divTestPush").removeClass("ui-screen-hidden");
                                    if ( $("#testResult0").length ) {
                                        $("#testResult0").addClass("ui-screen-hidden");
                                        $("#testResult0").addClass("ui-screen-hidden");
                                        $("#testResult1").addClass("ui-screen-hidden");
                                        $("#testResult2").addClass("ui-screen-hidden");
                                    }
                                    //updateResult = "Your Grade Alerts have been turned on.";
                                }
                                else {
                                    $("#divTestPush").addClass("ui-screen-hidden");
                                    //updateResult = "Your Grade Alerts have been turned off.";
                                }
                            }
                            else {
                                $("#updateResult").html(updateResult);
                            }
                        },
                        failure:function(data) {
                            $("#updateResult").html(updateResult);
                        }
                    });

                });

            });


        </script>

    </kme:content>

</kme:page>
