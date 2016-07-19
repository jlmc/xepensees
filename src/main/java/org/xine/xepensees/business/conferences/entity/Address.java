package org.xine.xepensees.business.conferences.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "country", length = 100, nullable = false)
	private String country;
	@Column(name = "city", length = 100, nullable = false)
	private String city;
	@Column(name = "street", length = 200, nullable = true)
	private String street;
	@Column(name = "number", length = 50, nullable = true)
	private String number;

	protected Address() {
	}

	protected void setNumber(final String number) {
		this.number = number;
	}

	protected void setStreet(final String street) {
		this.street = street;
	}

	protected void setCity(final String city) {
		this.city = city;
	}

	protected void setCountry(final String country) {
		this.country = country;
	}

	public String getCountry() {
		return this.country;
	}

	public String getCity() {
		return this.city;
	}

	public String getStreet() {
		return this.street;
	}

	public String getNumber() {
		return this.number;
	}

	public static Address of(final String country, final String city, final String street, final String number) {
		if (isNullOrEmpty(country)) {
			throw new IllegalArgumentException("the country should be not blank.");
		}

		if (isNullOrEmpty(city)) {
			throw new IllegalArgumentException("the city shoul be not blank.");
		}

		if (isNullOrEmpty(street)) {
			throw new IllegalArgumentException("the street should be not blank.");
		}
		
		if(isNullOrEmpty(number)) {
			throw new IllegalArgumentException("the number should be not blank.");
		}

		final Address address = new Address();
		address.country = toUppercaseFirstLetterAndLowercaseTheRest(country);
		address.city = toUppercaseFirstLetterAndLowercaseTheRest(city);
		address.street = toUppercaseFirstLetterAndLowercaseTheRest(street);
		address.number = number.trim().toUpperCase();
		return address;
	}

	private static String toUppercaseFirstLetterAndLowercaseTheRest(final String data) {
		final String firstLetter = data.substring(0, 1).toUpperCase();
		final String restLetters = data.substring(1).toLowerCase();
		return String.valueOf(firstLetter + restLetters).trim();
	}

	private static boolean isNullOrEmpty(final String country) {
		return country == null || country.trim().isEmpty();
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				String.valueOf(this.country).toLowerCase(), 
				String.valueOf(this.city).toLowerCase(),
				String.valueOf(this.street).toLowerCase(), 
				String.valueOf(this.number).toLowerCase());
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
		final Address other = (Address) obj;
		if (this.city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!this.city.equalsIgnoreCase(other.city)) {
			return false;
		}
		if (this.country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!this.country.equalsIgnoreCase(other.country)) {
			return false;
		}
		if (this.number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!this.number.equalsIgnoreCase(other.number)) {
			return false;
		}
		if (this.street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!this.street.equalsIgnoreCase(other.street)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Address [country=" + this.country + ", city=" + this.city + ", street=" + this.street + ", number=" + this.number + "]";
	}

}
