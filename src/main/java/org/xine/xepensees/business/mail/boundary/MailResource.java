package org.xine.xepensees.business.mail.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("mails")
public class MailResource {

	@Inject
	MailSender mailer;

	@GET
	public void send() {
		mailer.send("zzzzz@gmail.com", "xxxx@gmail.com", "testing jboss email configuration",
				"simple example");
	}

}
