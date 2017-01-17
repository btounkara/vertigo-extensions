package io.vertigo.x.workflow;

import javax.inject.Inject;

import org.junit.Assert;

import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;

public class TestHelper {

	@Inject
	private VTransactionManager transactionManager;

	VTransactionWritable transaction;

	protected void doSetUp() throws Exception {
		Assert.assertFalse("the previous test hasn't correctly close its transaction.",
				transactionManager.hasCurrentTransaction());
		// manage transactions
		transaction = transactionManager.createCurrentTransaction();
	}

	protected void doTearDown() throws Exception {
		Assert.assertTrue("All tests must rollback a transaction.", transactionManager.hasCurrentTransaction());
		// close transaction
		transaction.rollback();
	}

}
