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


function displayConfirm(message, callbackYes, callbackNo) {

    if (!callbackNo) {
        callbackNo = function () {
        };
    }

    if (!callbackYes) {
        callbackYes = function () {
        };
    }

    // List of buttons to display
    var buttonsList = {};

    // Configuration for selecting yes
    buttonsList[msgCat_Yes] = {
        'default': 'yes',
        'action': callbackYes
    };

    // Configuration for selecting no
    buttonsList[msgCat_No] = {
        'default': 'no',
        'action': callbackNo
    };

    // Display the confirmation dialog
    $.confirm({
        'message': message,
        'buttons': buttonsList
    });
}

/**
 * Displays a information dialog with okay button to dismiss
 * @param message Message to display
 * @param callbackOkay Callback for when okay is clicked
 */
function displayInformation(message, callbackOkay) {

    if (!callbackOkay) {
        callbackNo = function () {
        };
    }
    // List of buttons to display
    var buttonsList = {};

    // Configuration for selecting yes
    buttonsList[msgCat_Ok] = {
        'default': 'yes',
        'action': callbackOkay
    };

    // Display the confirmation dialog
    $.confirm({
        'message': message,
        'buttons': buttonsList
    });
}
