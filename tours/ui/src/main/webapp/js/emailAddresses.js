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


jQuery(window).load(function () {
    $('#addEmailButton').click(function () {
        var address = $.trim($('#newEmailAddress').val());
        var name = $.trim($('#emailName').val());
        if (!address) {
            alert('An email address is required.');
        } else {
            addEmailAddress(name, address);
        }
    });
});

function addEmailAddress(name, address) {
    var li = $('<li data-value="' + address + '"data-name="' + name + '"></li>');
    var x = $('<button class="removeEmailLink"><a href="#">x</a></button>');
    x.click(removeEmailAddress);
    li.append(x);
    var spanText;
    if (name) {
        spanText = name + ' ' + address;
    } else {
        spanText = address;
    }
    li.append('<span>' + spanText + '</span>');

    $('ul#emailAddresses').append(li);
    $('#newEmailAddress').val('');
    $('#emailName').val('');
}

function removeEmailAddress(event) {
    var li = $(this).parent().remove(); //remove the <li>
}

function addEmailAddressesToPoi(place) {
    place.emailAddresses = new Array();
    $('ul#emailAddresses li').each(function (index, item) {
        var address = $(item).attr('data-value');
        var name = $(item).attr('data-name');
        var email = new Object();
        email.address = address;
        email.name = name;
        place.emailAddresses.push(email);
    });
}

function populateEmailAddresses(addresses) {
    if (addresses) {
        for (var i = 0; i < addresses.length; i++) {
            var email = addresses[i];
            if (email.address) {
                addEmailAddress(email.name, email.address);
            }
        }
    }
}

function clearEmailAddresses() {
    $('#newEmailAddress').val('');
    $('#emailName').val('');
    $('ul#emailAddresses').empty();
}
