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
    $('#addViewPermissionButton').click(function () {
        var groupName = $('#newViewPermission').val();
        checkPermissionGroup(groupName, false);
    });
    $('#addEditPermissionButton').click(function () {
        var groupName = $('#newEditPermission').val();
        checkPermissionGroup(groupName, true);
    });
});

function checkPermissionGroup(groupName, edit) {
    if (groupName) {
        groupName = groupName.toUpperCase();
        if (!findExistingPermission(groupName, edit)) {
            validateGroup(groupName, edit);
        } else {
            alert('That group has already been added.');
        }
    } else {
        alert('Please enter an ADS group.');
    }
}

function findExistingPermission(groupName, edit) {
    var result = false;
    if (!edit) {
        $('#viewPermissionsList li').each(function (index, element) {
            var group = $('span.group', element).text();
            if (group == groupName) result = true;
        });
    } else {
        $('#editPermissionsList li').each(function (index, element) {
            var group = $('span.group', element).text();
            if (group == groupName) result = true;
        });
    }
    return result;
}

function validateGroup(groupName, edit) {
    var url = contextPath + '/tours/verify?group=' + groupName;
    var request = $.ajax({
        url: url,
        dataType: 'json'
    });

    request.fail(function () {
        alert('There was a problem verifying the ADS group name.');
    });
    request.done(function (data) {
        if (data.valid) {
            addPermissionGroup(groupName, edit)
        } else {
            alert(groupName + ' is not a valid ADS group.');
        }
    });
}

function addPermissionGroup(groupName, edit) {
    var li = $('<li></li>');
    var x = $('<button class="removePermissionLink"><a href="#">x</a></button>');
    x.click(removePermission);
    li.append(x);
    li.append('<span class="group">' + groupName + '</span>');

    if (!edit) {
        $('#publicTour').hide();
        $('#viewPermissionsList').append(li);
        $('#newViewPermission').val('');
    } else {
        $('#editPermissionsList').append(li);
        $('#newEditPermission').val('');
    }
}

function removePermission(event) {
    var li = $(this).parent().remove(); //remove the <li>

    //check if we removed the last view group
    if (!$('#viewPermissionsList li').length) {
        $('#publicTour').show();
    }
}
