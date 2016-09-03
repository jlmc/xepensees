package org.xine.xepensees.presentation.admin.conference;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.conferences.boundary.ConferencesMng;
import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.params.entity.QueryParameter;

@Named
@javax.faces.view.ViewScoped
public class SearchConferencesBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ConferencesMng conferenceMng;
	private Collection<Conference> currentItens = Collections.emptyList();
	private Integer currentPage = 0;
	private final Integer pageSize = 10;
	private String name;



	@PostConstruct
	public void initialize() {
		final QueryParameter query = QueryParameter.empty().page(this.currentPage, this.pageSize);
		this.currentItens = this.conferenceMng.search(query);
	}
	
	public void search() {
		final QueryParameter parameter = QueryParameter.with("name", this.name).page(0, this.pageSize);
		this.currentItens = this.conferenceMng.search(parameter);
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
		if (this.currentPage == 0) {
			return;
		}
		
		this.currentPage--;
		final QueryParameter queryParameter = QueryParameter.with("startwith", this.name).page(this.currentPage, this.pageSize);
		this.currentItens = this.conferenceMng.search(queryParameter);
	}

	public void forward() {
		this.currentPage++;
		final QueryParameter queryParameter = QueryParameter.with("startwith", this.name).page(this.currentPage, this.pageSize);
//		
//		int firtsRecord = (this.currentPage + 1) * this.pageSize;
//		this.filter.setFirtsResult(firtsRecord);
		this.currentItens = this.conferenceMng.search(queryParameter);
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
