package org.xine.xepensees.presentation.faces;

/**
 * The JSF controller returns the response to the browser with the status 302
 * (Moved temporarily). This code tells the browser that it is necessary
 * make a new request to the URL contained in the header location of the response.
 */
public class RedirectView {

	private final String viewName;

	public RedirectView(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String toString() {
		return this.viewName + "?faces-redirect=true";
	}

	public static RedirectView of (String viewName) {
		return new RedirectView(viewName);
	}
}
