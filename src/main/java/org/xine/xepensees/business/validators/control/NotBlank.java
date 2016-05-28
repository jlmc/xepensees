package org.xine.xepensees.business.validators.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Validate that the annotated string is not {@code null} or empty. The
 * difference to {@code NotEmpty} is that trailing whitespaces are getting
 * ignored.
 * 
 * @author Joao Costa
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "((?=\\s*\\S).*$)")
@NotNull
@ReportAsSingleViolation
public @interface NotBlank {
	// "^(?=\\s*\\S).*$"

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "{org.xine.xepensees.business.validators.control.NotBlank.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}