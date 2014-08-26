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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<spring:message code="news.title" var="msgCat_ToolTitle"/>
<spring:message code="news.fullarticle" var="msgCat_FullArticle"/>


<kme:page title="${msgCat_ToolTitle}" id="news" homeButton="true" backButton="true" cssFilename="news">
    <kme:content cssClass="news-story">
        <script type="text/javascript">
        $(document).ready( function() {
            $.ajax( {
                type:'Get',
                url:'${pageContext.request.contextPath}/services/news/articles?&articleId=${article.articleId}&_type=json',
                dataType: 'json',
                success:function(data) {

                    $('#articletitle').html(data.article.title);
                    $('#publishDate').html(data.article.publishDateDisplay);
                    $('#description').html(data.article.description);
                    $('#fullArticleLink').attr('href', data.article.link);
                }
            })
        });
        </script>
		<h3 id="articletitle" class="wrap"></h3>
		<h4 id="publishDate"></h4>
		<p id="description" class="wrap"></p>
		<p></p>
		<p><a href="" id="fullArticleLink"> ${msgCat_FullArticle} </a></p>
    </kme:content>
</kme:page>