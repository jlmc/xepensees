package org.xine.xepensees.business.user.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.logging.Logger;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.xine.xepensees.business.user.boundary.UsersMng;
import org.xine.xepensees.business.user.control.EncryptPassword;
import org.xine.xepensees.business.user.control.UsersRepository;
import org.xine.xepensees.business.user.entity.User;

public class UsersManagerTest {
	
	Logger loggerMock;
	UsersRepository usersRepositoryMock;
	Validator validatorMock;
	
	UsersMng usersManager;

	@Before
	public void initialize() {
		this.usersRepositoryMock = mock(UsersRepository.class);
		this.loggerMock = mock(Logger.class);
		this.validatorMock = mock(Validator.class);

		this.usersManager = new UsersMng();
		this.usersManager.logger = this.loggerMock;
		this.usersManager.encryptPassword = new EncryptPassword();
		this.usersManager.validator = this.validatorMock;
		this.usersManager.usersRepository = this.usersRepositoryMock;
	}
	
	@Test
	public void shouldRegisterNewUser() {
		User user = User.of("jamesgosling@javaee.com", "james gosling", "james");
		
		when(this.usersRepositoryMock.save(user)).then(invocation -> {
			User t = (User) invocation.getArguments()[0];
			User persistedUser = User.of(t.getEmail(), t.getName(), t.getPassword());
			return persistedUser;
		});
		when(this.usersRepositoryMock.find("jamesgosling@javaee.com")).thenReturn(null);
		
		
		User registedUser = this.usersManager.register(user);
		
		InOrder inOrder = inOrder(this.usersRepositoryMock);
		
		assertNotNull(registedUser);
		assertEquals(user, registedUser);
		inOrder.verify(this.usersRepositoryMock).find("jamesgosling@javaee.com");
		inOrder.verify(this.usersRepositoryMock).save(user);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotRegisteUserWithEmailAlreadyInUse() {
		User user = User.of("jamesgosling@javaee.com", "james gosling", "james");
		
		final User alreadyInUse = User.of("jamesgosling@javaee.com", "james gosling Already In Use", "james");
		
		when(this.usersRepositoryMock.find("jamesgosling@javaee.com")).thenReturn(alreadyInUse);
		
		try {
			this.usersManager.register(user);
		} catch (Exception e) {
			verify(this.usersRepositoryMock, never()).save(user);
			throw e;
		}
	}
}
