package org.xine.xepensees.presentation.demo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.conferences.boundary.ConferenceMng;
import org.xine.xepensees.business.conferences.entity.Conference;

@Named
@ViewScoped
public class DemoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ConferenceMng manager;

	private List<Conference> conferences; 
	private int pageNumber;
	private int pageSize;
	private int totalOfPages;


	@PostConstruct
	public void initialize() {
		this.pageSize = 10;
		search();
	}

	private void search() {
	//	SearchPage<Conference> result = this.manager.search(this.pageNumber, this.pageSize);
//		this.conferences = result.getCurrentItens();
//		this.pageNumber = result.getPageNumber();
//		this.totalOfPages = result.getTotalOfPages();
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public int getTotalOfPages() {
		return this.totalOfPages;
	}

	public void nextPage() {
		if (this.pageNumber + 1 < this.totalOfPages) {
			this.pageNumber++;
			search();
		}
	}

	public void previousPage() {
		if (this.pageNumber -1 >= 0 ) {
			this.pageNumber--;
			search();
		}
	}
	
	public List<Conference> getConferences() {
		return this.conferences;
	}
}
