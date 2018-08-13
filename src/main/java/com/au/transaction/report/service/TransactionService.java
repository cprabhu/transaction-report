package com.au.transaction.report.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.au.transaction.report.model.Transaction;

/**
 * Interface to define the service method contract
 * @author ChaitanyaPrabhu
 *
 */
public interface TransactionService {
	
	public List<String> getParsedListOfTransactions(Resource input, String customerId);
	
	public List<Transaction> getTransactions(List<String> transactionList);

}
