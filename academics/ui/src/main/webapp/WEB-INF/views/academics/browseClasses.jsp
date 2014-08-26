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
<spring:message code="academics.title" var="title"/>
<spring:message code="academics.terms" var="terms"/>
<spring:message code="academics.careers" var="careers"/>
<spring:message code="academics.subjects" var="subjects"/>
<spring:message code="academics.catalogNumbers" var="catalogNumbers"/>

<c:choose>
<c:when test="${stage == 'catalogNumber'}">
<kme:page title="${subject}" id="academics-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics" backButtonURL="${pageContext.request.contextPath}/${toolContext}/${backButtonURL}">
<kme:content>
              <div align='center'>
	           <form id="search" data-ajax="false" action="${pageContext.request.contextPath}/${toolContext}/classSearch" method="post">
		       <fieldset>
		       <input id="searchCriteria" name="searchCriteria"  type="text" class="text ui-widget-content ui-corner-all" placeholder="${watermark}" value=""/>
		       <input id="termId" name="termId" type="hidden" value="${termId}"/>
		       <input id="termDescription" name="termDescription" type="hidden"  value="${termDescription}"/>
		       </fieldset>
               </form>
              </div>

			<kme:listView id="catalogNumberList" dataTheme="c" dataDividerTheme="b" filter="false">
				</kme:listView>
				</kme:content>
				<script type="text/javascript">
					$.fn.loadCatalogNumbers = function() {
					var academics;
                    var termId = ${termId};
                    var termDescription="";
					if( sessionStorage ) {
						academics = JSON.parse( sessionStorage.getItem("academics") );
						if( academics == null || academics == "" ) {
							academics = {};
						}
                        //console.log( academics['termDescription']);
                        termDescription = academics['termDescription'];
                        $('#search input[name="termId"]').val(termId);
                        $('#search input[name="termDescription"]').val(academics['termDescription']);
                        $('#search input[type=text]').attr('placeholder','Search ' + academics['termDescription'] + ' Classes' );
					}
					$.ajax( {
						type:'Get',
						url:'${pageContext.request.contextPath}/services/${toolContext}/getCatalogNumbers?termId='+academics['termId']+'&subjectId='+academics['subjectId']+'&_type=json',
						dataType:"json",
						success:function(data) {
							$('#catalogNumberList').empty();
							for( i = 0; i < data.catalogNumber.length; i++ ) {
								var catalogNumber = data.catalogNumber[i];
								//console.log('catalognumber descr: ' + catalogNumber.description);
								$('#catalogNumberList').append(
								"<li data-theme=\"c\" data-icon=\"listview\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-icon ui-btn-up-c\"><div class=\"ui-btn-inner ui-li\"><div class=\"ui-btn-text\"><a href=\"${pageContext.request.contextPath}/${toolContext}/sections?termId="+academics['termId']+"&careerId="+academics['careerId']+"&subjectId="+academics['subjectId']+"&catalogNumber="+catalogNumber.number+"&catalogDescription="+encodeURIComponent(catalogNumber.description)+"&termDescription="+academics['termDescription']+"\" class=\"ui-link-inherit\" data-transition=\"slide\" data-direction=\"forward\"><h3 class=\"ui-li-heading\">"+academics["subjectId"]+" "+catalogNumber.number+"</h3><p>"+catalogNumber.description+"</p></a></div><span class=\"ui-icon ui-icon-arrow-r ui-icon-shadow\"></span></div></li>");
							}
							if( data.catalogNumber.length == 0 ) {
								$('#catalogNumberList').append(
								"<li data-theme=\"c\" data-icon=\"listview\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-icon ui-btn-up-c\"><div class=\"ui-btn-inner ui-li\"><div class=\"ui-btn-text\"><h3 class=\"ui-li-heading\">No results found</h3></div></div></li>");
							}
							try {
								$('#catalogNumberList').listview('refresh');
							} catch(err) {

							}
							$("#catalogNumberList li").each(function (index,item) {
								$(item).bind("vclick",function(e) {
									if( sessionStorage ) {
										var academics;
										academics = JSON.parse( sessionStorage.getItem("academics") );
										if( academics == null || academics == "" ) {
											academics = {};
										}
										var targetId = e.target.id;
										var mark = targetId.indexOf(":");
										academics['catalogNumber'] = targetId.substring(0,mark);
										academics['catalogNumberDesc'] = targetId.substring(mark+1);
										sessionStorage.setItem( "academics", JSON.stringify(academics) );
									}
									//$.fn.loadSections();
								});
							});
						},
						failure:function(data) {
							$('#catalogNumberList').append(
							"<li data-theme=\"c\" data-icon=\"listview\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-icon ui-btn-up-c\"><div class=\"ui-btn-inner ui-li\"><div class=\"ui-btn-text\"><h3 class=\"ui-li-heading\">No results found</h3></div></div></li>");
						}
					});
				};

				$('#academics-ui').live(
				'pageinit', $.fn.loadCatalogNumbers() );
			</script>
		</kme:page>
	</c:when>
	<c:when test="${stage == 'career'}">
		<kme:page title="${careers}" id="academics-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics" backButtonURL="${pageContext.request.contextPath}/${toolContext}/${backButtonURL}">
	        <kme:content>
              	<div align='center'>
	           	<form id="search" data-ajax="false" action="/michigan/${toolContext}/classSearch" method="post">
	           	<fieldset>
	           	<input id="searchCriteria" name="searchCriteria" type="text" class="text ui-widget-content ui-corner-all" placeholder=""/>
	           	<input id="termId" name="termId" type="hidden" value="${termId}"/>
	         	<input id="termDescription" name="termDescription" type="hidden" value="${termDescription}"/>
	           	</fieldset>
               	</form>
              	</div>

				<kme:listView id="careerList" dataTheme="c" dataDividerTheme="b" filter="false">
					<script id="careerTemplate" type="text/x=jquery-templ">
						{{each academicCareer}}
						<kme:listItem><a href="${pageContext.request.contextPath}/${toolContext}/browseClasses?stage=subject&careerId=\${id}" id="\${id}" data-transition=\"slide\" data-direction=\"forward\">\${description}</a></kme:listItem>
						{{/each}}
					</script>
				</kme:listView>
			</kme:content>
			<script type="text/javascript">
				$.fn.loadCareers = function() {
						var termId = 0;
                        var termDescription="";
						if( sessionStorage ) {
							var academics;
							academics = JSON.parse( sessionStorage.getItem("academics") );
							if( academics == null || academics == "" ) {
								academics = {};
							}
							termId = academics['termId'];
                            // console.log( academics['termDescription']);
                            termDescription = academics['termDescription'];
                            $('#search input[name="termId"]').val(termId);
                            $('#search input[name="termDescription"]').val(academics['termDescription']);
                            $('#search input[type=text]').attr('placeholder','Search ' + academics['termDescription'] + ' Classes' );
						}
						$('#careerTemplate').template('careerTemplate');
						refreshTemplate('${pageContext.request.contextPath}/services/${toolContext}/getCareers?termId='+termId+'&_type=json',
						'#careerList',
						'careerTemplate',
						'<li>No active Schools were found</li>',
						function() {
							try {
								$('#careerList').listview('refresh');
							} catch( err ) {
							}
							$("#careerList li").each(function (index,item) {
								$(item).bind("vclick",function(e) {
									if( sessionStorage ) {
										var academics;
										academics = JSON.parse( sessionStorage.getItem("academics") );
										if( academics == null || academics == "" ) {
											academics = {};
										}
										academics['careerId'] = e.target.id;
										sessionStorage.setItem( "academics", JSON.stringify(academics) );
									}
									//$.fn.loadSubjects();
								});
							});
						});
					};
					$('#academics-ui').live(
					'pageinit', $.fn.loadCareers() );
				</script>
			</kme:page>
		</c:when>
		<c:when test="${stage == 'subject'}">
		<kme:page title="${subjects}" id="academics-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics" backButtonURL="${pageContext.request.contextPath}/${toolContext}/${backButtonURL}">
			<kme:content>
              		<div align='center'>
	           		<form id="search" data-ajax="false" action="/michigan/${toolContext}/classSearch" method="post">
	            	<fieldset>
	             	<input id="searchCriteria" name="searchCriteria"  type="text" class="text ui-widget-content ui-corner-all" placeholder=""/>
	             	<input id="termId" name="termId" type="hidden"  value="${termId}"/>
	             	<input id="termDescription" name="termDescription" type="hidden"  value="${termDescription}"/>
	            	</fieldset>
               		</form>
              		</div>
			  		<kme:listView id="subjectList" dataTheme="c" dataDividerTheme="b" filter="false">
			  		<script id="subjectTemplate" type="text/x=jquery-templ">
						{{each subject}}
					 		<kme:listItem><a href="${pageContext.request.contextPath}/${toolContext}/browseClasses?stage=catalogNumber&termId=${termId}&subjectId=\${id}&subject=\${description}" id="\${id}:\${description}" data-transition=\"slide\" data-direction=\"forward\">\${description}</a></kme:listItem>
					  	{{/each}}
					</script>
			  </kme:listView>
		    </kme:content>
			<script type="text/javascript">
			 $.fn.loadSubjects = function() {
					var academics;
                	var termId = 0;
                	var termDescription="";
					if( sessionStorage ) {
							academics = JSON.parse( sessionStorage.getItem("academics") );
							if( academics == null || academics == "" ) {
								academics = {};
							}
                    		termId = academics['termId'];
                    		//console.log( 'subjects: ' + academics['termDescription']);
                    		termDescription = academics['termDescription'];
                    		$('#search input[name="termId"]').val(termId);
                    		$('#search input[name="termDescription"]').val(academics['termDescription']);
                    		$('#search input[type=text]').attr('placeholder','Search ' + academics['termDescription'] + ' Classes' );
							}
							$('#subjectTemplate').template('subjectTemplate');
							refreshTemplate('${pageContext.request.contextPath}/services/${toolContext}/getSubjects?termId='+academics['termId']+'&careerId='+academics['careerId']+'&_type=json',
							'#subjectList',
							'subjectTemplate',
							'<li>No active Subjects were found</li>',
							function() {
								try {
									$('#subjectList').listview('refresh');
								} catch(err) {

								}
								$("#subjectList li").each(function (index,item) {
									$(item).bind("vclick",function(e) {
										if( sessionStorage ) {
											var academics;
											academics = JSON.parse( sessionStorage.getItem("academics") );
											if( academics == null || academics == "" ) {
												academics = {};
											}
											var targetId = e.target.id;
											var mark = targetId.indexOf(":");
											academics['subjectId'] = targetId.substring(0,mark);
											academics['subjectDesc'] = targetId.substring(mark+1).trim();
											sessionStorage.setItem( "academics", JSON.stringify(academics) );
										}
										//$.fn.loadCatalogNumbers();
									});
								});
							});
						};
						$('#academics-ui').live(
						'pageinit', $.fn.loadSubjects() );
					</script>
				</kme:page>
			</c:when>
			<c:otherwise>
				<kme:page title="${terms}" id="academics-ui" backButton="true" homeButton="true" cssFilename="academics" jsFilename="academics" backButtonURL="${pageContext.request.contextPath}/${toolContext}">
					<kme:content>
						<kme:listView id="termList" dataTheme="c" dataDividerTheme="b" filter="false">
							<script id="termTemplate" type="text/x=jquery-templ">
								{{each term}}
								<kme:listItem><a href="${pageContext.request.contextPath}/${toolContext}/browseClasses?stage=career&termId=\${id}" id="\${id}" data-transition=\"slide\" data-direction=\"forward\">\${description}</a></kme:listItem>
								{{/each}}
								</script>
							</kme:listView>
						</kme:content>
						<script type="text/javascript">
							$.fn.loadTerms = function() {
								$('#termTemplate').template('termTemplate');
								refreshTemplate('${pageContext.request.contextPath}/services/${toolContext}/getTerms?_type=json',
								'#termList',
								'termTemplate',
								'<li>No active Terms were found</li>',
								function() {
									try {
										$('#termList').listview('refresh');
									} catch( err ) {
									}
									$("#termList li").each(function (index,item) {
										$(item).bind("vclick",function(e) {
											if( sessionStorage ) {
												var academics;
												academics = JSON.parse( sessionStorage.getItem("academics") );
												if( academics == null || academics == "" ) {
													academics = {};
												}
												//console.log($(this).text());
												academics['termId'] = e.target.id;
                                        		academics['termDescription']=$(this).text().trim();
                                        		//console.log( academics['termDescription']);
                                        		$('#search input[name="termDescription"]').val($(this).text());
                                        		$('#search input[type=text]').attr('placeholder','Search ' + $(this).text() + ' Classes' );
				        						sessionStorage.setItem( "academics", JSON.stringify(academics) );
											}
											//$.fn.loadCareers();
										});
									});
								});
							};
							$('#academics-ui').live(
							'pageinit', $.fn.loadTerms() );
						</script>
					</kme:page>
				</c:otherwise>
			</c:choose>
