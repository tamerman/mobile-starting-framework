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


$(document).ready(function () {
    var $calendar = $('#calendar');
    var id = 10;

    $calendar.weekCalendar({
        timeslotsPerHour: 4,
        allowCalEventOverlap: true,
        overlapEventsSeparate: true,
        firstDayOfWeek: 7,
        useShortDayNames: true,
        businessHours: {
            start: 8,
            end: 23,
            limitDisplay: true
        },
        daysToShow: 7,
        height: function ($calendar) {
            return $(window).height() - $("h1").outerHeight() - 1;
        },
        eventRender: function (calEvent, $event) {
            if (calEvent.end.getTime() < new Date().getTime()) {
                $event.css("backgroundColor", "#aaa");
                $event.find(".wc-time").css({
                    "backgroundColor": "#999",
                    "border": "1px solid #888"
                });
            }
            if (calEvent.source == "google" && calEvent.end.getTime() > new Date().getTime()) {
                $event.css("backgroundColor", "#9933FF");
                $event.find(".wc-time").css({
                    "backgroundColor": "#9933FF",
                    "border": "1px solid ##FFB4FF"
                });
            }


        },
        draggable: function (calEvent, $event) {
            return calEvent.readOnly != true;
        },
        resizable: function (calEvent, $event) {
            return calEvent.readOnly != true;
        },
        eventNew: function (calEvent, $event) {
            var $dialogContent = $("#event_edit_container");
            resetForm($dialogContent);
            var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
            var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
            var titleField = $dialogContent.find("input[name='title']");
            var bodyField = $dialogContent.find("textarea[name='body']");


            $dialogContent.dialog({
                modal: true,
                title: "New Calendar Event",
                close: function () {
                    $dialogContent.dialog("destroy");
                    $dialogContent.hide();
                    $('#calendar').weekCalendar("removeUnsavedEvents");
                },
                buttons: {
                    save: function () {
                        calEvent.id = id;
                        id++;
                        calEvent.start = new Date(startField.val());
                        calEvent.end = new Date(endField.val());
                        calEvent.title = titleField.val();
                        calEvent.body = bodyField.val();
                        $dialogContent.dialog("close");
                        var startTime = startField.val();
                        var endTime = endField.val();
                        var eventTitle = titleField.val();
                        var eventBody = bodyField.val();
                        var eventDate = $dialogContent.find(".date_holder").text();

                        $calendar.weekCalendar("removeUnsavedEvents");
                        $calendar.weekCalendar("updateEvent", calEvent);
                        $.ajax({
                            type: "POST",
                            url: "http://localhost:9999/mdot/calendar/addEvent",
                            data: {
                                startTime: startTime,
                                endTime: endTime,
                                title: eventTitle,
                                eventBody: eventBody,
                                eventDate: eventDate
                            }
                        });
                    },
                    cancel: function () {
                        $dialogContent.dialog("close");
                    }
                }
            }).show();

            var eDate = $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
            setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));

        },
        eventDrop: function (calEvent, $event) {

        },
        eventResize: function (calEvent, $event) {
        },
        eventClick: function (calEvent, $event) {

            if (calEvent.readOnly) {
                return;
            }

            var $dialogContent = $("#event_edit_container");
            resetForm($dialogContent);
            var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
            var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
            var titleField = $dialogContent.find("input[name='title']").val(calEvent.title);
            var bodyField = $dialogContent.find("textarea[name='body']");
            var idField = $dialogContent.find("input[name='id']").val(calEvent.id);
            bodyField.val(calEvent.body);

            $dialogContent.dialog({
                modal: true,
                title: "Edit - " + calEvent.title,
                close: function () {
                    $dialogContent.dialog("destroy");
                    $dialogContent.hide();
                    $('#calendar').weekCalendar("removeUnsavedEvents");
                },
                buttons: {
                    save: function () {


                        calEvent.start = new Date(startField.val());
                        calEvent.end = new Date(endField.val());
                        calEvent.title = titleField.val();
                        calEvent.body = bodyField.val();

                        $calendar.weekCalendar("updateEvent", calEvent);

                        $dialogContent.dialog("close");
                        var startTime = startField.val();
                        var endTime = endField.val();
                        var eventTitle = titleField.val();
                        var eventBody = bodyField.val();
                        var eventDate = $dialogContent.find(".date_holder").text();
                        var eventid = idField.val();

                        $.ajax({
                            url: 'http://localhost:9999/mdot/calendar/updateEvent',
                            type: 'POST',
                            data: {
                                eventId: eventid,
                                startTime: startTime,
                                endTime: endTime,
                                title: eventTitle,
                                eventBody: eventBody,
                                eventDate: eventDate
                            }
                        });

                    },
                    "delete": function () {
                        $calendar.weekCalendar("removeEvent", calEvent.id);
                        $dialogContent.dialog("close");
                        var eventid = idField.val();
                        $.ajax({
                            url: 'http://localhost:9999/mdot/calendar/deleteEvent',
                            type: 'POST',
                            data: {
                                eventId: eventid
                            }
                        });

                    },
                    cancel: function () {
                        $dialogContent.dialog("close");
                    }
                }
            }).show();

            var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
            var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
            $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
            setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));
            $(window).resize().resize(); //fixes a bug in modal overlay size ??

        },
        eventMouseover: function (calEvent, $event) {
        },
        eventMouseout: function (calEvent, $event) {
        },
        noEvents: function () {

        },
        data: function (start, end, callback) {
            callback(getEventData(start, end));
        }
    });

    function resetForm($dialogContent) {
        $dialogContent.find("input").val("");
        $dialogContent.find("textarea").val("");
    }


    function convert(str) {
        var date = new Date(str),
            mnth = ("0" + (date.getMonth() + 1)).slice(-2),
            day = ("0" + date.getDate()).slice(-2);
        return [ date.getFullYear(), mnth, day ].join("-");
    }


    function getEventData(start, end) {
        var startDate = convert(start);
        var endDate = convert(end - 1);
        var year = new Date().getFullYear();
        var month = new Date().getMonth();
        var day = new Date().getDate();
        var eventData;
        var eventData1;
        var returnEventData = "{events:[";
        var myObj = new Object();
        var entireEvents = {
            events: []
        };

        $.ajax({
            url: 'http://localhost:9999/mdot/calendar/getEvents',
            type: 'POST',
            async: false,
            data: {
                "startdate": startDate,
                "enddate": endDate
            },
            success: function (data) {
                eventData = data;
                eventData1 = eval(eventData);
                for (var i = 0; i < eventData1.length; i++) {
                    var id = eventData1[i]["id"];
                    var start = eventData1[i]["start"];
                    var end = eventData1[i]["end"];
                    var title = eventData1[i]["title"];
                    var eventBody = eventData1[i]["eventBody"];
                    var eventSource = eventData1[i]["source"];
                    var startfullDate = new Date(start);
                    var endfullDate = new Date(end);
                    var startYear = startfullDate.getFullYear();
                    var startMonth = startfullDate.getMonth();
                    var startDay = startfullDate.getDate();
                    var endYear = endfullDate.getFullYear();
                    var endMonth = endfullDate.getMonth();
                    var endDay = endfullDate.getDate();
                    var startTimeHour = startfullDate.getHours();
                    var endTimeHour = endfullDate.getHours();
                    var startTimeMins = startfullDate.getMinutes();
                    var endTimeMins = endfullDate.getMinutes();
                    var item = {
                        "id": id,
                        "start": new Date(startYear, startMonth, startDay, startTimeHour, startTimeMins),
                        "end": new Date(endYear, endMonth, endDay, endTimeHour, endTimeMins),
                        "title": title,
                        "body": eventBody,
                        "source": eventSource
                    };
                    entireEvents.events.push(item);
                }
            }
        });
        $("#caltitle").val(startDate + "  to  " + endDate);
        return entireEvents;

    }


    /*
     * Sets up the start and end time fields in the calendar event
     * form for editing based on the calendar event being edited
     */
    function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {

        for (var i = 0; i < timeslotTimes.length; i++) {
            var startTime = timeslotTimes[i].start;
            var endTime = timeslotTimes[i].end;
            var startSelected = "";
            if (startTime.getTime() === calEvent.start.getTime()) {
                startSelected = "selected=\"selected\"";
            }
            var endSelected = "";
            if (endTime.getTime() === calEvent.end.getTime()) {
                endSelected = "selected=\"selected\"";
            }
            $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
            $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

        }
        $endTimeOptions = $endTimeField.find("option");
        $startTimeField.trigger("change");
    }

    var $endTimeField = $("select[name='end']");
    var $endTimeOptions = $endTimeField.find("option");

    //reduces the end time options to be only after the start time options.
    $("select[name='start']").change(function () {
        var startTime = $(this).find(":selected").val();
        var currentEndTime = $endTimeField.find("option:selected").val();
        $endTimeField.html(
            $endTimeOptions.filter(function () {
                return startTime < $(this).val();
            })
        );

        var endTimeSelected = false;
        $endTimeField.find("option").each(function () {
            if ($(this).val() === currentEndTime) {
                $(this).attr("selected", "selected");
                endTimeSelected = true;
                return false;
            }
        });

        if (!endTimeSelected) {
            //automatically select an end date 2 slots away.
            $endTimeField.find("option:eq(1)").attr("selected", "selected");
        }

    });


    var $about = $("#about");

    $("#about_button").click(function () {
        $about.dialog({
            title: "About this calendar demo",
            width: 600,
            close: function () {
                $about.dialog("destroy");
                $about.hide();
            },
            buttons: {
                close: function () {
                    $about.dialog("close");
                }
            }
        }).show();
    });


});
