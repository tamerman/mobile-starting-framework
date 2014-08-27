/*
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


// Remove the back button from all pages
//$.mobile.page.prototype.options.addBackBtn=false;

//  turns default transitions off


// $(document).bind("mobileinit", function(){
//         $.mobile.defaultTransition = 'none';
// });

/* Test */

$(document).bind("mobileinit", function(){
  $.mobile.ajaxEnabled = false;
  /*
    The below lines of code retrieve the campus information from the local storage automatically when user executes any tool.
    First time user has to select one campus. Next time onwards(even after closing the browser also), it will not prompt the user
    to select campus because it will get the campus from local storage. This request will be redirected to CampusController's
    selectCampus() method and the user object will be associated with the campus value. So user object will always have some value.
    User can still change the campus by requesting like: http://localhost:9999/mdot/campus?toolName=computerlabs(any tool).
  */
  var myCampus = localStorage.getItem("myCampus");
  var url = $(location).attr('href');
  var temp = url.split("?");
  var isRedirect=true;
  if(temp[0] != window.kme.serverDetails.getServerPath()+"/campus")
  {
       var toolName=null;
       if(temp.length>=2)
       {
         var queryString = temp[1].split("&");
         var tool=queryString[0].split("=");
         if(tool[0]=="toolName")
         {
             toolName=tool[1];
         }
       }
       var fromLocalStroage=url.split("&");
       var isRedirect=true;
       if(fromLocalStroage[fromLocalStroage.length-1]=="local=storage")
             isRedirect=false;
       if(myCampus != null && isRedirect && toolName != null) {
             /*
                 "&local=storage" is appended to the redirectURL so that it will not redirect to the same page again and again.
                 If the "&local=storage"(can be anything) is not appended to the url then it will be redirected to the same url infinite number of times.
                 So it is just to avoid this.
             */
    	   var redirectURL = window.kme.serverDetails.getServerPath()+"/campus/select?toolName="+toolName+"&campus="+myCampus+"&local=storage";
           
             $(location).attr('href',redirectURL);
       }
  }
});

function getParameterByName( name )
{
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return decodeURIComponent(results[1].replace(/\+/g, " "));
}



/* Computer Labs */

$('[data-role=page][id=computerlabshome]').live('pagebeforeshow',function(event, ui){
	//alert('test44');
	$('#clListTemplate').template('clListTemplate');
	//refreshComputerLabs();
});

function refreshComputerLabs() {
	$.mobile.pageLoading();
	$('#cllist').text('');
	var dynamicDataResp = $.ajax({
		url: "computerlabs?campus=BL",
		dataType: 'json',
		async: false,
		cache: false           
	});
	if(dynamicDataResp.status == 200){
		var dynamicDataObj = jQuery.parseJSON(dynamicDataResp.responseText);
		$.tmpl('clListTemplate', dynamicDataObj).appendTo('#cllist');
		$('#cllist').listview('refresh');
	}
}

//$('[data-role=page][id=kb]').live("pagebeforeshow", function(event) {
////	alert("test");
//	$('#searchText').keyup(function() {
//		lookup($('#searchText').val());
//	});	
//});

/* Begin Calendar js*/

var calendarSelectedDate = null;
var calendarSelectedMonthYear = null;

$("div#Calendar-Events").live('pagecreate',function(e, ui){ 
    if (calendarSelectedDate == null){
    	var d = new Date();
    	var curr_date = ""+d.getDate();
    	if(curr_date.length < 2){
    		curr_date = "0"+curr_date;
    	}
    	var curr_month = (d.getMonth()+1)+"";
    	if(curr_month.length < 2){
    		curr_month = "0"+curr_month;
    	}
    	var curr_year = d.getFullYear();
    	calendarSelectedDate = "" + curr_year + curr_month + curr_date;
    	calendarSelectedMonthYear = "" + curr_year + curr_month;
//    	alert("selected: "+calendarSelectedDate + " year: "+calendarSelectedMonthYear);
    }
	hideCalendarDay(calendarSelectedMonthYear);
	showCalendarDay(calendarSelectedMonthYear, calendarSelectedDate);
 });

