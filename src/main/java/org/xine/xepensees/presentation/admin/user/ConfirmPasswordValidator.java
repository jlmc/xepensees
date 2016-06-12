package org.xine.xepensees.presentation.admin.user;

import java.lang.annotation.Annotation;
import java.util.ResourceBundle;

import javax.enterprise.util.AnnotationLiteral;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.xine.xepensees.presentation.faces.Beans;
import org.xine.xepensees.presentation.faces.messages.MessageBundle;

@FacesValidator("passwordValidator")
public class ConfirmPasswordValidator implements Validator{

	private static final String CONFIRMATION = "confirmation";
	
	private ResourceBundle resourceBundle;
	
	public ConfirmPasswordValidator() {
		@SuppressWarnings("serial")
		Annotation messageBundle = new AnnotationLiteral<MessageBundle>() {};
		this.resourceBundle = new Beans().getBean(ResourceBundle.class , messageBundle );
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String password = (String) value;
		//HtmlInputSecret confirmationInput = (HtmlInputSecret) component.getAttributes().get("confirmation");
        UIInput confirmationInput = (UIInput) component.getAttributes().get(CONFIRMATION);
		
        if (confirmationInput == null) {
			return;
		}
        
        String confirm = String.valueOf(confirmationInput.getSubmittedValue());

        if (!isPasswordValid(password, confirm)) { 
        	confirmationInput.setValid(false);
        	
        	throw new ValidatorException(
        			new FacesMessage(
        					FacesMessage.SEVERITY_ERROR
        					, getMessage()
        					, "")
        			);
        }
	}

	private String getMessage() {
		if (this.resourceBundle == null) {
			return "The password is not valid, acording the confiration input value";
		}
		
		return this.resourceBundle.getString("org.xine.xepensees.presentation.admin.user.ConfirmPasswordValidator");
	}

	private boolean isPasswordValid(String password, String confirm) {
		if (password == null || confirm == null) {
			return false;
		}
		
		if (!password.trim().equals(confirm.trim())) {
			return false;
		}
		
		return true;
	}
}
