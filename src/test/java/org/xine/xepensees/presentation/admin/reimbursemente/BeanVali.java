package org.xine.xepensees.presentation.admin.reimbursemente;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Size;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BeanVali {
	@Size(min = 1)
	List<String> people = new ArrayList<>();

	@Test
	public void name() {

		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		final Set<ConstraintViolation<List<String>>> validation = validator.validate(people);
	}

}
