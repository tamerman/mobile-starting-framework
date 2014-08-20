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

 
package org.kuali.mobility.reporting.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.file.entity.File;
import org.kuali.mobility.reporting.domain.Incident;
import org.kuali.mobility.reporting.entity.Submission;
import org.kuali.mobility.reporting.entity.SubmissionAttribute;
import org.kuali.mobility.reporting.service.ReportingService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller 
@RequestMapping("/reporting")
public class ReportingController {

	private static Logger LOG = LoggerFactory.getLogger(ReportingController.class);

	public static final String INCIDENT_TYPE = "INCIDENT";
	public static final String INCIDENT_GROUP = "INCIDENT_GROUP";
	public static final String SUMMARY = "SUMMARY";
	public static final String EMAIL = "EMAIL";
	public static final String AFFILIATION_STUDENT = "AFFILIATION_STUDENT";
	public static final String AFFILIATION_FACULTY = "AFFILIATION_FACULTY";
	public static final String AFFILIATION_STAFF = "AFFILIATION_STAFF";
	public static final String AFFILIATION_OTHER = "AFFILIATION_OTHER";
	public static final String CONTACT_ME = "CONTACT_ME";
	public static final String ATTACHMENT = "ATTACHMENT";
	public static final String COMMENT = "COMMENT";
	
    @Autowired
    private ReportingService reportingService;
    
    @Resource(name="messageSource")
    private MessageSource messageSource;
    
