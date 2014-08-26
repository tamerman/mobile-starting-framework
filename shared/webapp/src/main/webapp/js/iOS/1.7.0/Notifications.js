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


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *	Added the onIncoming as a event listener for deviceready. This is needed to deal with a
 *	LocalNotification that occurs when the app is not running at. all.
 *
 *
 */
$(function () {
    document.addEventListener("deviceready", Notifications.onIncoming, false);
});


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *	Notification Handler. Once this file/object is included, it can be used directly via the following call.
 *
 *
 *	var id = '1234';
 *	Notifications.add({...});
 *	Notifications.cancel(id);
 *	etc...
 */
var Notifications = {
    onIncoming: function () {
        window.plugins.applicationPreferences.get('LocalNotification', function (LocalNotification) {
                if (typeof(LocalNotification) != undefined && LocalNotification != '') {
                    var noteOBJ = jQuery.parseJSON(LocalNotification);
                    var msg = noteOBJ.alertBody;
                    var url = noteOBJ.url;
                    var id = noteOBJ.notificationId;

                    Notifications.handle(msg, url, id);

                    window.plugins.applicationPreferences.set('LocalNotification', '', function () {
                            console.log("LocalNotification: nullified");
                        }, function (error) {
                            console.log("Failed to nullify LocalNotification: " + error);
                        }
                    );
                }
            }, function (error) {
                console.log("Failed to get setting: " + error);
            }
        );
    },
    handle: function (msg, url, id) {
        if (url == "") {
            navigator.notification.alert(msg,
                function () {
                    window.plugins.localNotification.cancel(id);
                },
                mdotName,
                'Ok');
        } else {
            navigator.notification.confirm(msg,
                function (button) {
                    window.plugins.localNotification.cancel(id);
                    if (button == 1) {
                        window.location = url;
                    }
                },
                mdotName,
                'Open Link, Close');

        }
    },
    cancel: function (id) {
        window.plugins.localNotification.cancel(id);
        console.info("Cancelled Notification ID: " + id);
    },
    cancelAll: function () {
        window.plugins.localNotification.cancelAll();
        console.info("Cancelled All Notification");
    },
    add: function (notObj) {
        window.plugins.localNotification.add(notObj);
        console.log("Added new Note: " + d);
    }
};

