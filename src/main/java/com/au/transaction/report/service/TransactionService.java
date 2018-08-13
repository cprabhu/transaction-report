package com.au.transaction.report.service;

import java.util.List;

import com.au.transaction.report.model.Transaction;

/**
 * Interface to define the service method contract
 * @author ChaitanyaPrabhu
 *
 */
public interface TransactionService {
	
	public List<Transaction> getTransactions(List<String> transactionList);

}
