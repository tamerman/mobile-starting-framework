/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

//  Copyright 2011-2012 The Kuali Foundation Licensed under the Educational Community
//  License, Version 2.0 (the "License"); you may not use this file except in
//  compliance with the License. You may obtain a copy of the License at
//  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
//  agreed to in writing, software distributed under the License is distributed
//  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
//  express or implied. See the License for the specific language governing
//  permissions and limitations under the License.
$(document).ready(function() {
	$.validator.addMethod(
		"greaterThan",
		function(value, element, params) {
			var target = $("#" + params).val().replace(/-/g, '');
			var val = value.replace(/-/g, '');
			return val > target;
		},
		msgCat_ValEndate);
	
	$("#inputForm").validate({
		wrapper: 'div', 
		rules: {
			// Validation of the start date
			startDate: {
				required: true,
				date: false
			},
			// Validation of the end date
			endDate: {
				required: true,
				date: false,
				greaterThan : 'startDate'
			}
		},
		messages: validation_messages,
		errorPlacement: function(error, element) {
			error.appendTo(element.parent().parent());
		},
	});
	
	
	// Search botton action
	$("#btnSearch").click(function() {
		if ($("#inputForm").valid()) { // First validate form
			$("#inputForm").submit();
		}
	});
	
	/* If we had newer jqm the date tool could also be 
	 * updated to do this automaticallu
	 */
	$("#startDate").live('focus', function() {
		$('#startDate').datebox && $('#startDate').datebox('open');
	});
	$("#endDate").live('focus', function() {
		$('#endDate').datebox && $('#endDate').datebox('open');
	});
	
	/**
	 * Defaults for date fields
	 */
	 var year = new Date().getFullYear();
	 var defaultStartDate = [year, 0, 1];
	 var defaultEndDate = [year, 11, 31];
	
	
	// Set the default dates
	$('#startDate').data('datebox') && ($('#startDate').data('datebox').options.defaultPickerValue = defaultStartDate);
	$('#endDate').data('datebox') && ($('#endDate').data('datebox').options.defaultPickerValue = defaultEndDate);
	$('#startDate').val(year + '-01-01')
	$('#endDate').val(year + '-12-31')
}); // End ready
		
