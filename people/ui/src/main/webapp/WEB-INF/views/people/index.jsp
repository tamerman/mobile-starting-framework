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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:message code="people.affiliation" var="affiliation"/>



<spring:message code="people.submit" var="submit"/>
<spring:message code="people.searchWatermark" var="searchWatermark"/>
<spring:message code="people.exactMatchLabel" var="exactMatchLabel"/>
<spring:message code="people.peopleLabel" var="peopleLabel"/>
<spring:message code="people.groupsLabel" var="groupsLabel"/>
<spring:message code="people.groupDescription" var="groupDescription"/>
<spring:message code="people.noResultsFoundMessage" var="noResultsFoundMessage"/>
<spring:message code="people.serviceErrorMessage" var="serviceErrorMessage"/>
<spring:message code="people.isExactly" var="msgCat_IsExactly"/>
<spring:message code="people.startsWith" var="msgCat_StartsWith"/>


<kme:page title="Directory" id="directory" backButton="true" homeButton="true" cssFilename="directory">
    <style>
        div#div2{
            display:none;
        }
        .ui-field-contain div.ui-slider-switch{
            left:134px;top:2px;width:50%;
        }
    </style>
    <kme:content>
		<c:if test="${enableAdvancedSearchToggle}"><button id="togglebtn"><spring:message code="people.advancedSimpleSearch" /></button></c:if>
        <div id="${basicSearchDiv}" class="ui-content ui-body-b" data-theme="b">
            <form:form action="${pageContext.request.contextPath}/people" method="post" commandName="search" data-ajax="false">
                <fieldset>                	
                    <label for="searchText" style="position:absolute; left:-9999px;"><spring:message code="people.searchUseage" /></label>
                    <form:input path="searchText" type="search" cssClass="text ui-widget-content ui-corner-all" placeholder="${searchWatermark}" />
                    <form:errors path="searchText" />
                </fieldset>
                <input type="hidden" id="basicSearch" name="searchtype" value="BasicSearch" />
            </form:form>
        </div>
        <div id="${advancedSearchDiv}">
            <form:form id="form2" action="${pageContext.request.contextPath}/people" commandName="search" data-ajax="false" method="post">

                <div data-role="fieldcontain">
                    <label for="lastName"><spring:message code="people.lastName" />:</label>
                    <form:input path="lastName" type="text" value="" />
                    <div class="error"><form:errors path="lastName"/></div>

                    <div id="peopleSlider">
                        <fieldset data-theme="c" data-role="controlgroup" data-type="horizontal" >
                            <label for="slider"></label>
                            <form:select data-theme="c" path="exactness" id="slider" data-role="slider">
                                <form:option data-theme="c" value="starts" label="${msgCat_StartsWith}" />
                                <form:option data-theme="c" value="exact" label="${msgCat_IsExactly}" />
                            </form:select>
                        </fieldset>
                    </div>
                    <label for="firstName"><spring:message code="people.firstName" />:</label>
                    <form:input path="firstName" type="text" value=""  />
                </div>
                <div data-role="fieldcontain">
                    <form:select data-theme="c" path="status" multiple="false" items="${statusTypes}" />
                    <form:select data-theme="c" path="location" multiple="false" items="${locations}" />
                </div>
                <div data-role="fieldcontain">
                    <label for="userName"><spring:message code="people.username" />:</label>
                    <form:input path="userName" type="text" value="" />
                    <div class="error"><form:errors path="userName"/></div>
                </div>
                <div data-inline="true">
                    <div class="ui-grid-a">
                        <div class="ui-block-a">
                        	<a data-theme="c"  href="${pageContext.request.contextPath}" data-role="button"><spring:message code="people.cancel" /></a>
                        </div>
                        <div class="ui-block-b">
                            <input data-theme="a" class="submit" type="submit" value="${submit}" />
                        </div>
                    </div>
                </div>
                <input type="hidden" id="advancedSearch" name="searchtype" value="AdvancedSearch" />
            </form:form>
        </div>
        <div id="searchresults">
            <kme:listView id="peopleList" filter="false" dataTheme="c" dataInset="false">
            </kme:listView>
        </div>
    </kme:content>
    <script type="text/javascript">
        $(document).ready( function() {
            if( sessionStorage ) {
                var results = JSON.parse( sessionStorage.getItem( "people_search_results" ) );
                if( results != null ) {
                    $("#searchText").val( results.searchResults.searchCriteria.searchText );
                    $.fn.showResults( results );
                }
            }
            $("#togglebtn").click(function() {
                $("#div1").toggle();
                $("#div2").toggle();
            });
        });
        $('#search').submit( function(e) {
            var responseData, personObj;
            var html = "";
            e.preventDefault();
            e.stopPropagation();
            responseData = null;

            $("#searchText").blur();
            $.mobile.showPageLoadingMsg();
            $.ajax( {
                type:'Post',
                url:'${pageContext.request.contextPath}/services/directory/search?_type=json',
                data:$('#search').serialize(),
                dataType:'json',
                success:function(data) {
                    $.mobile.hidePageLoadingMsg();
                    $.fn.showResults(data);
                },
               error:function(data) {
                             $.mobile.hidePageLoadingMsg();
                             $.fn.showErrors(data);
                             return false;
                            }

        });
})
        // This helper will build\format the HTML for a person.
        function buildPersonDisplay(personObj) {
            var htmlStr = "";
            htmlStr += '<li class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-btn-up-c" data-theme="c">';
            htmlStr += '<div class="ui-btn-inner ui-li"><div class="ui-btn-text">';
            htmlStr += '<a class="ui-link-inherit" href="${pageContext.request.contextPath}/people/' + personObj.userName + '">';
            htmlStr += '<h3 class="ui-li-heading">' + (typeof personObj.displayName != 'undefined' ? personObj.displayName : personObj.userName ) + "</h3>";

            if (personObj.affiliations) {
                htmlStr += '<p class="ui-li-desc"><strong>${affiliation}:&nbsp;</strong>';
                var theAffiliations = personObj.affiliations;
				if (theAffiliations instanceof Array) {
                    $.each(theAffiliations, function(index, value) {
                        if (index < (theAffiliations.length -1)) {
                            htmlStr += value + ",&nbsp; ";
                        } else {
                            htmlStr += value;
                        }
                    });
                } else {
                    htmlStr += theAffiliations;
                }

                htmlStr += "</p>";
            }

            htmlStr += "</a></div></div></li>";

            return htmlStr;
        }

        $.fn.showResults = function(data) {
            responseData = data;
            var html = "";
            var results = responseData.searchResults;
            var people = results.people;
            var groups = results.groups;
            var exactMatchFound = false;
            var peopleListHtml = "";
            var groupObj;

            if ( sessionStorage ) {
                sessionStorage.setItem( "people_search_results", JSON.stringify(responseData) );
            }

            // TODO: We should really do the empty and the spinner when the AJAX
            // call is made, instead of here...
            $("#peopleList").empty();

            if ( people == null || people.length == 0 ) {
                $("#peopleList")
                .append("<li data-role=\"list-divider\" data-theme=\"b\" data-icon=\"listview\" role=\"heading\" class=\"ui-li ui-li-divider ui-btn ui-bar-b ui-btn-up-b\">${peopleLabel}</li>" +
                    "<li data-theme=\"c\" class=\"ui-li ui-li-static ui-body-c\"><div class=\"wrap ui-li\"><div class=\"wrap ui-btn-text\"><h3 class=\"ui-li-heading\">${noResultsFoundMessage}</h3></div></div></li>");
            } else {

                // Exact match on network ID block
                //   - Assume if an exact match exists, it's first in the response.
                personObj = ( people[0] );
                if ( personObj ) {
                    if ( personObj.userName != null &&
                        (personObj.userName.toLowerCase()) == (responseData.searchResults.searchCriteria.searchText.toLowerCase()) ) {
                        exactMatchFound = true;
                        html += "<li data-role=\"list-divider\" data-theme=\"b\" data-icon=\"listview\" role=\"heading\" class=\"ui-li ui-li-divider ui-btn ui-bar-b ui-btn-up-b\">${exactMatchLabel}</li>";
                        html += buildPersonDisplay(personObj);
                    }
                }

                // Person results block
                //   - Don't repeat the exact match person
                //   - If we have only one result and it's an exact match, break.
                html += "<li data-role=\"list-divider\" data-theme=\"b\" data-icon=\"listview\" role=\"heading\" class=\"ui-li ui-li-divider ui-btn ui-bar-b ui-btn-up-b\">${peopleLabel}</li>";
                $.each(people, function(i) {
                    if (i === 0 && exactMatchFound) {
                        i++;
                        if (i == people.length) {
                            html +="<li data-theme=\"c\" class=\"ui-li ui-li-static ui-body-c\"><div class=\"wrap ui-li\"><div class=\"wrap ui-btn-text\"><h3 class=\"ui-li-heading\">${noResultsFoundMessage}</h3></div></div></li>";
                         }

                        return (people.length != 1);
                    }

                    if ( "Error" == people[i].displayName ) {
                        html += "<li data-theme=\"c\" class=\"ui-li ui-li-static ui-body-c\"><div class=\"wrap ui-li\"><div class=\"wrap ui-btn-text\">" + people[i].affiliations + "</div></div></li>";
                    } else {
                        personObj = (people[i]);
                        if ( personObj ) {
                            html += buildPersonDisplay( personObj );
                        }
                    }
                });

                $("#peopleList").append(html);
            }

            // Groups block
            $("#peopleList")
            .append("<li data-role=\"list-divider\" data-theme=\"b\" data-icon=\"listview\" role=\"heading\" class=\"ui-li ui-li-divider ui-btn ui-bar-b ui-btn-up-b\">${groupsLabel}</li>");

            if ( groups == null || groups.length == 0 ) {
                $("#peopleList").append("<li data-theme=\"c\" class=\"ui-li ui-li-static ui-body-c\"><div class=\"ui-li\"><div class=\"ui-btn-text\"><h3 class=\"ui-li-heading\">${noResultsFoundMessage}</h3></div></div></li>");
            } else {
                $.each(groups, function(j) {
                    groupObj = (groups[j]);
                    if (groupObj) {
                        if ( "Error" == groupObj.displayName ) {
                            html = "<li data-theme=\"c\" class=\"ui-li ui-li-static ui-body-c\"><div class=\"wrap ui-li\"><div class=\"wrap ui-btn-text\">" + groupObj.descriptions + "</div></div></li>";
                        } else {
                            html = '<li class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-btn-up-c" data-theme="c">';
                            html += '<div class="ui-btn-inner ui-li"><div class="ui-btn-text">';
                            html += '<a class="ui-link-inherit" href="${pageContext.request.contextPath}/people/group/' + groupObj.displayName + '">';
                            html += '<h3 class="ui-li-heading">' + groupObj.displayName + "</h3>";
                            if (groupObj.descriptions) {
                                html += '<p class="ui-li-desc"><strong>${groupDescription}:</strong>&nbsp;' + groupObj.descriptions + "</p>";
                            }

                            html += "</a></div></div></li>";
                        }
                        $("#peopleList").append(html);
                    }
                });
            }

            $("#peopleList").listview("refresh");
        }

        $.fn.showErrors = function(data) {
          var htmlErr = "";
          htmlErr = "<li data-theme=\"c\" class=\"ui-li ui-li-static ui-body-c\"><div class=\"wrap ui-li\"><div class=\"wrap ui-btn-text\"><h3 class=\"ui-li-heading\">${serviceErrorMessage}</h3></div></div></li> ";
           $("#peopleList").empty();
          $("#peopleList").append(htmlErr);
          $("#peopleList").listview("refresh");
        }

    </script>
</kme:page>
