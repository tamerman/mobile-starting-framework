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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="news.noarticles" var="msgCat_NoArticles"/>

<kme:page title="${feed.title}" id="news" homeButton="true" backButton="true" cssFilename="news">
    <kme:content>
    	<c:choose>
    	<c:when test="${not empty feed.id}">
        <kme:listView id="newsArticles" dataTheme="c" dataDividerTheme="b" filter="false">

            <script id="newsArticleTemplate" type="text/x-jquery-templ">
                {{each newsSource.articles}}
                <li data-theme="c">
                    <a href="${pageContext.request.contextPath}/news/source?id=\${sourceId}&articleId=\${articleId}&referrer=stream" data-role="button" data-icon="arrow-r">
                        <p class="wrap">\${title}</p>
                    </a>
                </li>
                {{/each}}
                </script>
				
                <script type="text/javascript">
                    $(document).bind("pageinit", function() { 
                        console.log
                        $('[data-role=page][id=news]').live("pageshow", function() {
                            $('#newsArticleTemplate').template('newsArticleTemplate');
                            refreshTemplate('${pageContext.request.contextPath}/services/news/sources/getNewsSource/${feed.id}', 
                            '#newsArticles',
                            'newsArticleTemplate', 
                            '<li>${msgCat_NoArticles}</li>', 
                            function() {
                                $('.news-article').each(function(){
                                    $(this).jqmData('icon','arrow-r');
//                                    $(this).jqmData('theme','e');
                                });
                                $('#newsArticles').listview('refresh'); 
                            }
                        );     
                        });
                    });
                </script>      

            </kme:listView>
            </c:when>
            <c:otherwise>
            	<h2 align="center" style="color: red">${msgCat_NoArticles}</h2>
            </c:otherwise>
            </c:choose>
        </kme:content>
    </kme:page>