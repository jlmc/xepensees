package org.xine.xepensees.business.conference.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.xine.xepensees.business.constraints.control.NotBlank;
import org.xine.xepensees.business.persistence.control.LocalDateConverter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "t_conference")
public class Conference implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@NotNull
	@NotBlank
	@Size(max = 100)
	private String name;

	@NotNull
	@Convert(converter = LocalDateConverter.class)
	private LocalDate date;

	@NotNull
	@Size(max = 100)
	@Column(name = "country", length = 100)
	private String country;

	@NotNull
	@Size(max = 100)
	@Column(name = "city", length = 100)
	private String city;
	
	protected Conference() {}

	public Long getId() {
		return id;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	protected void setVersion(final int version) {
		this.version = version;
	}

	protected int getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Conference other = (Conference) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Conference [id=" + id + ", name=" + name + "]";
	}

	public static Conference empty() {
		return new Conference();
	}

	public static class Builder {
		private final Conference conference = new Conference();

		private Builder() {
		}

		public static Builder builder() {
			return new Builder();
		}

		public Conference build() {
			return conference;
		}

		public Builder id(Long id) {
			conference.id = id;
			return this;
		}

		public Builder name(String name) {
			conference.name = name;
			return this;
		}

		public Builder city(String city) {
			conference.city = city;
			return this;
		}
	}

}
