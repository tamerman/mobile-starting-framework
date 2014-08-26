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
	calendarSelectedDate = ${selectedDate};
	calendarSelectedMonthYear = ${monthYear};
</script>

<script type="text/javascript">
 $(window).load(function() {
     $('.tabs-tab1').addClass('selected');
     $('.tabs-panel1').show();
 });
</script>
  
  
  
  <div class="tabs-tabcontainer">
      <a style="width:24.5%;" class="tabs-tab1 selected" name="tabs-tab1" href="${pageContext.request.contextPath}/calendar/month?date=${monthYear}">Month</a>
      <a style="width:24.5%;" class="tabs-tab2" name="tabs-tab2" href="${pageContext.request.contextPath}/calendar/list?date=${selectedDate}">List</a>
     <a style="width:24.5%; "class="tabs-tab3" name="tabs-tab3" href="${pageContext.request.contextPath}/calendar/options">Options</a>
     <a style="width:24.5%; " class="tabs-tab4" name="tabs-tab4" href="${pageContext.request.contextPath}/calendar/createEvent" data-ajax="false">Add Event</a>
    </div>
    <div class="tabs-panel1" name="tabs-panel1" style="margin-top:25px">
    
      <div class="container_16 monthgrid" style="background-color:; margin-top:px">
        <div class="grid_3">
          <div class="cal-arrow-left">  <a href="${pageContext.request.contextPath}/calendar/month?date=${previousMonth}" onclick="javascript:setCalendarSelectedDate(${previousMonth}01, ${previousMonth}); return true;" data-direction="reverse">
                    <img src="${pageContext.request.contextPath}/images/arrow-left.png" width="16" height="16" alt="back">
                </a>  </div>
        </div>
        <div class="grid_10">
          <div class="month-year"><c:out value="${viewData.title}"/></div>
        </div>
        <div class="grid_3">
          <div class="cal-arrow-right">  <a href="${pageContext.request.contextPath}/calendar/month?date=${nextMonth}" onclick="javascript:setCalendarSelectedDate(${nextMonth}01, ${nextMonth});return true;">
                    <img src="${pageContext.request.contextPath}/images/arrow-right.png" width="16" height="16" alt="forward">
                </a>  </div>
        </div>
        
  <div class="month-grid">  
        <div class=" daysofweek grid_cal"> Sun</div>
        <div class=" daysofweek grid_cal"> Mon</div>
        <div class=" daysofweek grid_cal"> Tue</div>
        <div class=" daysofweek grid_cal"> Wed</div>
        <div class=" daysofweek grid_cal"> Thu</div>
        <div class=" daysofweek grid_cal"> Fri</div>
        <div class=" daysofweek grid_cal"> Sat</div>
   

      <c:forEach var="day" items="${events}">
      
              <c:choose>
                <c:when test="${day.value.currentMonth}">
                	<c:choose>
               			<c:when test="${selectedDate eq day.key}">
               				<c:set var="current" value="-selected"/>
               			</c:when>
               			<c:otherwise>
               				<c:set var="current" value=""/>
               			</c:otherwise>
               		</c:choose>
               		<c:choose>
               			<c:when test="${today eq day.key}">
               				<c:set var="todaySelected" value="event-${day.value.hasEvents}-today"/>
               			</c:when>
               			<c:otherwise>
               				<c:set var="todaySelected" value=""/>
               			</c:otherwise>
               		</c:choose>
                    <div class="datebox-${day.value.currentMonth} grid_cal" onclick='javascript:hideCalendarDay(${monthYear});showCalendarDay(${monthYear}, ${day.key});return false;'>
                        <div class="event-${day.value.hasEvents}${current} event-${day.value.hasEvents}${monthYear}${day.key} event-${day.value.hasEvents}${current}${monthYear} ${todaySelected}">${day.value.day}</div>
                    </div>
                 </c:when>
                 <c:otherwise>
                    <c:choose>
                        <c:when test="${day.value.beforeCurrentMonth}">
                            <c:set var="dataDirection" value="reverse"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="dataDirection" value=""/>
                        </c:otherwise>
                     </c:choose>
                        <div class="datebox-${day.value.currentMonth} grid_cal">
                            <div class="event-${day.value.hasEvents} event-${day.value.hasEvents}-${day.key} event-${day.value.hasEvents}${monthYear}">${day.value.day}</div>
                        </div>
                 </c:otherwise>
              </c:choose>
          
      </c:forEach>
  
      </div>
      </div>
 
  <c:choose>
  <c:when test="${not empty filter}">
        <div class="container_12">
        <div class="grid_12">
          <p>Viewing: ${filter.filterName} <a data-ajax="false" href="${pageContext.request.contextPath}/calendar/removeFilter" style="color:#990000; font-weight:normal">(clear filter)</a></p>
        </div>
        
      </div>
  </c:when>
  <c:otherwise>
  
      <div class="container_12">
        <div class="grid_12">
          <p><a data-ajax="false" href="${pageContext.request.contextPath}/calendar/filters" style="color:#990000; font-weight:normal">Filter by category</a></p>
        </div>
      </div>
  
  </c:otherwise>
  </c:choose>
    <c:forEach var="day" items="${events}">
    <c:choose>
       <c:when test="${selectedDate eq day.key}">
           <c:set var="display" value="block"/>
       </c:when>
       <c:otherwise>
           <c:set var="display" value="none"/>
       </c:otherwise>
	</c:choose>
    
      <div class="Calendar-Day-${monthYear} Calendar-Day-${monthYear}-${day.key}" style="display: ${display};">
     <ul data-role="listview" data-theme="c" data-inset="true" style="margin: 0 0 0 0;">
        <c:forEach var="event" items="${day.value.events}">
           <li>
              <a href="${pageContext.request.contextPath}/calendar/event?eventId=${event.eventId}&date=${event.date}">
                <h3 style="white-space:normal"><c:out value="${event.title}"/></h3>
                <p style="white-space:normal"><c:out value="${event.location}"/></p>
                <p><c:out value="${event.time}"/></p>
             </a> 
          </li>
        </c:forEach>
        <c:if test="${empty day.value.events}">
           <li style="padding:15px !important;">
                <h3 style="margin: 0 0 0 0;" class="wrap">There are no events on this day.</h3>
          </li>     
        </c:if>
      </ul>
     </div>
    
   </c:forEach>
   
    
 </div>
    
    </kme:content></kme:page>
    
   