function hideCalendarDay(selectedMonthYear){
	$("div.Calendar-Day-"+selectedMonthYear).hide();
	
	var events = $("div.event-true-selected"+selectedMonthYear);
	events.removeClass("event-true-selected event-true-selected"+selectedMonthYear);
	events.addClass("event-true event-true"+selectedMonthYear);

	var eventsFalse = $("div.event-false-selected"+selectedMonthYear);
	eventsFalse.removeClass("event-false-selected event-false-selected"+selectedMonthYear);
	eventsFalse.addClass("event-false event-false"+selectedMonthYear);
}

function showCalendarDay(selectedMonthYear, selectedDate){
	$("div.Calendar-Day-"+selectedMonthYear+"-"+selectedDate).show()

    var currentEvent = $("div.event-true"+selectedMonthYear+selectedDate);
    currentEvent.removeClass("event-true event-true-"+selectedMonthYear);
    currentEvent.addClass("event-true-selected event-true-selected"+selectedMonthYear);

    var currentEventFalse = $("div.event-false"+selectedMonthYear+selectedDate);
    currentEventFalse.removeClass("event-false event-false-"+selectedMonthYear);
    currentEventFalse.addClass("event-false-selected event-false-selected"+selectedMonthYear);
}

/* End Calendar js*/

/* 	function fill(thisValue) {
	$('#inputString').val(thisValue);
	setTimeout("$('#suggestions').hide();", 200);
} */


/* Begin Horizontal Tab Navigation*/

 $(window).load(function() {
// alert("two");
     $('.tabs-panel2').hide();
     $('.tabs-panel3').hide();
     $('.tabs-panel4').hide();
     $('.tabs-panel1').hide();

     $('.tabs-tab2').click(function() {
         $('.tabs-tab2').addClass('selected');
         $('.tabs-tab3').removeClass('selected');
         $('.tabs-tab4').removeClass('selected');
         $('.tabs-tab1').removeClass('selected');
         $('.tabs-panel2').show();
         $('.tabs-panel3').hide();
         $('.tabs-panel4').hide();
         $('.tabs-panel1').hide();
     });

     $('.tabs-tab3').click(function() {
         $('.tabs-tab3').addClass('selected');
         $('.tabs-tab2').removeClass('selected');
         $('.tabs-tab4').removeClass('selected');
         $('.tabs-tab1').removeClass('selected');
         $('.tabs-panel2').hide();
         $('.tabs-panel3').show();
         $('.tabs-panel4').hide();
         $('.tabs-panel1').hide();
     });

     $('.tabs-tab4').click(function() {
         $('.tabs-tab3').removeClass('selected');
         $('.tabs-tab2').removeClass('selected');
         $('.tabs-tab4').addClass('selected');
         $('.tabs-tab1').removeClass('selected');
         $('.tabs-panel2').hide();
         $('.tabs-panel3').hide();
         $('.tabs-panel4').show();
         $('.tabs-panel1').hide();
     });

     $('.tabs-tab1').click(function() {
         $('.tabs-tab3').removeClass('selected');
         $('.tabs-tab2').removeClass('selected');
         $('.tabs-tab4').removeClass('selected');
         $('.tabs-tab1').addClass('selected');
         $('.tabs-panel2').hide();
         $('.tabs-panel3').hide();
         $('.tabs-panel4').hide();
         $('.tabs-panel1').show();
     });

 });


/* End Horizontal Tab Navigation*/

/**
 * Code for localisation box
 */
$(document).ready(function(){
	$("div[data-role=l10nBox] a[data-l10n-lang]").click(function(){
		var field = $(this).parent("div[data-role=l10nBox]").attr("data-l10n-field");
		var language = $(this).attr("data-l10n-lang");

		$("div[data-role=l10nBox][data-l10n-field="+field + "] div[data-l10n-lang]").hide();
		$("div[data-role=l10nBox][data-l10n-field="+field + "] a[data-l10n-lang]").removeClass("l10n-active");
		$("div[data-role=l10nBox][data-l10n-field="+field + "] a[data-l10n-lang="+language+"]").addClass("l10n-active");
		$("div[data-role=l10nBox][data-l10n-field="+field + "] div[data-l10n-lang="+language+"]").show();
	});
});