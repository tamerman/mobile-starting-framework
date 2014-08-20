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
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="dining.title" var="title"/>

<kme:page title="${place}" id="dining" backButton="true" homeButton="true" cssFilename="dining" jsFilename="dining">
    <kme:content>
        <ul data-role="listview" id="dropdownHeader" data-theme="c" data-inset="false" data-filter="false" data-dividertheme="b">
            <li data-role="list-divider" data-theme="b" data-icon="arrow-d" class="dropdownHeader">
                <a id="toggleDropdown" href="#">Select a Menu</a></li>
        </ul>

        <kme:listView  id="dropdownMenu" dataTheme="c" dataInset="false" dataDividerTheme="b" filter="false">
            <script type="text/javascript">
                $('[data-role=page][id=dining]').live('pagebeforeshow', function(event, ui) {
                                $('#dropdownMenuTemplate').template('dropdownMenuTemplate');
                    refreshTemplate('${name}?location=${location}', '#dropdownMenu', 'dropdownMenuTemplate', '<li>No Menus to select</li>',
                    function() {$('#dropdownMenu').listview('refresh');
                    });

                                $('#menuListTemplate').template('menuListTemplate');
                    refreshTemplate('${name}?location=${location}', '#results', 'menuListTemplate', '<li>No Menus</li>',
                    function() {$('#results').listview('refresh');
                    });
                });
            </script>

            <script id="dropdownMenuTemplate" type="text/x-jquery-tmpl">
                {{each(i,m) meal}}
                <li data-theme="c" class="dropdownItem" value="\${i + 1}">
                    <h3 class="wrap">\${name}</li></h3>
                {{/each}}
                </script>
            </kme:listView>

            <kme:listView id="results" filter="false" dataTheme="c" dataInset="false" dataDividerTheme="b">

                <script id="menuListTemplate" type="text/x-jquery-tmpl">
                    {{each(i,m) meal}}
                    {{if categories}}
                    {{each categories}}
                    <li class="contentItem category-\${i + 1}" data-role="list-divider" data-theme="b">\${title}
                        {{if quantities}}
                        {{each quantities}}
                        <em> -\${$value}</em>
                        {{/each}}
                        {{/if}}
                    </li>
                    {{each items}}
                    <li class="contentItem category-\${i + 1}" data-theme="c">

                        <h3 class="wrap">\${this.title}</h3>
                        {{if prices}}
                        <p class="wrap">
                            {{each prices}}
                            <em>\${$value} </em>
                            {{/each}}
                        </p>
                        {{/if}}
                        {{if attributes}}
                        <div class="nutritionIcons">
                            {{each attributes}}
                            <span class="nutrition nutrition-\${$value}">\${$value}</span>
                            {{/each}}
                        </div>
                        {{/if}}
                    </li>
                    {{/each}}
                    {{/each}}
                    {{else}}
                    <li class="contentItem category-\${i + 1}" data-role="list-divider" data-theme="b">Not served</li>
                    {{/if}}

                    {{/each}}
                    </script>

                </kme:listView>

            </kme:content>

            <div id="legend">
                <span class="closeLegend">Close</span>
                <ul id="legendItems">
                    <li><span class="nutrition-featured">Featured</span></li>
                    <li><span class="nutrition-glutenfree">Gluten-free</span></li>
                    <li><span class="nutrition-halal">Halal</span></li>
                    <li><span class="nutrition-mhealthy">MHealthy</span></li>
                    <li><span class="nutrition-vegan">Vegan</span></li>
                    <li><span class="nutrition-vegetarian">Vegetarian</span></li>
                </ul>
            </div>

        </kme:page>