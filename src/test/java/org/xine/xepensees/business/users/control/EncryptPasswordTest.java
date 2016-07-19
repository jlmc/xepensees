package org.xine.xepensees.business.users.control;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncryptPasswordTest {

	@Test
	public void shouldEncriptPassword() {
		final String disgestPassword = new EncryptPassword().digest("admin");
		assertEquals("jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=", disgestPassword);
	}

	@Test(expected = RuntimeException.class)
	public void shouldNotEncriptNullPassword() {
		new EncryptPassword().digest(null);
	}

}
