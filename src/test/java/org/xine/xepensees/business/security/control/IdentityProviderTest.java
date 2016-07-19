package org.xine.xepensees.business.security.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.xine.xepensees.business.security.entity.Anonymous;
import org.xine.xepensees.business.security.entity.Identity;
import org.xine.xepensees.business.users.control.UsersRepository;
import org.xine.xepensees.business.users.entity.User;

public class IdentityProviderTest {

	private IdentityProvider sut;

	private Principal principalMock;
	private UsersRepository usersRepositoryMock;

	@Before
	public void init() {
		this.principalMock = mock(Principal.class);
		this.usersRepositoryMock = mock(UsersRepository.class);

		this.sut = new IdentityProvider();
		this.sut.principal = this.principalMock;
		this.sut.usersRepository = this.usersRepositoryMock;
	}

	@Test
	public void shouldFetchTheAnonymous() {
		when(this.principalMock.getName()).thenReturn("anonymous");
		when(this.usersRepositoryMock.find("anonymous")).thenReturn(null);

		final Identity identity = this.sut.fetch();

		assertNotNull(identity);
		assertEquals(Anonymous.instance(), identity);
	}

	@Test
	public void shouldFetchTheAnonymousWhenTheUserDontExistOnRepository() {
		when(this.principalMock.getName()).thenReturn("DalaiLama@domain.com");
		when(this.usersRepositoryMock.find("DalaiLama@domain.com")).thenReturn(null);

		final Identity identity = this.sut.fetch();

		assertNotNull(identity);
		assertEquals(Anonymous.instance(), identity);
	}

	@Test
	public void shouldFetchValidIdentityWhenTheUserExistOnRepository() {
		when(this.principalMock.getName()).thenReturn("DalaiLama@domain.com");
		when(this.usersRepositoryMock.find("DalaiLama@domain.com"))
				.thenReturn(User.of("DalaiLama@domain.com", "Dalai Lama", "secret"));

		final Identity identity = this.sut.fetch();

		verify(this.usersRepositoryMock, times(1)).find("DalaiLama@domain.com");

		assertNotNull(identity);
		assertEquals(User.of("DalaiLama@domain.com", "Dalai Lama", "secret"), identity.getUser());
	}

}
