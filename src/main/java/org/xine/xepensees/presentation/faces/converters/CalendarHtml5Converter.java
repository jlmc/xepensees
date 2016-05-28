package org.xine.xepensees.presentation.faces.converters;

import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Calendar.class)
public class CalendarHtml5Converter implements Converter {

	private static DateTimeConverter originalConverter = new DateTimeConverter();

	static {
		originalConverter.setPattern("yyyy-MM-dd");
	}

	@Override
	public Object getAsObject(final FacesContext context,
			final UIComponent component,
			final String value) {

		final Date date = (Date) originalConverter.getAsObject(context, component, value);

		if (date == null) {
			return null;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	@Override
	public String getAsString(
			final FacesContext context,
			final UIComponent component,
			final Object value) {
		if (value == null) {
			return null;
		}

		final Calendar cal = (Calendar) value;

		return originalConverter.getAsString(context, component, cal.getTime());
	}

}

