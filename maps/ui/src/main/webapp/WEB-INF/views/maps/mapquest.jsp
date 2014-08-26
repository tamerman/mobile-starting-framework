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