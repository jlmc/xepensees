package org.xine.xepensees.presentation.admin;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.conferences.boundary.ConferencesMng;
import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.conferences.entity.ConferenceFilter;

@Named
@javax.faces.view.ViewScoped
public class SearchConferencesBean implements Serializable {
	// https://github.com/RicardoToledoB/jsf-paginacion/blob/master/src/main/webapp/index.xhtml
	private static final long serialVersionUID = 1L;

	@Inject
	private ConferencesMng conferenceMng;

	
	private Collection<Conference> currentItens = Collections.emptyList();
	private ConferenceFilter filter = new ConferenceFilter();
	
	private Integer currentPage = 0;
	private Integer pageSize = 10;



	@PostConstruct
	public void initialize() {
		this.filter.setFirtsResult(this.currentPage * this.pageSize);
		this.filter.setMaxResults(this.pageSize);
		
		this.currentItens = this.conferenceMng.search(this.filter);
	}

	public Collection<Conference> getConferences() {
		return this.currentItens;
	}

	public int getCurrentPageNumber() {
		return this.currentPage;
	}

	public int getTotalOfConferences() {
		return 500;
	}

	public void backward() {
		int firtsRecord = (this.currentPage - 1) * this.pageSize;
		this.filter.setFirtsResult(firtsRecord);
		this.currentItens = this.conferenceMng.search(this.filter);
		
		this.currentPage--;
	}

	public void forward() {
		int firtsRecord = (this.currentPage + 1) * this.pageSize;
		this.filter.setFirtsResult(firtsRecord);
		this.currentItens = this.conferenceMng.search(this.filter);
		
		this.currentPage++;
	}

}
