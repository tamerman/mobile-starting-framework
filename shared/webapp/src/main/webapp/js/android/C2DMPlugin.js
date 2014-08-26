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


/**
 *
 * @return Instance of C2DM
 */
var C2DM = function () {

}

/**
 *
 *
 *
 */
C2DM.prototype.register = function (senderEmail, eventCallback, successCallback, failureCallback) {

    if (typeof eventCallback != "string")		// The eventCallback has to be a STRING name not the actual routine like success/fail routines
    {
        var e = new Array();
        e.msg = 'eventCallback must be a STRING name of the routine';
        e.rc = -1;
        failureCallback(e);
        return;
    }

    return PhoneGap.exec(successCallback,    	//Callback which will be called when directory listing is successful
        failureCallback,     	//Callback which will be called when directory listing encounters an error
        'C2DMPlugin',  			//Telling PhoneGap that we want to run "DirectoryListing" Plugin
        'register',             //Telling the plugin, which action we want to perform
        [
            { email: senderEmail, ecb: eventCallback }
        ]);        	//Passing a list of arguments to the plugin,
    // The ecb variable is the STRING name of your javascript routine to be used for callbacks
    // You can add more to validate that eventCallback is a string and not an object
};


C2DM.prototype.unregister = function (successCallback, failureCallback) {


    return PhoneGap.exec(successCallback,    	//Callback which will be called when directory listing is successful
        failureCallback,     	//Callback which will be called when directory listing encounters an error
        'C2DMPlugin',  			//Telling PhoneGap that we want to run "DirectoryListing" Plugin
        'unregister',             //Telling the plugin, which action we want to perform
        [
            { }
        ]);        	//Passing a list of arguments to the plugin,
};


/**
 */


PhoneGap.addConstructor(function () {
    //Register the javascript plugin with PhoneGap
    PhoneGap.addPlugin('C2DM', new C2DM());

    //Register the native class of plugin with PhoneGap
    PluginManager.addService("C2DMPlugin", "com.plugin.C2DM.C2DMPlugin");

    //alert( "added Service C2DMPlugin");
});
