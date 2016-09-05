package org.xine.xepensees.business.params.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryParameter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> parameters = null;
	private Integer page = 0;
	private Integer pageLength = Integer.MAX_VALUE;
	
	private QueryParameter(String name, Object value) {
		this();
        this.parameters.put(name, value);
	}
	
	private QueryParameter(){
		this.parameters = new HashMap<>();
	}
	
	public static QueryParameter empty() {
		return new QueryParameter();
	}
	
	public static QueryParameter with(String name, Object value) {
		return new QueryParameter(name, value);
	}
	
	public QueryParameter and(String name, Object value){
        this.parameters.put(name, value);
        return this;
    }

	public QueryParameter page(int pageNumber, int pageLength){
		if (pageNumber < 0) {
			throw new IllegalArgumentException("the page number should be greater or equals than zero.");
		}
		if (pageLength < 0) {
			throw new IllegalArgumentException("the page length should be greater or equals than zero.");
		}
		
		this.page = pageNumber;
		this.pageLength = pageLength;
		
		return this;
	}
	
	public Object get(String key) {
		return this.parameters.get(key);
	}

	public boolean contains(String key) {
		return this.parameters.containsKey(key);
	}

	public boolean containsNotNullValue(String key) {
		return contains(key) && get(key) != null;
	}

	public Map<String, Object> parameters() {
		return Collections.unmodifiableMap(this.parameters);
	}
	
	public int getFirtsRecord() {
		return this.page * this.pageLength;
	}
	
	public int getPageLength() {
		return this.pageLength;
	}

	public Integer getPage() {
		return this.page;
	}
}
