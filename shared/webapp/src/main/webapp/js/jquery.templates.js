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


function refreshTemplate(ajaxUrl, htmlElement, templateName, emptyContentMessage, successCallback, failCallback) {
    $.mobile.showPageLoadingMsg();
    $(htmlElement).text('');
    var dynamicDataResp = $.ajax({
        url: ajaxUrl,
        dataType: 'json',
        async: false,
        cache: false,
    });
    dynamicDataResp.done(function () {
        var dynamicDataObj = jQuery.parseJSON(dynamicDataResp.responseText);
        $.tmpl(templateName, dynamicDataObj).appendTo(htmlElement);
        if (!$(htmlElement).children().length) {
            $(htmlElement).html(emptyContentMessage);
        }
        if (successCallback) {
            successCallback();
        }
    });
    dynamicDataResp.fail(function () {
        $.mobile.hidePageLoadingMsg();
        if (failCallback) {
            failCallback();
        }
        alert("An error has occurred. Make sure you have network connectivity.");
    });
    dynamicDataResp.always(function () {
        $.mobile.hidePageLoadingMsg();
    });
}

function setPageTitle(title) {
    $('div[data-role=page] div[data-role=header] h1').text(title);
    $('title').text(title);
    return "";
}
