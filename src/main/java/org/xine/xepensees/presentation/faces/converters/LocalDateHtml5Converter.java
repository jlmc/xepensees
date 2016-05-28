package org.xine.xepensees.presentation.faces.converters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = LocalDate.class)
public class LocalDateHtml5Converter implements Converter {

	private static DateTimeConverter originalConverter = new DateTimeConverter();

	static {
		originalConverter.setPattern("yyyy-MM-dd");
		// originalConverter.setPattern("MM/dd/yyyy");
	}

	@Override
	public Object getAsObject(
			final FacesContext context,
			final UIComponent component,
			final String value) {
		final Date date = (Date) originalConverter.getAsObject(context, component, value);

		if (date == null) {
			return null;
		}

		final Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}

	@Override
	public String getAsString(
			final FacesContext context,
			final UIComponent component,
			final Object value) {
		if (value == null) {
			return null;
		}

		final LocalDate localDate = (LocalDate) value;
		final Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		final Date date = Date.from(instant);

		return originalConverter.getAsString(context, component, date.getTime());
	}

}