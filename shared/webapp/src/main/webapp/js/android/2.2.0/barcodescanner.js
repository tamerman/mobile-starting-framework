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


var BarcodeScanner = function () {
};

//-------------------------------------------------------------------
BarcodeScanner.Encode = {
    TEXT_TYPE: "TEXT_TYPE",
    EMAIL_TYPE: "EMAIL_TYPE",
    PHONE_TYPE: "PHONE_TYPE",
    SMS_TYPE: "SMS_TYPE",
    //  CONTACT_TYPE: "CONTACT_TYPE",  // TODO:  not implemented, requires passing a Bundle class from Javascriopt to Java
    //  LOCATION_TYPE: "LOCATION_TYPE" // TODO:  not implemented, requires passing a Bundle class from Javascriopt to Java
};

//-------------------------------------------------------------------
BarcodeScanner.prototype.scan = function (successCallback, errorCallback) {
    if (errorCallback == null) {
        errorCallback = function () {
        }
    }

    if (typeof errorCallback != "function") {
        console.log("BarcodeScanner.scan failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("BarcodeScanner.scan failure: success callback parameter must be a function");
        return
    }

    cordova.exec(successCallback, errorCallback, 'BarcodeScanner', 'scan', []);
};

//-------------------------------------------------------------------
BarcodeScanner.prototype.encode = function (type, data, successCallback, errorCallback, options) {
    if (errorCallback == null) {
        errorCallback = function () {
        }
    }

    if (typeof errorCallback != "function") {
        console.log("BarcodeScanner.scan failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("BarcodeScanner.scan failure: success callback parameter must be a function");
        return
    }

    cordova.exec(successCallback, errorCallback, 'BarcodeScanner', 'encode', [
        {"type": type, "data": data, "options": options}
    ]);
};

//-------------------------------------------------------------------

if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.barcodeScanner) {
    window.plugins.barcodeScanner = new BarcodeScanner();
}


console.log("Loaded BarcodeScanner.js");
