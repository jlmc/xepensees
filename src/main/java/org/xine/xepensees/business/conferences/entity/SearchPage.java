package org.xine.xepensees.business.conferences.entity;

import java.io.Serializable;
import java.util.List;

public class SearchPage<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int totalOfRecords;
	private int pageSize;
	private int pageNumber;
	private List<T> currentItens;
	
	SearchPage() {}

	public SearchPage(int totalOfRecords, int pageSize, int pageNumber, List<T> currentItens) {
		this.totalOfRecords = totalOfRecords;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.currentItens = currentItens;
	}

	public int getTotalOfRecord() {
		return this.totalOfRecords;
	}
	
	public int getPageSize() {
		return this.pageSize;
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public List<T> getCurrentItens() {
		return this.currentItens;
	}
	
	public int getTotalOfPages() {
		return this.totalOfRecords % this.pageSize > 0 ?  this.totalOfRecords / this.pageSize : this.totalOfRecords / this.pageSize + 1;
	}
	

}
