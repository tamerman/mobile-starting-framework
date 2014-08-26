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


//alert( localStorage.site );
gApp = new Array();

gApp.deviceready = false;
gApp.c2dmregid = '';

window.onbeforeunload = function (e) {

    if (gApp.c2dmregid.length > 0) {
        // The same routines are called for success/fail on the unregister. You can make them unique if you like
        window.plugins.C2DM.unregister(C2DM_Success, C2DM_Fail);			// close the C2DM

    }
};


//$(document).bind("mobileinit", function() {
//alert( 'Starting...');

$("#app-status-ul").append('<li>Mobileinit event received');

document.addEventListener('deviceready', function () {
    // This is the PhoneGap deviceready event. Once this is called PhoneGap is available to be used
    $("#app-status-ul").append('<li>deviceready event received');

    $("#app-status-ul").append('<li>calling C2DM.register, register our email with Google');


    gApp.DeviceReady = true;

    // Some Unique stuff here,
    // The first Parm is your Google email address that you were authorized to use C2DM with
    // the Event Processing rountine (2nd parm) we pass in the String name
    // not a pointer to the routine, under the covers a JavaScript call is made so the name is used
    // to generate the function name to call. I didn't know how to call a JavaScript routine from Java
    // The last two parms are used by PhoneGap, they are the callback routines if the call is successful or fails
    //
    // CHANGE: your_c2dm_account@gmail.com
    // TO: what ever your C2DM authorized email account name is
    //
    window.plugins.C2DM.register("mtwagner@gmail.com", "C2DM_Event", C2DM_Success, C2DM_Fail);

}, false);


//});

function post_to_url(path, params, method) {
    method = method || "post"; // Set method to post by default, if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.    
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for (var key in params) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", params[key]);

        form.appendChild(hiddenField);
    }

    document.body.appendChild(form);
    form.submit();
}

function C2DM_Event(e) {
    console.log("EVENT Occurred: " + e.event);

    switch (e.event) {
        case 'registered':
            // the definition of the e variable is json return defined in C2DMReceiver.java
            // In my case on registered I have EVENT and REGID defined
            gApp.c2dmregid = e.regid;
            if (gApp.c2dmregid.length > 0) {
                console.log('REGISTERED -> REGID:' + e.regid);
                console.log('DEVICE -> UUID:' + device.uuid);

                postquery = "https://www.indiana.edu/~iumobile/push/registerAndroid.php?regID=" + e.regid + "&deviceName=" + device.name + "&deviceUUID=" + device.uuid;

                var registerHost = 'http://mtwagner.dyndns.org:9999/mdot/push/register?data={name:"' + device.name + '",regId:"' + e.regid + '",deviceId:"' + device.uuid + '",type:"Android",username:"mtwagner"}';
                console.log(registerHost);

                $.get(postquery, function (data) {
                    console.log("REGID -> Get to AppServer: " + data);
                });
            }
            break;
        case 'message':
            handle(e.msg, e.url, e.title);
            break;
        case 'error':
            console.log(e.msg);
            break;
        default:
            console.log("Unknown Event");
            break;
    }
}

function C2DM_Success(e) {
    console.log("C2DM_Success -> We have successfully registered and called the C2DM plugin, waiting for C2DM_Event:reistered -> REGID back from Google");
}

function C2DM_Fail(e) {
    console.log("C2DM_Fail -> C2DM plugin failed to register");
    console.log("C2DM_Fail -> " + e.msg);
}

