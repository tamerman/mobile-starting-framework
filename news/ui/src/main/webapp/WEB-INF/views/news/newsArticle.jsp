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