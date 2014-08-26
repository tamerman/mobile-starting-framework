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

<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme"  uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="people.contactExists" var="msgCat_ContactExists"/>
<spring:message code="people.addAsNewContact" var="msgCat_AddAsNewContact"/>
<spring:message code="people.savedContact" var="msgCat_SavedContact"/>
<spring:message code="people.personNotFound" var="msgCat_PersonNotFound"/>



<spring:message code="people.searchResults" var="msgCat_ToolTitle"/>
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


        // Passing the arguments rather than scanning the DOM. It would require re-write of the template below. 
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

        function onFindSuccess(contacts) {

            c = 0;
            for(i = 0; i < contacts.length; i++){
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


        function onFindError(contactError) {
            alert('onError!');
        };

        function onSaveSuccess(contact) {
            //Only Displays on Android.
            alert("${msgCat_SavedContact}");
        };

        function onSaveError(contactError) {
            alert("Save Error = " + contactError.code);
        };


    </script>
    <kme:content>
        <kme:listView id="detailsList" filter="false" dataTheme="c" dataInset="false">
            <script type="text/javascript">		
                var nativeCookie 	= $.cookie('native');
                var pgCookie 		= $.cookie('phonegap');
                var canSaveContact = 0;
                if(nativeCookie == "yes" && pgCookie != ''){
                    canSaveContact = 1;
                }
				
                $('[data-role=page][id=people]').live('pagebeforeshow', function(event, ui) {
                    $('#detailsTemplate').template('detailsTemplate');
                    refreshTemplate('${pageContext.request.contextPath}/services/directory/person/lookup?_type=json&username=${person.userName}', '#detailsList', 'detailsTemplate', '<li>${msgCat_PersonNotFound}</li>', function() {$('#detailsList').listview('refresh');});				
                });
            </script>
            <script id="detailsTemplate" type="text/x-jquery-tmpl">			
                {{if person}}
                {{if person.lastName}}
                <li data-role="list-divider">\${person.lastName}, \${person.firstName}</li>
                {{else}}
                <li data-role="list-divider">\${person.displayName}</li>
                {{/if}}

                {{if (person.locations && person.locations.length > 0) || (person.affiliations && person.affiliations.length > 0)
                || (person.departments && person.departments.length > 0) || person.address }}
                <li>
                    {{if person.locations && person.locations.length > 0}}
                    <h3 class="wrap">:
                        <span style="font-weight:normal;">
                            {{each(i,location) person.locations}}
                            \${location}{{if i+1 < person.locations.length}}, {{/if}}
                            {{/each}}
                        </span>
                    </h3>
                    {{/if}}
                    {{if person.departments && person.departments.length > 0}}
                    <h3 class="wrap"><spring:message code="people.department" />:
                        <span style="font-weight:normal;">
                            {{each(i,department) person.departments}}
                            \${department}{{if i+1 < person.departments.length}}, {{/if}}
                            {{/each}}
                        </span>
                    </h3>
                    {{/if}}
                    {{if person.affiliations && person.affiliations.length > 0}}
                    <h3 class="wrap"><spring:message code="people.affiliation" />: 
                        <span style="font-weight:normal;">
                            {{each(i,affiliation) person.affiliations}}
                            \${affiliation}{{if i+1 < person.affiliations.length}}, {{/if}}
                            {{/each}}									
                        </span>
                    </h3>
                    {{/if}}
                    {{if person.address}}
                    <h3 class="wrap"><spring:message code="people.address" />: <span style="font-weight:normal;">\${person.address}</span></h3>
                    {{/if}}
                </li>
                {{/if}}

                {{if person.email}}
                <li class="link-email">
                    <a href="mailto:\${person.email}" >\${person.email}</a>
                </li>
                {{/if}}

                {{if person.phone}}
                <li class="link-phone"><a href="tel:\${person.phone}">\${person.phone}</a></li>
                {{/if}}				

                {{if canSaveContact}}
                <li>
                    <%--
                        <a id="savecontact" data-icon="plus" href="#" data-role="button" onclick="saveContact('\${person.firstName}', '\${person.lastName}', '\${person.departments}', '\${person.email}', '\${person.phone}')" data-theme="c">
                                Save Contact
                        </a>
                    --%>
                </li>
                {{/if}}

                {{else}}
                <li><spring:message code="people.personNotFound" /></li>
                {{/if}}

                </script>
            </kme:listView>

            <%-- 
                    {{if person.email}}
                            <li class="link-email">
                                    {{if loggedIn}}
                                            <a href="mailto:\${person.email}" >\${person.email}</a>
                                    {{else}}
                                            <img src="${pageContext.request.contextPath}/people/image/\${imageKey}" alt="email" />
                                    {{/if}}
                            </li>
                    {{/if}}
            --%>

            <%-- <c:choose>
                    <c:when test="${person != null}">
                            <kme:listView id="peopleList" filter="false" dataTheme="c" dataInset="false">
                                    <li data-role="list-divider"><c:out value="${person.lastName}" />, <c:out value="${person.firstName}" /></li>
                                    <li>
                                            <c:if test="${not empty person.locations}" >
                                                    <h3 class="wrap">Campus:
                                                            <span style="font-weight:normal;"><c:forEach items="${person.locations}" var="location" varStatus="status">
                                                                    <c:out value="${location}" /><c:if test="${not status.last}">, </c:if>
                                                            </c:forEach></span>
                                                    </h3>
                                            </c:if>
                                            <c:if test="${not empty person.departments}" >
                                                    <h3 class="wrap">Department:
                                                            <span style="font-weight:normal;"><c:forEach items="${person.departments}" var="department" varStatus="status">
                                                                    <c:out value="${department}" /><c:if test="${not status.last}">, </c:if>
                                                            </c:forEach></span>
                                                    </h3>
                                            </c:if>
                                            <c:if test="${not empty person.affiliations}" >
                                                    <h3 class="wrap">Affiliation:
                                                            <span style="font-weight:normal;"><c:forEach items="${person.affiliations}" var="affiliation" varStatus="status">
                                                                    <c:out value="${affiliation}" /><c:if test="${not status.last}">, </c:if>
                                                            </c:forEach></span>
                                                    </h3>
                                            </c:if>
                                            <c:if test="${not empty person.address}" >
                                                    <h3 class="wrap">Address: <span style="font-weight:normal;"><c:out value="${person.address}" /></span></h3>
                                            </c:if>
                                    </li>
                                    <c:if test="${not empty person.email}" >
                                            <li class="link-email">
                                                    <c:choose>
                                                            <c:when test="${loggedIn == true}">
                                                                    <c:set var="email"><c:out value="${person.email}" /></c:set>
                                                                    <a href="mailto:${email}" ><c:out value="${email}" /></a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                    <img src="${pageContext.request.contextPath}/people/image/${imageKey}" alt="email" />
                                                            </c:otherwise>
                                                    </c:choose>
                                            </li>
                                    </c:if>
                                    
                                    <c:if test="${not empty person.phone}" >
                                            <c:set var="phone"><c:out value="${person.phone}" /></c:set>
                                            <li class="link-phone"><a href="tel:${phone}"><c:out value="${phone}" /></a></li>
                                    </c:if>
                            </kme:listView>
                    </c:when>
                    <c:otherwise>
                            The person was not found.
                    </c:otherwise>
            </c:choose>--%>


        </kme:content>
    </kme:page>
