package org.xine.xepensees.business.conferences.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private String country;
	private String city;
	private String street;
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
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
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
		address.country = toUppercaseFirstLetterAndLowercaseTheRest(country.trim());
		address.city = toUppercaseFirstLetterAndLowercaseTheRest(city.trim());
		address.street = toUppercaseFirstLetterAndLowercaseTheRest(street.trim());
		address.number = number.trim().toUpperCase();
		return address;
	}

	private static String toUppercaseFirstLetterAndLowercaseTheRest(final String data) {
		final String firstLetter = data.substring(0, 1).toUpperCase();
		final String restLetters = data.substring(1).toLowerCase();
		return firstLetter + restLetters;
	}

	private static boolean isNullOrEmpty(final String country) {
		return country == null || country.trim().isEmpty();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.country, this.city, this.street, this.number);
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
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equalsIgnoreCase(other.city)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equalsIgnoreCase(other.country)) {
			return false;
		}
		if (number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!number.equalsIgnoreCase(other.number)) {
			return false;
		}
		if (street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!street.equalsIgnoreCase(other.street)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Address [country=" + country + ", city=" + city + ", street=" + street + ", number=" + number + "]";
	}

}
