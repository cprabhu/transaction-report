package com.au.transaction.report.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.au.transaction.report.model.Client;
import com.au.transaction.report.model.Product;
import com.au.transaction.report.model.Transaction;

/**
 * Service class to extract the transactions from the parsed input list of
 * strings.
 * 
 * @author ChaitanyaPrabhu
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	@Override
	public List<Transaction> getTransactions(List<String> transactionList) {
		List<Transaction> resultList = new ArrayList<>();
		transactionList.forEach(transactionString -> {
			Transaction transaction = new Transaction();
			transaction.setClient(getClient(transactionString));
			transaction.setProduct(getProduct(transactionString));
			transaction.setQuantityLongSign(Byte.parseByte(
					!transactionString.substring(51, 52).trim().replaceAll("\\s", "").isEmpty() ? transactionString.substring(51, 52) : "0"));
			transaction.setQuantityLong(Long.parseLong(transactionString.substring(52, 62)));
			transaction.setQuantityShortSign(Byte.parseByte(
					!transactionString.substring(62, 63).trim().replaceAll("\\s", "").isEmpty() ? transactionString.substring(62, 63) : "0"));
			transaction.setQuantityShort(Long.parseLong(transactionString.substring(63, 73)));
			resultList.add(transaction);
		});
		return resultList;
	}
	
	private Client getClient(String transaction) {
		Client client = new Client();
		client.setClientType(transaction.substring(3, 7));
		client.setClientNumber(transaction.substring(7, 11));
		client.setAccountNumber(transaction.substring(11, 15));
		client.setSubAccountNumber(transaction.substring(15, 19));
		return client;
	}

	private Product getProduct(String transaction) {
		Product product = new Product();
		product.setProductGroupCode(transaction.substring(25, 27));
		product.setExchangeCode(transaction.substring(27, 31));
		product.setSymbol(transaction.substring(31, 37));
		product.setExpirationDate(transaction.substring(37, 45));
		return product;
	}

}
