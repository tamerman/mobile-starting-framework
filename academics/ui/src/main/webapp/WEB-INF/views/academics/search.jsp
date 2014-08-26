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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="academics.search" var="title"/>
<kme:page title="${title}" id="academics" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics">
    <kme:content>
        <div class="formContainer">
            <form action="${pageContext.request.contextPath}/${toolContext}/classSearch" method="post" commandName="search" data-ajax="false">
                <div class="singleColumnGroup">
                    <span class="academicsFormLabel ui-li-heading">Term</span>
                    <span class="academicsFormInput">
                        <select name="termId" data-role="none" id="termId">
                            <c:forEach var="lTerm" items="${termId}">
                                <option value="${lTerm.id}">${lTerm.description}</option>
                            </c:forEach>
                        </select>
                    </span>
                </div>
                <div class="singleColumnGroup">
                    <span class="academicsFormLabel ui-li-heading">School/College</span>
                    <span class="academicsFormInput">
                        <select name="careerId" data-role="none" id="careerId">
                            <option value="0">Any</option>
                            <c:forEach var="lCareer" items="${careerId}">
                                <option value="${lCareer.id}">${lCareer.description}</option>
                            </c:forEach>
                        </select>
                    </span>
                </div>
                <div class="singleColumnGroup">
                    <span class="academicsFormLabel ui-li-heading">Subject</span>
                    <span class="academicsFormInput">
                        <select name="subjectId" data-role="none" id="subjectId" multiple="multiple">
                            <option value="0">Any</option>
                            <c:forEach var="lSubject" items="${subjectId}">
                                <option value="${lSubject.id}">${lSubject.description}</option>
                            </c:forEach>
                        </select>
                    </span>
                </div>
                <div class="singleColumnGroup">
                    <span class="academicsFormLabel ui-li-heading"></span>
                    <label>
                        <span class="textLabel">Course #</span>
                        <input type="text" name="catalogNumber" maxlength="4" size="4" data-role="none" style="width: 80px;" />
                    </label>
                </div>
                <div class="singleColumnGroup">
                    <span class="academicsFormLabel ui-li-heading">Instructor</span>
                    <span class="academicsFormInput">
                        <input type="text" name="instructor" size="20" data-role="none" />
                    </span>
                </div>

                <div class="inputContainer singleColumnGroup" id="divfilterCriteria">
		            <span class="academicsFormLabel">&nbsp;</span>
                    <div class="col1">
                        <input type="checkbox" id="c1" name="filterCriteria" data-role="none" value="100"/><label for ="c1"><span></span>&nbsp;100 Level</label>
                        <span class="academicsFormLabel ui-li-heading"></span>
                        <input type="checkbox" id="c2" name="filterCriteria" data-role="none" value="200"/><label for ="c2"><span></span>&nbsp;200 Level</label>
                        <span class="academicsFormLabel ui-li-heading"></span>
                        <input type="checkbox" id="c3" name="filterCriteria" data-role="none" value="300"/><label for ="c3"><span></span>&nbsp;300 Level</label>
                    </div>
                    <div class="col2">
                        <input type="checkbox" id="c4" name="filterCriteria" data-role="none" value="400"/><label for ="c4"><span></span>&nbsp;400 Level</label>
                        <span class="academicsFormLabel ui-li-heading"></span>
                        <input type="checkbox" id="c5" name="filterCriteria" data-role="none" value="500"/><label for ="c5"><span></span>&nbsp;500+ Level</label>
                    </div>
                </div>

                <div class="inputContainer singleColumnGroup">
                    <span class="academicsFormLabel">&nbsp;</span>
                    <input type="checkbox" id="showOpen" name="showOpen" data-role="none" checked/><label for="showOpen"><span></span>&nbsp;Show Open Classes Only</label>
                    <span class="academicsFormLabel ui-li-heading"></span>
                </div>

                <div id="footerSearch">
                    <input type="submit" name="submit" value="Search">
                </div>
            </form>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {
                var careers = {};
                <c:forEach var="lCareer" items="${careerId}">careers["${lCareer.id}"] = "${lCareer.description}";
                </c:forEach>
                var subjects = {};
                <c:forEach var="lSubject" items="${subjectId}">subjects["${lSubject.id}"] = "${lSubject.description}";
                </c:forEach>

                $.fn.buildCareerList = function (iTerm) {
                    var optionList = "<option value=\"0\" selected>Any</option>";
                    if (iTerm != null && iTerm != "0") {
                        $.ajax({
                            type: 'Get',
                            url: '${pageContext.request.contextPath}/services/academics/getCareers?termId=' + iTerm + '&_type=json',
                            dataType: 'json',
                            success: function (data) {
                                for (i = 0; i < data.academicCareer.length; i++) {
                                    var localCareer = data.academicCareer[i];
                                    optionList += "<option value='" + localCareer.id + "'>" + localCareer.description + "</option>";
                                }
                                $("select#careerId").html(optionList);
                            },
                            failure: function (data) {
                                for (var key in careers) {
                                    optionList += "<option value='" + key + "'>" + careers[key] + "</option>";
                                }
                                $("select#careerId").html(optionList);
                            }
                        });
                    } else {
                        for (var key in careers) {
                            optionList += "<option value='" + key + "'>" + careers[key] + "</option>";
                        }
                    }
                    $("select#careerId").html(optionList);
                };
                $.fn.buildSubjectList = function (iTerm, iCareer) {
                    var optionList = "<option value=\"0\" selected>Any</option>";
                    if (iCareer != null && iCareer != "0") {
                        $.ajax({
                            type: 'Get',
                            url: '${pageContext.request.contextPath}/services/academics/getSubjects?termId=' + iTerm + '&careerId=' + iCareer + '&_type=json',
                            dataType: 'json',
                            success: function (data) {
                                for (i = 0; i < data.subject.length; i++) {
                                    var localSubject = data.subject[i];
                                    optionList += "<option value='" + localSubject.id + "'>" + localSubject.description + "</option>";
                                }
                                $("select#subjectId").html(optionList);
                            },
                            failure: function (data) {
                                for (var key in subjects) {
                                    optionList += "<option value='" + key + "'>" + subjects[key] + "</option>";
                                }
                                $("select#subjectId").html(optionList);
                            }
                        });
                    } else {
                        for (var key in subjects) {
                            optionList += "<option value='" + key + "'>" + subjects[key] + "</option>";
                        }
                    }
                    $("select#subjectId").html(optionList);
                };

                $("select#termId").change(function () {
                    $.fn.buildCareerList(this.value);
                    $.fn.buildSubjectList(this.value, null);
                });
                $("select#careerId").change(function () {
                    $.fn.buildSubjectList($("select#termId").val(), this.value);
                });

                $("input[name=catalogNumber]").change(function () {
                    if (this.value == "") {
                        $("#divfilterCriteria").removeClass("ui-disabled");
                    }
                    else {
                        $('input[name=filterCriteria]').removeAttr('checked');
                        $("#divfilterCriteria").addClass("ui-disabled");
                    }
                });

            });
        </script>
    </kme:content>
</kme:page>
