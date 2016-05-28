package org.xine.xepensees.business.conferences.entity;

import java.io.Serializable;

public class ConferenceFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer firtsResult;
	private Integer maxResults;
	private String name;
	private SortBy sortBy;
	
	public Integer getFirtsResult() {
		return this.firtsResult;
	}

	public void setFirtsResult(Integer firtsResult) {
		this.firtsResult = firtsResult;
	}

	public Integer getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortBy getSortBy() {
		return this.sortBy;
	}

	public void setSortBy(SortBy sortBy) {
		this.sortBy = sortBy;
	}

	public enum SortBy {
		NAME, DATE
	}
}