    @Resource(name="kmeProperties")
    private Properties kmeProperties;
    

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel, HttpServletRequest request) {
    	//User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
   			return "ui3/reporting/index";
   		} else {
   			return "reporting/index";
   		}
    }

    
    // 
    // Reporting Administration Front End
    //
    
    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public String adminIndex(Model uiModel, HttpServletRequest request) {
    	//User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		
    	// TODO: Based on the user, find the reporting types that she is allowed to see and use as the filter.
    	
    	List<Submission> submissions = reportingService.findAllSubmissions();
    	
   		uiModel.addAttribute("submissions", submissions);
   		if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
   			return "ui3/reporting/adminIndex";
   		} else {
   			return "reporting/admin/index";
   		}
    }

    @RequestMapping(value = "/admin/incident/details/{id}", method = RequestMethod.GET)
    public String adminDetails(@PathVariable("id") Long id, Model uiModel, HttpServletRequest request) {
    	//User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		    	
    	prepareSubmissionById(id, uiModel, request);
   		
   		return "reporting/admin/incident/details";
    }   
    
    @RequestMapping(value = "/admin/incident/save", method = RequestMethod.POST)
    public String saveSumission(HttpServletRequest request, ModelMap model, @ModelAttribute("file") File file, BindingResult result, SessionStatus status) {
    	
    	if (file != null && file.getFile() != null) {
    		String contentType = file.getFile().getContentType();
    		String fileName = file.getFile().getOriginalFilename();
    		file.setContentType(contentType);
    		file.setFileName(fileName);
    		try {
				file.setBytes(file.getFile().getBytes());
			} catch (IOException e) {
				LOG.error("File contained no bytes.", e);
			}
    		reportingService.saveAttachment(file);
    	}
    	return "reporting/admin/index";
    	//return "redirect:manageFiles.do?groupId=" + groupId;
    }
    
    @RequestMapping(value = "/admin/incident/revisions/{id}", method = RequestMethod.GET)
    public String revisions(@PathVariable("id") Long id, Model uiModel, HttpServletRequest request) {
    	//User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		
    	// TODO: Based on the user, find the reporting types that she is allowed to see and use as the filter.

    	Submission submission = reportingService.findSubmissionById(id);
    	Long parentId = submission.getParentId() == null ? submission.getId() : submission.getParentId();

    	List<Submission> submissions = reportingService.findAllSubmissionsByParentId(parentId);
    	
   		uiModel.addAttribute("submissions", submissions);    	
    	return "reporting/admin/incident/revisions";
    }
    
    @RequestMapping(value = "/admin/incident/savefile", method = RequestMethod.POST)
    public String addFile(HttpServletRequest request, ModelMap model, @ModelAttribute("file") Incident incident, BindingResult result, SessionStatus status) {
    	
    	Submission oldSubmission = reportingService.findSubmissionById(incident.getId());
    	oldSubmission.setActive(0);
    	oldSubmission.setArchivedDate(new Timestamp(System.currentTimeMillis()));
    	// Set revision attributes for oldSubmission
    	reportingService.saveSubmission(oldSubmission);

    	Long parentId = oldSubmission.getParentId() == null ? oldSubmission.getId() : oldSubmission.getParentId();
    	
    	Submission revisedSubmission = new Submission();
    	revisedSubmission.setActive(1);
    	revisedSubmission.setParentId(parentId);
    	revisedSubmission.setPostDate(new Timestamp(System.currentTimeMillis()));
    	revisedSubmission.setRevisionNumber(oldSubmission.getRevisionNumber() + 1);
    	revisedSubmission.setType(INCIDENT_TYPE);
    	revisedSubmission.setGroup(INCIDENT_GROUP);
    	
    	Long pk = reportingService.saveSubmission(revisedSubmission);
    	
    	SubmissionAttribute submissionAttributeSummary = new SubmissionAttribute();
    	submissionAttributeSummary.setKey(SUMMARY);
    	submissionAttributeSummary.setValueLargeText(incident.getSummary());
    	submissionAttributeSummary.setSubmissionId(pk);
    	submissionAttributeSummary.setSubmission(revisedSubmission);
    	
    	SubmissionAttribute submissionAttributeContactMe = new SubmissionAttribute();
    	submissionAttributeContactMe.setKey(CONTACT_ME);
    	submissionAttributeContactMe.setValueNumber(incident.isContactMe() ? 1L : 0L);
    	submissionAttributeContactMe.setSubmissionId(pk);
    	submissionAttributeContactMe.setSubmission(revisedSubmission);
    	
    	SubmissionAttribute submissionAttributeEmail = new SubmissionAttribute();
    	submissionAttributeEmail.setKey(EMAIL);
    	submissionAttributeEmail.setValueText(incident.getEmail());
    	submissionAttributeEmail.setSubmissionId(pk);
    	submissionAttributeEmail.setSubmission(revisedSubmission);
    	
    	SubmissionAttribute submissionAttributeAffiliationStudent = new SubmissionAttribute();
    	submissionAttributeAffiliationStudent.setKey(AFFILIATION_STUDENT);
    	submissionAttributeAffiliationStudent.setValueText(incident.getAffiliationStudent());
    	submissionAttributeAffiliationStudent.setSubmissionId(pk);
    	submissionAttributeAffiliationStudent.setSubmission(revisedSubmission);
    	
    	SubmissionAttribute submissionAttributeAffiliationFaculty = new SubmissionAttribute();
    	submissionAttributeAffiliationFaculty.setKey(AFFILIATION_FACULTY);
    	submissionAttributeAffiliationFaculty.setValueText(incident.getAffiliationFaculty());
    	submissionAttributeAffiliationFaculty.setSubmissionId(pk);
    	submissionAttributeAffiliationFaculty.setSubmission(revisedSubmission);
    	
    	SubmissionAttribute submissionAttributeAffiliationStaff = new SubmissionAttribute();
    	submissionAttributeAffiliationStaff.setKey(AFFILIATION_STAFF);
    	submissionAttributeAffiliationStaff.setValueText(incident.getAffiliationStaff());
    	submissionAttributeAffiliationStaff.setSubmissionId(pk);
    	submissionAttributeAffiliationStaff.setSubmission(revisedSubmission);
    	
    	SubmissionAttribute submissionAttributeAffiliationOther = new SubmissionAttribute();
    	submissionAttributeAffiliationOther.setKey(AFFILIATION_OTHER);
    	submissionAttributeAffiliationOther.setValueText(incident.getAffiliationOther());
    	submissionAttributeAffiliationOther.setSubmissionId(pk);
    	submissionAttributeAffiliationOther.setSubmission(revisedSubmission);
    	
    	ArrayList<SubmissionAttribute> attributes = new ArrayList<SubmissionAttribute>();
    	attributes.add(submissionAttributeSummary);
    	attributes.add(submissionAttributeEmail);
    	attributes.add(submissionAttributeContactMe);
    	attributes.add(submissionAttributeAffiliationStudent);
    	attributes.add(submissionAttributeAffiliationFaculty);
    	attributes.add(submissionAttributeAffiliationStaff);
    	attributes.add(submissionAttributeAffiliationOther);
    	    	    	
    	// Save new attachment if available
    	if (incident != null && incident.getFile() != null) {
        	SubmissionAttribute newAttachment = new SubmissionAttribute();
        	newAttachment.setKey(ATTACHMENT);
        	newAttachment.setSubmissionId(pk);
        	newAttachment.setSubmission(revisedSubmission);
        	newAttachment.setContentType(incident.getFile().getContentType());
    		newAttachment.setFileName(incident.getFile().getOriginalFilename());
    		try {
    			newAttachment.setValueBinary(incident.getFile().getBytes());
			} catch (IOException e) {
				LOG.error("File contained no bytes.", e);
			}
    		attributes.add(newAttachment);
    	}

    	// Save new comment if available
    	if (incident != null && incident.getNewComment() != null) {
        	SubmissionAttribute newComment = new SubmissionAttribute();
        	newComment.setKey(COMMENT);
        	newComment.setSubmissionId(pk);
        	newComment.setSubmission(revisedSubmission);
   			newComment.setValueLargeText(incident.getNewComment());
    		attributes.add(newComment);
    	}
    	
    	// Need the comments and attachments from the oldSubmission
    	for (SubmissionAttribute submissionAttribute : findAttributesByKey(ATTACHMENT, oldSubmission.getAttributes())) {
        	SubmissionAttribute oldAttachment = new SubmissionAttribute();
        	oldAttachment.setKey(ATTACHMENT);
        	oldAttachment.setSubmissionId(revisedSubmission.getId());
        	oldAttachment.setSubmission(revisedSubmission);
        	oldAttachment.setContentType(submissionAttribute.getContentType());
    		oldAttachment.setFileName(submissionAttribute.getFileName());				
			oldAttachment.setValueBinary(submissionAttribute.getValueBinary());
    		attributes.add(oldAttachment);
		}
    	for (SubmissionAttribute submissionAttribute : findAttributesByKey(COMMENT, oldSubmission.getAttributes())) {
        	SubmissionAttribute oldComment = new SubmissionAttribute();
        	oldComment.setKey(COMMENT);
        	oldComment.setSubmissionId(revisedSubmission.getId());
        	oldComment.setSubmission(revisedSubmission);
			oldComment.setValueLargeText(submissionAttribute.getValueLargeText());
    		attributes.add(oldComment);
		}
    	
    	revisedSubmission.setAttributes(attributes);
    	
    	reportingService.saveSubmission(revisedSubmission);
    	
    	return "reporting/admin/index";
    	//return "redirect:manageFiles.do?groupId=" + groupId;
    }
    
    @RequestMapping(value = "/admin/incident/edit/{id}", method = RequestMethod.GET)
    public String adminEdit(@PathVariable("id") Long id, Model uiModel, HttpServletRequest request) {
    	//User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

    	Submission submission = reportingService.findSubmissionById(id);
    			
   		Incident incident = new Incident();

   		prepareSubmissionById(id, uiModel, request);
    	
   		String affiliationStudent = findAttributeByKey(AFFILIATION_STUDENT, submission.getAttributes()).getValueText();
   		String affiliationFaculty = findAttributeByKey(AFFILIATION_FACULTY, submission.getAttributes()).getValueText();
   		String affiliationStaff = findAttributeByKey(AFFILIATION_STAFF, submission.getAttributes()).getValueText();
   		String affiliationOther = findAttributeByKey(AFFILIATION_OTHER, submission.getAttributes()).getValueText();
    	   		
    	incident.setSummary(findAttributeByKey(SUMMARY, submission.getAttributes()).getValueLargeText());
    	incident.setAffiliationFaculty(affiliationFaculty);
    	incident.setAffiliationStudent(affiliationStudent);
    	incident.setAffiliationStaff(affiliationStaff);
    	incident.setAffiliationOther(affiliationOther);
    	    	
    	incident.setAttachments(findAttributesByKey(ATTACHMENT, submission.getAttributes()));
    	incident.setComments(findAttributesByKey(COMMENT, submission.getAttributes()));
    	
   		uiModel.addAttribute("incident", incident);

   		return "reporting/admin/incident/edit";
    }

    
	private void prepareSubmissionById(Long id, Model uiModel, HttpServletRequest request) {
		Submission submission = reportingService.findSubmissionById(id);
    	
   		uiModel.addAttribute("submission", submission);    	   		
   		
   		uiModel.addAttribute("summary", findAttributeByKey(SUMMARY, submission.getAttributes()).getValueLargeText());    	
   		
   		uiModel.addAttribute("email", findAttributeByKey(EMAIL, submission.getAttributes()).getValueText());
   		
   		String affiliationStudent = findAttributeByKey(AFFILIATION_STUDENT, submission.getAttributes()).getValueText();
   		String affiliationFaculty = findAttributeByKey(AFFILIATION_FACULTY, submission.getAttributes()).getValueText();
   		String affiliationStaff = findAttributeByKey(AFFILIATION_STAFF, submission.getAttributes()).getValueText();
   		String affiliationOther = findAttributeByKey(AFFILIATION_OTHER, submission.getAttributes()).getValueText();
   		
   		Locale locale = RequestContextUtils.getLocale(request);
   		String msgCat_Yes = messageSource.getMessage("shared.yes", null, locale);
   		String msgCat_No = messageSource.getMessage("shared.yes", null, locale);
   		
   		if (affiliationStudent != null) {
   			uiModel.addAttribute("affiliationStudent", messageSource.getMessage("reporting.affiliation.student", null, locale));
   		}
   		if (affiliationFaculty != null) {
   			uiModel.addAttribute("affiliationFaculty", messageSource.getMessage("reporting.affiliation.faculty", null, locale));
   		}
   		if (affiliationStaff != null) {
   			uiModel.addAttribute("affiliationStaff", messageSource.getMessage("reporting.affiliation.staff", null, locale));
   		}
   		if (affiliationOther != null) {
   			uiModel.addAttribute("affiliationOther", messageSource.getMessage("reporting.affiliation.other", null, locale));
   		}    	
   		uiModel.addAttribute("affiliations", !(affiliationStudent == null && affiliationFaculty == null && affiliationStaff == null && affiliationOther == null));
   		
   		boolean contactMe = findAttributeByKey(CONTACT_ME, submission.getAttributes()).getValueNumber() == 1 ? true : false;
   		uiModel.addAttribute("contactMe", contactMe);
		uiModel.addAttribute("contactMeText", contactMe ? msgCat_Yes : msgCat_No );
   		uiModel.addAttribute("attachments", findAttributesByKey(ATTACHMENT, submission.getAttributes()));
   		uiModel.addAttribute("comments", findAttributesByKey(COMMENT, submission.getAttributes()));
   		uiModel.addAttribute("activeText", submission.getActive() == 1 ? msgCat_Yes : msgCat_No);
   		
	}
    
    private SubmissionAttribute findAttributeByKey(String key, List<SubmissionAttribute> attributes) {
    	if (key == null || key.trim().equals("")) {
    		return null;
    	}
    	SubmissionAttribute found = null;
    	for (SubmissionAttribute submissionAttribute : attributes) {
			if (key.trim().equals(submissionAttribute.getKey())) {
				found = submissionAttribute;
				break;
			}
		}
    	return found;
    }
    
    private List<SubmissionAttribute> findAttributesByKey(String key, List<SubmissionAttribute> attributes) {
    	if (key == null || key.trim().equals("")) {
    		return null;
    	}
    	List<SubmissionAttribute> found = new ArrayList<SubmissionAttribute>();
    	for (SubmissionAttribute submissionAttribute : attributes) {
			if (key.trim().equals(submissionAttribute.getKey())) {
				found.add(submissionAttribute);
			}
		}
    	return found;
    }
    
    
    //
    // Incident Reporting Tool Front End
    // TODO: Should probably move this to an IncidentController in a separate incident tool 
    // 
    
    @RequestMapping(value="/incidentForm", method = RequestMethod.GET)
    public String incidentForm(Model uiModel, HttpServletRequest request) {
    	//User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
   		Incident incident = new Incident();
   		uiModel.addAttribute("incident", incident);
   		if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
   			return "ui3/reporting/incidentIndex";
   		} else {
   			return "incident/form";
   		}
    }

    @RequestMapping(value="/incidentPost", method = RequestMethod.POST)
    public String submitIncident(Model uiModel, HttpServletRequest request, @ModelAttribute("incident") Incident incident, BindingResult result) {
       	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY); 	
    	    	
        if (isValidIncident(incident, result)) {
        	Submission submission = new Submission();
        	submission.setType(INCIDENT_TYPE);
        	submission.setActive(1);
        	submission.setGroup(INCIDENT_GROUP);
        	//submission.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        	submission.setPostDate(new Timestamp(new Date().getTime()));
        	submission.setRevisionNumber(0L);
        	submission.setUserId(user.getLoginName());
        	//submission.setUserAgent();
        	
            Long pk = reportingService.saveSubmission(submission);

        	SubmissionAttribute submissionAttributeSummary = new SubmissionAttribute();
        	submissionAttributeSummary.setKey(SUMMARY);
        	submissionAttributeSummary.setValueLargeText(incident.getSummary());
        	submissionAttributeSummary.setSubmissionId(pk);
        	submissionAttributeSummary.setSubmission(submission);
        	
        	SubmissionAttribute submissionAttributeContactMe = new SubmissionAttribute();
        	submissionAttributeContactMe.setKey(CONTACT_ME);
        	submissionAttributeContactMe.setValueNumber(incident.isContactMe() ? 1L : 0L);
        	submissionAttributeContactMe.setSubmissionId(pk);
        	submissionAttributeContactMe.setSubmission(submission);
        	
        	SubmissionAttribute submissionAttributeEmail = new SubmissionAttribute();
        	submissionAttributeEmail.setKey(EMAIL);
        	submissionAttributeEmail.setValueText(incident.getEmail());
        	submissionAttributeEmail.setSubmissionId(pk);
        	submissionAttributeEmail.setSubmission(submission);
        	
        	SubmissionAttribute submissionAttributeAffiliationStudent = new SubmissionAttribute();
        	submissionAttributeAffiliationStudent.setKey(AFFILIATION_STUDENT);
        	submissionAttributeAffiliationStudent.setValueText(incident.getAffiliationStudent());
        	submissionAttributeAffiliationStudent.setSubmissionId(pk);
        	submissionAttributeAffiliationStudent.setSubmission(submission);
        	
        	SubmissionAttribute submissionAttributeAffiliationFaculty = new SubmissionAttribute();
        	submissionAttributeAffiliationFaculty.setKey(AFFILIATION_FACULTY);
        	submissionAttributeAffiliationFaculty.setValueText(incident.getAffiliationFaculty());
        	submissionAttributeAffiliationFaculty.setSubmissionId(pk);
        	submissionAttributeAffiliationFaculty.setSubmission(submission);
        	
        	SubmissionAttribute submissionAttributeAffiliationStaff = new SubmissionAttribute();
        	submissionAttributeAffiliationStaff.setKey(AFFILIATION_STAFF);
        	submissionAttributeAffiliationStaff.setValueText(incident.getAffiliationStaff());
        	submissionAttributeAffiliationStaff.setSubmissionId(pk);
        	submissionAttributeAffiliationStaff.setSubmission(submission);
        	
        	SubmissionAttribute submissionAttributeAffiliationOther = new SubmissionAttribute();
        	submissionAttributeAffiliationOther.setKey(AFFILIATION_OTHER);
        	submissionAttributeAffiliationOther.setValueText(incident.getAffiliationOther());
        	submissionAttributeAffiliationOther.setSubmissionId(pk);
        	submissionAttributeAffiliationOther.setSubmission(submission);
        	
        	ArrayList<SubmissionAttribute> attributes = new ArrayList<SubmissionAttribute>();
        	attributes.add(submissionAttributeSummary);
        	attributes.add(submissionAttributeEmail);
        	attributes.add(submissionAttributeContactMe);
        	attributes.add(submissionAttributeAffiliationStudent);
        	attributes.add(submissionAttributeAffiliationFaculty);
        	attributes.add(submissionAttributeAffiliationStaff);
        	attributes.add(submissionAttributeAffiliationOther);
        	
        	submission.setAttributes(attributes);
        	
            reportingService.saveSubmission(submission);
            
            return "incident/thanks";                	
        } else {
        	return "incident/form";    	
        }
    }
    
    @RequestMapping(value="/templates/{key}")
    public String getAngularTemplates(
            @PathVariable("key") String key,
            HttpServletRequest request,
            Model uiModel ) {
        return "ui3/reporting/templates/"+key;
    }
    
    @RequestMapping(value = "/js/{key}.js")
    public String getJavaScript(
            @PathVariable("key") String key,
            Model uiModel) {
        return "ui3/reporting/js/"+key;
    }
    
    private boolean isValidIncident(Incident incident, BindingResult result) {
    	boolean hasErrors = false;
    	Errors errors = ((Errors) result);
    	if (incident == null || incident.getSummary() == null || "".equals(incident.getSummary().trim())) {
    		errors.rejectValue("summary", "", "Please enter a summary.");
    		hasErrors = true;
    	}
    	return !hasErrors;
    }


	public Properties getKmeProperties() {
		return kmeProperties;
	}


	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

}
