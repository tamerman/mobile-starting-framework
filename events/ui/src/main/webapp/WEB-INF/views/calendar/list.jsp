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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>


<kme:page title="Events" id="Calendar-Events" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/events">


	<kme:content>
	
	
	<script type="text/javascript">
 $(window).load(function() {
     $('.tabs-tab2').addClass('selected');
     $('.tabs-panel2').show();
 });
</script>
	

	
	<div class="tabs-tabcontainer">
    <a style="width:24.5%;"class="tabs-tab1" name="tabs-tab1" href="${pageContext.request.contextPath}/calendar/month?date=${monthSelectedDate}">Month</a>
    <a style="width:24.5%;" class="tabs-tab2" name="tabs-tab2" href="${pageContext.request.contextPath}/calendar/list?date=${selectedDate}">List</a>
    <a style="width:24.5%;" class="tabs-tab3" name="tabs-tab3" href="${pageContext.request.contextPath}/calendar/options">Options</a>
    <a style="width:24.5%;" class="tabs-tab4" name="tabs-tab4" href="${pageContext.request.contextPath}/calendar/createEvent" data-ajax="false">Add Event</a>
  </div>
	
	



  <div class="tabs-panel2" name="tabs-panel2">
  
  
  
  <div class="container_12" style="margin-bottom:22px">
         <div class="grid_6"><a href="${pageContext.request.contextPath}/calendar/listEvents?date=${selectedDate}&beginDate=${previousDate}&endDate=${currentEndDate}" >&lt; <c:out value="${days}"/> previous</a></div>
        <div class="grid_6" align="right"><c:out value="${viewData.title}"/></div>
      </div>
  
  
  
  
  
  
  <c:if test="${not empty filter}">Filtered by: ${filter.filterName}<br/><br/></c:if>
          <ul data-role="listview" data-theme="c"  data-dividertheme="d"  data-inset="false">
        
    <c:forEach var="listData" items="${events}">
      <li data-role="list-divider"><c:out value="${listData.weekday}"/> <c:out value="${listData.displayDate}"/></li>
      <c:forEach var="event" items="${listData.events}">      
          <li><a href="${pageContext.request.contextPath}/calendar/event?eventId=${event.eventId}&date=${event.date}">
            <h3><c:out value="${event.title}"/></h3>
                <p><c:out value="${event.location}"/></p>
                <p><c:out value="${event.time}"/></p>
            </a> </li>
      </c:forEach>
    </c:forEach>
   
    </ul>

      <div  style="padding-top:20px">
       
       <a href="${pageContext.request.contextPath}/calendar/listEvents?date=${selectedDate}&beginDate=${beginDate}&endDate=${endDate}" ><c:out value="${days}"/> More &gt;</a>
      </div>

            
           
    

  </div>
 </kme:content></kme:page>
    