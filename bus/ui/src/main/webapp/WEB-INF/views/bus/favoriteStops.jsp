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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="bus.title.favorites" var="favoritesTitle"/>
<spring:message code="bus.label.map" var="mapLabel"/>
<spring:message code="bus.label.routes" var="routesLabel"/>
<spring:message code="bus.label.favorites" var="favoritesLabel"/>
<spring:message code="bus.label.nearbystops" var="nearbystopsLabel"/>

<kme:page title="${favoritesTitle}" id="bus-webapp" backButton="true" homeButton="true" cssFilename="bus">
    <kme:content>
        <kme:listView id="busStops" dataTheme="c" dataDividerTheme="b" filter="false">
        </kme:listView>
    </kme:content>
	<c:set var="fileName" value="favorites"/>
	<%@ include file="footer.jsp" %>
    <script type="text/javascript">
        $(document).bind("pageinit", function() {
            if( localStorage ) {
                //LOG("Local storage is available in the browser.");
                var favs = JSON.parse( localStorage.getItem( "bus_favorites" ) );
                if( $.isArray( favs ) ) {
                } else {
                    favs = new Array();
                    favs[0] = localStorage.getItem( "bus_favorites" );
                }
                favs = favs.sort();
                favs = favs.reverse();
                //LOG("favs list is:"+favs);
                if ((favs.length == 1 && (favs[0] == null || favs[0]=="")) || favs.length == 0 ) {
                    $('#busStops').append(
                                "<li><div>No favorites found.</div></li>");
                                $('#busStops').listview("refresh");
                } else {
                for( n = 0; n < favs.length; n++ ) {
                    if( favs[n] == null || favs[n]=="" ) {
                        continue;
                    } else {
                        $.ajax( {
                            type:'Get',
                            url:'${pageContext.request.contextPath}/services/bus/stop/lookupbyname/${campus}?name='+favs[n]+"&_type=json",
                            dataType: "json",
                            success:function(data) {
                                //LOG( "Response is: "+data.stop.name );
                                var url = "${pageContext.request.contextPath}/bus/viewStop?campus=ALL";
                                url = url + "&stopId=" + data.stop.id;
                                $('#busStops').append(
                                "<li data-theme=\"c\" data-icon=\"listview\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-icon ui-btn-up-c\"><div class=\"ui-btn-inner ui-li\"><div class=\"ui-btn-text\"><a href=\""+url+"\" class=\"ui-link-inherit\"><h3 class=\"ui-li-heading\">"+data.stop.name+"</h3></a></div><span class=\"ui-icon ui-icon-arrow-r ui-icon-shadow\"></span></div></li>");
                            }
                        });
                    }
                    $('#busStops').listview("refresh");

                }}
            } else {
                //LOG("Local storage is unavailable in the browser.");
                 $('#busStops').append(
                                "<li><div>Local storage is not available in the browser.</div></li>");
                                $('#busStops').listview("refresh");
            }
        });
        function LOG(msg) {
            setTimeout(function() {
                throw new Error(msg);
            }, 0);
        }
    </script>
</kme:page>
