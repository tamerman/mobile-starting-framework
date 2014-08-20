<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
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
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="feedback.title" 	var="msgCat_ToolTitle"/>

<kme:page title="${msgCat_ToolTitle}" id="feedback_page" backButton="true" homeButton="true" cssFilename="feedback" appcacheFilename="iumobile.appcache" platform="${platform}" phonegap="${phonegap}">

<script type="text/javascript">
	var browserDetect 	= window.kme.browserDetect;
    var which = "feedback.default";

    
    // Only an other smart phone if it is running opera, or on a Symbian, WebOS or Nokia Phone, and is not overruled by any of the following conditions. 
    // Nothing defaults to "other" device, perhaps that should be the "value" of the "Select a Device" option.  
    
    // Probably over did this, in that I made it perhaps entirely too thorough for our current needs. -Mitch
    
    if(browserDetect.isBrowser(BrowserDetect.browsers.Opera) 
    || browserDetect.isOS(BrowserDetect.OSes.Symbian)
    || browserDetect.isOS(BrowserDetect.OSes.Nokia)
    || browserDetect.isOS(BrowserDetect.OSes.webOS)){
        which = "feedback.other";
    }
    // Checks if it is one of the iOS devices. 
    if(browserDetect.isIOS()){
        which = "feedback.iphone";
		// Also Change Visible text in the Select Options to reflect actual device. (It's the little things...)
        if(browserDetect.isOS(BrowserDetect.OSes.iPad)){
            which = "feedback.ipad";
        }
        if(browserDetect.isOS(BrowserDetect.OSes.iPod)){
            which = "feedback.ipod";
        }
    }
    // This will mark a phone as Android, even if the user is running Opera.
    if(browserDetect.isOS(BrowserDetect.OSes.Android)){
        which = "feedback.android";
    }
    // Guess what device this script doesn't work on?! Not in Simulator anyway.
    if(browserDetect.isOS(BrowserDetect.OSes.BlackBerry)){
        which = "feedback.blackberry";
    }
	// This is only coded from spec/doc. Not tested on device. 
    if(browserDetect.isBrowser(BrowserDetect.browsers.IEMobile)){
        which = "feedback.windowsMobile";
    }
	// Doesn't test for Linux, as most android devices show up as Linux in UA Strings.
	if(browserDetect.isDesktop()){
        which = "feedback.computer";
    } 
    // Must call after DOM is ready. 
    // This is shortcut for $(document).ready(...);
    $(function(){
        $("#deviceType option[value=\"" + which + "\"]").attr('selected', 'selected');
        $("#deviceType").selectmenu('refresh', true);

        var maxLength =$("textarea#noteText").attr("maxlength");
        $('textarea[name="noteText"]').after("<div><span id='remLengForOther'>" + maxLength + "</span> characters left</div>");
        $('textarea[name="noteText"]').bind("keyup change", function(){checkMaxLength("noteText", maxLength, "remLengForOther"); } );

    });


    function checkMaxLength(textareaName, maxLength, infoDiv){
        currentLengthInTextarea = $("textarea[name="+ textareaName +"]").val().length;
        $("#"+infoDiv).text(parseInt(maxLength) - parseInt(currentLengthInTextarea));

        if (currentLengthInTextarea > (maxLength)) {
            $("textarea[name="+ textareaName +"]").val($("textarea[name="+ textareaName +"]").val().slice(0, maxLength));
            $("#"+infoDiv).text(0);
        }
    }
</script>  

    <kme:content>
        <form:form action="${pageContext.request.contextPath}/feedback" commandName="feedback" data-ajax="false" method="post"> ${greeting}
            <%--hidden fields: <form:hidden path="eventId"/>--%>


			<%--
				This hidden field submits the entire userAgent string along with their feedback. 
				There were already feilds/columns in Feedback.java for storing userAgent, But I didn't
				see where it was being submitted or saved. 
				
				TODO: Check with legal regarding if this is ok regarding the Privacy Policy.
			 --%>
			<form:hidden path="userAgent" value="${header['User-Agent']}"/>
            <form:hidden path="campus" value="${cookie['campusSelection'].value}" />

            <fieldset>
                <div data-role="fieldcontain">
                    <label for="service" class="select"><spring:message code="feedback.subject" />:</label>
                    <form:select data-theme="c" path="service">
                        <%--<option value="N/A" selected="selected"> Select type:</option> --%>
                        <form:option value="General Feedback" selected="selected"><spring:message code="feedback.general" /></form:option>
                        <c:forEach var="homeTool" items="${tools}" varStatus="status">
                            <spring:message code="${homeTool.tool.title}" var="toolName" />
                            <form:option value="${toolName}">${toolName}</form:option>
                        </c:forEach>
                    </form:select>
                </div>

                <div data-role="fieldcontain">
                    <label for="deviceType" class="select"><spring:message code="feedback.devicetype" />:</label>
                    <form:select data-theme="c" path="deviceType">                    
	                    <c:forEach var="devicetype" items="${deviceTypes}" varStatus="status">
							<spring:message code="${devicetype.value}" var="deviceLabel"/>
                        	<form:option value="${devicetype.key}">${deviceLabel}</form:option>
		                </c:forEach>
                    </form:select>
                    <form:errors path="deviceType"/>
                </div> 
                <div data-role="fieldcontain">
                    <label for="noteText"><spring:message code="feedback.message" />:</label>
                    <form:textarea path="noteText" cols="40" rows="8" class="required" maxlength="2000" />
                    <form:errors path="noteText"/>
                </div>
                <div data-role="fieldcontain">
                    <label for="email"><spring:message code="feedback.youremail" />:</label>
                    <form:input path="email" type="text" value="" class="email"  />
                </div>
            </fieldset>
            
            <div data-inline="true">
                <div class="ui-grid-a">
                    <div class="ui-block-a">
                    	<a href="${pageContext.request.contextPath}/home" data-theme="c"  data-role="button"><spring:message code="feedback.cancel" /></a>
                    </div>
                    <div class="ui-block-b">
                        <input data-theme="a" class="submit" type="submit" value="<spring:message code="feedback.submit" />" />
                    </div>
                </div>
            </div>
        </form:form>
    </kme:content>
</kme:page>
