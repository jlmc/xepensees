package org.xine.xepensees.presentation.faces.messages;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class Messages implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesContext facesContext;
	
	public void addMessageFlash(final FacesMessage message) {
		this.facesContext.
			 getExternalContext().
			 getFlash().
			 setKeepMessages(true);
		this.facesContext.
			addMessage(null, message);
	}

	public void addMessage(final FacesMessage message) {
		this.facesContext.addMessage(null, message);
	}

	public void addSucessMessageFlash(final String summary) {
		addMessageFlash(new FacesMessage(summary));
	}

	public void addErrorMessageFlash(final String detail, final String summary) {
		addMessageFlash(new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	public void addErrorMessageFlash(final String summary) {
		this.addErrorMessageFlash("", summary);
	}

}
