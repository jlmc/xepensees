package org.xine.xepensees.presentation.admin.conference;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.conferences.boundary.ConferencesMng;
import org.xine.xepensees.business.conferences.entity.Conference;

@Named
@RequestScoped
public class ViewConferenceView {

	@Inject
	private ConferencesMng conferencesMng;

	private Long id;
	
	private Conference conference;
	
	public void retrive() {
		 if (FacesContext.getCurrentInstance().isPostback()) {
	        return;
	     }
		 
		 if ( this.id != null ) {
			this.conference = this.conferencesMng.getConference(this.id);
		 }
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public Conference getConference() {
		return this.conference;
	}
	
	public void setConference(Conference conference) {
		this.conference = conference;
	}
	
	public void create() {
		
	}
	
	

}
