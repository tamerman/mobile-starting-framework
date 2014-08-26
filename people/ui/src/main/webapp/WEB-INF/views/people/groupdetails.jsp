<%--

    The MIT License
    Copyright (c) 2011 Kuali Mobility Team

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

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme"  uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="people.searchResults" var="msgCat_ToolTitle"/>
<spring:message code="people.addAsNewContact" var="msgCat_AddAsNewContact"/>
<spring:message code="people.contactExists" var="msgCat_ContactExists"/>
<spring:message code="people.savedContact" var="msgCat_SavedContact"/>
<spring:message code="people.errorFindingContact" var="msgCat_ErrorFindingContact"/>
<spring:message code="people.groupNotFound" var="msgCat_GroupNotFound"/>


<kme:page title="${msgCat_ToolTitle}" id="people" backButton="true" homeButton="true" cssFilename="people" appcacheFilename="iumobile.appcache" onBodyLoad="init()">
<script type="text/javascript" charset="utf-8">
var browserDetect 	= window.kme.browserDetect;
// Wait for PhoneGap to load
//
function init() {
	// alert($('#savecontact').html());
	document.addEventListener("deviceready", onDeviceReady, false);
}

// PhoneGap is ready
//
function onDeviceReady() {

}
var fname 		= "";
var lname 		= "";
var contactID 	= "";
var newContact 	= "";


/**
 * Passing the arguments rather than scanning the DOM. It would require re-write of the template below.
 */ 
function saveContact(firstname, lastname, dept, email, phone){
	
	fname = firstname;
	lname = lastname;
	
	
	/* Android/iOS Block */
	if(browserDetect.isOS(BrowserDetect.OSes.Android) || browserDetect.isIOS()){ 
		/*
		if(existing contact)
			add missing info	
		else
			add new contact
		*/
		
		// find all contacts with 'Bob' in any name field
		var options 		= new ContactFindOptions();
		options.filter 		= lastname;
		options.multiple 	= true;
		var fields 			= ["displayName", "name", "emails"];
		navigator.contacts.find(fields, onFindSuccess, onFindError, options);
		
		var myContact = navigator.contacts.create();
		myContact.displayName = firstname + " " + lastname;	

		var name = new ContactName();
		name.givenName = firstname;
		name.familyName = lastname;
		myContact.name = name; 
	
		var phoneNumbers = [];
		phoneNumbers[0] = new ContactField('IU', phone, true);
		myContact.phoneNumbers = phoneNumbers;

		var emails = [];
		emails[0] = new ContactField('IU', email, true);
		myContact.emails = emails;
	
		// Saves the Contact to Android. 
		newContact = myContact;
		
/*
		if(confirm("Add New Contact")){		
			myContact.save(onSaveSuccess, onSaveError);
		}else{
			
		}
*/
	}
	/* End Android/iOS Block */

	
	/* Blackberry Block */
	// TODO
	/* End Black Berry Block */
}

/**
 * Callback when finding a contact was successful
 */
function onFindSuccess(contacts) {

	c = 0;
	for(var i = 0; i < contacts.length; i++){
		if(contacts[i].name.formatted.indexOf(fname) != -1){
			c++;
			contactID = contacts[i].id;
		}
	}
	
	if(contactID != ""){
		alert('${msgCat_ContactExists}' + contactID);
	}else{
		if(confirm('${msgCat_AddAsNewContact}')){
			newContact.save(onSaveSuccess, onSaveError);
		}
	}
	
};

/**
 * Callback when there was an error finding a contact
 */
function onFindError(contactError) {
	alert('${msgCat_ErrorFindingContact}');
};


/**
 * Callback when saving a contact was successful
 */
function onSaveSuccess(contact) {
	//Only Displays on Android.
	alert("${msgCat_SavedContact}");
};

/**
 * Callback when there was an error saving a contact
 */
function onSaveError(contactError) {
	alert("Save Error = " + contactError.code);
};
</script>
	<kme:content>
		<kme:listView id="detailsGroupList" filter="false" dataTheme="c" dataInset="false">
			<script type="text/javascript">		
				var nativeCookie 	= $.cookie('native');
				var pgCookie 		= $.cookie('phonegap');
				var canSaveContact = 0;
				if(nativeCookie == "yes" && pgCookie != ''){
					canSaveContact = 1;
				}
				$('[data-role=page][id=people]').live('pagebeforeshow', function(event, ui) {
					$('#detailsTemplate').template('detailsTemplate');
					refreshTemplate('${pageContext.request.contextPath}/services/directory/group/lookup?dn=${groupName}',
							'#detailsGroupList',
							'detailsTemplate',
							'<li>${msgCat_GroupNotFound}</li>',
							function() {
								$('#detailsGroupList').listview('refresh');
							});
				});
			</script>
			<script id="detailsTemplate" type="text/x-jquery-tmpl">
			  {{if group}}
				
				{{if group.displayName}}
					<li data-role="list-divider" ><h3 class="wrap"><spring:message code="people.groupName"/>:
						<span style="font-weight:normal;">\${group.displayName}</span></h3></li>
				{{/if}}
				
				{{if group.descriptions && group.descriptions.length > 0}}
				{{each(i,description) group.descriptions}}
					{{if description != '' }}
					<li><h3 class="wrap"><spring:message code="people.groupDescription" />:
						<span style="font-weight:normal;">\${description}</span>
						</h3>
					</li>
					{{/if}}
					{{if i+1 < group.descriptions.length}}, {{/if}}
				{{/each}}
				{{/if}}
				{{if group.email != 'null@umich.edu' }}
					<li class="link-email">
						<a href="mailto:\${group.email}" >\${group.email}</a>
					</li>
				{{/if}}
				
				{{if group.telephoneNumber && group.telephoneNumber.length > 0}}
					<li class="link-phone"><a href="tel:\${group.telephoneNumber}">\${group.telephoneNumber}</a></li>
				{{/if}}

				{{if group.facsimileTelephoneNumber && group.facsimileTelephoneNumber.length > 0}}
					<li>
						<span style="font-weight:normal;">\${group.facsimileTelephoneNumber}</span>
					</li>
				{{/if}}

				<li class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-btn-up-c" data-theme="c">
					<div class="ui-btn-inner ui-li">
						<div class="ui-btn-text">
							<a a class="ui-link-inherit" href="${pageContext.request.contextPath}/people/group/groupmembers/${groupName}"><spring:message code="people.viewMembers"/></a>
						</div>
					</div>
				</li>
			{{/if}}	
			</script>
		</kme:listView>
	</kme:content>
</kme:page>
