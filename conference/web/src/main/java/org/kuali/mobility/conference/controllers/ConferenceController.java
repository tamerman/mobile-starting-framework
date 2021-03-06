/**
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

package org.kuali.mobility.conference.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.kuali.mobility.conference.entity.Attendee;
import org.kuali.mobility.conference.entity.ContentBlock;
import org.kuali.mobility.conference.entity.MenuItem;
import org.kuali.mobility.conference.entity.Session;
import org.kuali.mobility.conference.entity.SessionFeedback;
import org.kuali.mobility.conference.service.ConferenceService;
import org.kuali.mobility.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private EmailService emailService;

	public void setConferenceService(ConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model uiModel) {
		Date d = new Date();
		DateFormat formatter = new SimpleDateFormat("MMddyy");
		String today = formatter.format(d);
		if ("092811".equals(today) || "092911".equals(today) || "093011".equals(today) || "100111".equals(today)) {
		} else {
			today = "";
		}
		//List<MenuItem> menuItems = conferenceService.findAllMenuItems();
		//uiModel.addAttribute("menuItems", menuItems);
		//uiModel.addAttribute("today", today);
		return "conference/index";
	}

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String getMenuJson(@RequestParam(value = "lang", required = false) String lang) {
		List<MenuItem> menuItems = conferenceService.findAllMenuItems(lang);
		return new JSONSerializer().exclude("*.class").deepSerialize(menuItems);
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(Model uiModel) {
		List<ContentBlock> contentBlocks = conferenceService.findAllContentBlocks();
		uiModel.addAttribute("contentBlocks", contentBlocks);
		return "conference/welcome";
	}

	@RequestMapping(value = "/featuredSpeakers", method = RequestMethod.GET)
	public String featuredSpeakers(Model uiModel) {
		List<ContentBlock> contentBlocks = conferenceService.findFeaturedSpeakers();
		uiModel.addAttribute("contentBlocks", contentBlocks);
		return "conference/featuredSpeakers";
	}

	@RequestMapping(value = "/attendeeGroups", method = RequestMethod.GET)
	public String attendeeGroups(Model uiModel) {
		return attendees("A", "Z", uiModel);
		//return "conference/attendeeGroups";
	}

	@RequestMapping(value = "/attendees", method = RequestMethod.GET)
	public String attendees(@RequestParam(value = "start", required = true) String start, @RequestParam(value = "end", required = true) String end, Model uiModel) {
		List<Attendee> attendees = conferenceService.findAllAttendees(start.charAt(0), end.charAt(0));
		uiModel.addAttribute("attendees", attendees);
		return "conference/attendees";
	}

	@RequestMapping(value = "/attendeeDetails/{id}", method = RequestMethod.GET)
	public String attendeeDetails(@PathVariable("id") String id, Model uiModel) {
		Attendee attendee = conferenceService.findAttendeeById(id);
		uiModel.addAttribute("attendee", attendee);
		return "conference/attendeeDetails";
	}

	@RequestMapping(value = "/sessions", method = RequestMethod.GET)
	public String sessions(@RequestParam(value = "date", required = false) String date, Model uiModel) {
		List<Session> sessions = conferenceService.findAllSessions(date);
		uiModel.addAttribute("sessions", sessions);
		return "conference/sessions";
	}

	@RequestMapping(value = "/sessionDetails/{id}", method = RequestMethod.GET)
	public String sessionDetails(@PathVariable("id") String id, Model uiModel) throws UnsupportedEncodingException {
		Session session = conferenceService.findSessionById(id);
		uiModel.addAttribute("session", session);
		uiModel.addAttribute("sessionFeedback", new SessionFeedback());
		return "conference/sessionDetails";
	}

	@RequestMapping(value = "/sessionFeedback", method = RequestMethod.POST)
	public String submitFeedback(Model uiModel, @ModelAttribute("sessionFeedback") SessionFeedback sessionFeedback) {
		sessionFeedback.setTimePosted(new Timestamp(System.currentTimeMillis()));
		sendEmail(sessionFeedback);
		return "conference/sessionFeedbackThanks";
	}

	@RequestMapping(value = "/sessionSpeakerDetails", method = RequestMethod.GET)
	public String sessionSpeakerDetails(Model uiModel, @RequestParam(required = true) int id) {
		List<Session> sessions = conferenceService.findAllSessions("");
		uiModel.addAttribute("session", sessions.get(id));
		return "conference/sessionSpeakerDetails";
	}

	private void sendEmail(SessionFeedback f) {
		try {
			emailService.sendEmail(f.toString(), "SWITC Feedback; " + f.getSessionId() + ":" + f.getRating(), conferenceService.getToEmailAddress(), conferenceService.getFromEmailAddress());
		} catch (Exception e) {
		}
	}

}
