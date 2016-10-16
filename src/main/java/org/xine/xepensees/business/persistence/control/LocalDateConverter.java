package org.xine.xepensees.business.persistence.control;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(final LocalDate localDate) {
		if (localDate == null) {
			return null;
		}

		final Instant instant = localDate.atStartOfDay().
										atZone(ZoneId.systemDefault()).
										toInstant();
		return Date.from(instant);
	}

	@Override
	public LocalDate convertToEntityAttribute(final Date date) {
		if (date == null) {
			return null;
		}

		final Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).
							 toLocalDate();
	}

}
