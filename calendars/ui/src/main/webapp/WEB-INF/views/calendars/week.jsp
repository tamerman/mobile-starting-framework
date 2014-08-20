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

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme:page title="Calendars" id="Calendars-Events" backButton="true" homeButton="true" cssFilename="weekcalendar" backButtonURL="${pageContext.request.contextPath}/calendarsCalender">


    <link rel='stylesheet' type='text/css' href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/start/jquery-ui.css' />
    <link rel='stylesheet' type='text/css' href='css/jquery.weekcalendar.css' />
    <script type='text/javascript' src='js/jquery.min.js'></script>
    <script type='text/javascript' src='js/jquery-ui.min.js'></script>
    <script type='text/javascript' src='js/jquery.weekcalendar.js'></script>
    <script type='text/javascript' src='js/demo.js'></script>
    <script type='text/javascript' src='js/jquery.ui.touch-punch.min.js'></script>


    <kme:content>
        <style type="text/css">
            .ui-select .ui-btn select {
                opacity: 1;
            }
            .ui-icon-home {
                background-image: url("images/icons-18-white.png");
                background-position: -577px 50%;
            }
            .ui-icon-back {
                background-image: url("images/icons-18-white.png");
                background-position: -396px 50%;
            }
            .ui-select .ui-btn-icon-right .ui-btn-inner, .ui-select .ui-li-has-count .ui-btn-inner {
                display: table-row;
                height: 20px;
                padding-right: 45px;
            }
            .ui-dialog {
                padding: 0.8em;
                position: relative;
                width: 300px;
            }
            .ui-dialog .ui-dialog-buttonpane {
                background-image: none;
                border-width: 1px 0 0;
                margin: 0;
                padding: 0;
                text-align: left;
            }           

            .ui-dialog .ui-dialog-content {
                background: none repeat scroll 0 0 transparent;
                border: 0 none;
                overflow: auto;
                padding: 0 0.5em;
            }
            .ui-widget {
                font-family: Verdana,Arial,sans-serif;
                font-size: 0.8em;
            }
            .ui-widget select{
                font-family: Verdana,Arial,sans-serif;
                font-size: 1em;
            }
            .ui-widget-header {
                background: none repeat scroll 0 0 #7D110C;
                border: 1px solid #7D110C;
                color: #EAF5F7;
                font-weight: bold;
            }
            .ui-state-default, .ui-widget-content .ui-state-default {
                background: none repeat scroll 0 0 #7D110C;
                border: 1px solid #7D110C;
                color: #FFFFFF;
                font-weight: normal;
                outline: medium none;
            }
        </style>
       
        <div id="synchronizationStatus">${param.synchronizationStatus}</div>

        <input type="button" id="googlebutton" name="googlebutton" value="Sync with Google">

        <div id="googlediv" style="display:none">
            <h2 class="text1">Google User Credentials</h2>
            <form id="googleform" action="${pageContext.request.contextPath}/calendarsCalender/calendarsSynchronizeWithGoogleeAccount" commandName="userAccount" data-ajax="false" method="post">
                <div data-theme="b">
                    Email:<input type="email" id="emailId" name="emailId" required/>
                    Password:<input type="password" id="password" name="password" required />
                </div>
                <div data-inline="true">
                    <input type="submit" value="Submit"/>
                    <a href="${pageContext.request.contextPath}/calendar" data-role="button">Cancel</a>
                </div>
            </form>
        </div>

        <div><input type="text" name="caltitle" id="caltitle" readonly="true"/></div>
        <!-- <div>(yyyy-mm-dd)</div> -->

        <div id='calendar'></div>

        <div id="event_edit_container" style="display: none">
        
            <form>
                <div id="eventdiv"></div>
                <ul style="list-style-type: none;">
                	<li><span>Date: </span> <span class="date_holder"></span></li>
                    <li><label>Start Time: </label><select name="start"><option></option></select></li>
                    <li><label>End Time: </label><select name="end"><option></option></select></li>
                    <li><label>Title: </label><input type="text" name="title" /></li>
                    <li><label>Body: </label><textarea name="body"></textarea></li>
                    <li><input type="hidden" name="id" value="" /></li>					 
                </ul>               
            </form>
            
        </div>
         
    </kme:content>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#googlebutton").click(function(){
                $("#googlediv").toggle();
            });       
        });
    </script>

    <script>
        $('#widget').draggable();
    </script>
    
</kme:page>