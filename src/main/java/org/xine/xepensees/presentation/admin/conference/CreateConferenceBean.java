package org.xine.xepensees.presentation.admin.conference;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.conference.boundary.ConferencesMng;
import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.presentation.faces.messages.Messages;

@Named
@RequestScoped
public class CreateConferenceBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Conference conference;

	@Inject
	private ConferencesMng conferencesMng;

	@Inject
	private Messages messages;

	@PostConstruct
	public void setUp() {
		this.conference = Conference.empty();
	}

	public Conference getConference() {
		return this.conference;
	}

	public Object create() {

		this.conferencesMng.create(this.conference);

		this.messages
				.addSucessMessageFlash(String.format("Conference %s create with sucess", this.conference.getName()));

		return "/admin/conference/create?faces-redirect=true";
	}
}
