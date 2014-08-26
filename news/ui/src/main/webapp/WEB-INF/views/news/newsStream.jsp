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