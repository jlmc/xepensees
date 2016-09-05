package org.xine.xepensees.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaginatedListWrapper<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer currentPage;
	private Integer pageSize;
	private Integer totalResults;

	private String sortFields;
	private String sortDirections;

	@XmlElement
	private List<T> list = new ArrayList<T>();

	public Integer getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalResults() {
		return this.totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public String getSortFields() {
		return this.sortFields;
	}

	public void setSortFields(String sortFields) {
		this.sortFields = sortFields;
	}

	public String getSortDirections() {
		return this.sortDirections;
	}

	public void setSortDirections(String sortDirections) {
		this.sortDirections = sortDirections;
	}

	public List<T> getList() {
		return this.list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
