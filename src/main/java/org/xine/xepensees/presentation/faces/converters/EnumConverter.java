package org.xine.xepensees.presentation.faces.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EnumConverter implements Converter {

	@Override
	public Object getAsObject(
				final FacesContext context,
				final UIComponent component, 
				final String value) {
		
		final Class enumType = component.getValueExpression("value").
								   getType(context.getELContext());
		
		return Enum.valueOf(enumType, value);
	}

	@Override
	public String getAsString(
			final FacesContext context,
			final UIComponent component,
			final Object value) {
		if (value == null) {
			return null;
		}

		final Enum type = (Enum) value;
		return type.toString();
	}

}
