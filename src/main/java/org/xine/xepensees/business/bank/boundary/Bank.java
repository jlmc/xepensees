package org.xine.xepensees.business.bank.boundary;

import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.xine.xepensees.business.reimbursement.entity.Reimbursement;

@Singleton
public class Bank {

	@Inject
	protected Logger logger;
	

	@Asynchronous
	public void reimbursementToBePaid(@Observes Reimbursement event) {
		waitAbit();
		logger.info(" ");
		logger.info("-- BankingService -- ");
		logger.info("-- " + event);
		logger.info("-------------------- ");

	}
	
	/*
	public void reimbursementToBePaidAsyncs(@ObservesAsync Reimbursement event) {
		waitAbit();
		logger.info(" ");
		logger.info("-- BankingService -- ");
		logger.info("-- " + event);
		logger.info("-------------------- ");
	}
	*/

	private void waitAbit() {
		try {
			Thread.sleep(5000);
		} catch (final InterruptedException e) {
		}
	}

}
