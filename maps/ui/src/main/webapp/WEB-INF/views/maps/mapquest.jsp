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

<c:set var="localeCode" value="${pageContext.response.locale}" />

<kme:page title="${msgCat_ToolTitle}" id="map" backButton="true" homeButton="true" mapLocale="${localeCode}">
    <kme:content>

        <script src="http://www.mapquestapi.com/sdk/js/v7.0.s/mqa.toolkit.js?key=${mapquestApiKey}"></script>
         <style>
             .mqacopyrightdark {
                 display: none;
             }
         </style>


        <script type="text/javascript">

            $(document).ready(function() {

                var options={
                    elt: document.getElementById('map'),
                    zoom: 13,
                    latLng:{lat:40.735383, lng:-73.984655},
                    mtype: 'osm'
                };

                window.map = new MQA.TileMap(options);

                MQA.withModule('largezoom', 'viewoptions', 'traffictoggle', 'mousewheel', 'directions', function() {
                    map.addControl(new MQA.LargeZoom(), new MQA.MapCornerPlacement(MQA.MapCorner.TOP_LEFT, new MQA.Size(5,5)));
                    map.addControl(new MQA.TrafficToggle());
                    map.addControl(new MQA.ViewOptions());
                    map.enableMouseWheelZoom();
                });

                $('#submit').click(function(event) {
                    var location = document.getElementById('location').value;
                    MQA.withModule('nominatim', function() {
                        map.nominatimSearchAndAddLocation(location, null);
                    });
                });
            });

        </script>

        <input type="text" id="location" name="location" value="">
        <input type="submit" id="submit" value="Search">

        <br />

        <div id='map'></div>

        <br />

    </kme:content>
</kme:page>