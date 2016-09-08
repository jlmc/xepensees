package org.xine.xepensees.presentation.admin.user;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import org.xine.xepensees.business.user.entity.Permission;

@FacesConverter(value = "permissionConverter")
public class PermissionConverter extends EnumConverter {
	public PermissionConverter() {
		super(Permission.class);
	}
}
