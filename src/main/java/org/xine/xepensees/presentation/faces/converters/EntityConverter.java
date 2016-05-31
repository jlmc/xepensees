package org.xine.xepensees.presentation.faces.converters;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.Id;

@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {

		if (isEmpty(value)) {
			return null;
		}

		Optional<UISelectItems> uiSelectItems = getUISelectItems(component);
		
		Collection<?> objects = uiSelectItems.
							map(uiComponent -> (Collection<?>)uiComponent.getValue()).
							orElse(Collections.emptyList());
		
		
		final Object foundEntity = 
				objects.stream()
				.filter(entity -> getAsString(context, uiSelectItems.get(), entity).equals(value))
				.findFirst()
				.orElse(null);

		return foundEntity;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return null;
		}
		
		final Field field = findEntityIdField(value);
		return getEntityIdValue(field, value);
	}

	private String getEntityIdValue(final Field idField, final Object value) {

		try {
			final Field field = value.getClass().getDeclaredField(idField.getName());
			field.setAccessible(true);

			return field.get(value).toString();

		} catch (IllegalArgumentException | 
				 IllegalAccessException |
				 NoSuchFieldException | 
				 SecurityException e) {
			throw new ConverterException("can´t getEntityIdValue from " + value);
		} 
	}

	private Field findEntityIdField(final Object value) {
		return Arrays.stream(value.getClass().getDeclaredFields())
				.filter((field) -> field.getAnnotation(Id.class) != null)
				.findFirst().get();
	}

	private boolean isEmpty(final String value) {
		return value == null || value.trim().isEmpty();
	}
	
	
	private Optional<UISelectItems> getUISelectItems(UIComponent component) {
		List<UIComponent> childrens = component.getChildren();
		
		return childrens.stream().
				filter(c -> UISelectItems.class.isAssignableFrom(c.getClass())).
				map(c -> (UISelectItems) c).
				findFirst();
	}

}