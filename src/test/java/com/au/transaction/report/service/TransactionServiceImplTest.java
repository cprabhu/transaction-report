package com.au.transaction.report.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.au.transaction.report.model.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {
	
	@Mock
	private TransactionServiceImpl service;
	
	@Test
	public void testGetTransactions() {
		List<String> inputList = parsedInput("Input-test.txt");
		List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(new Transaction());
		transactionList.add(new Transaction());
		Mockito.when(service.getTransactions(inputList)).thenReturn(transactionList);
		List<Transaction> transacations = service.getTransactions(inputList);
		System.out.println("TransactionServiceImplTest.testGetTransactions() "+transacations);
		assertThat(transacations).isNotEmpty();
		assertThat(transacations).isSameAs(transactionList);
	}
	
	private List<String> parsedInput(String fileName) {
		List<String> inputStrList = null;
		try {
			inputStrList = Files.readAllLines(new File(
					getClass().getClassLoader().getResource("testfiles/"+fileName).getPath())
							.toPath());
		} catch (IOException e) {
			// Unable to parse input file
		}
		return inputStrList;
	}

}
