package com.au.transaction.report.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;

import com.au.transaction.report.model.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {
	
	@Mock
	private TransactionServiceImpl service;
	
	@Mock
	private Resource input;
	
	private List<Transaction> mockedListOfTransactions() {
		List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(new Transaction());
		transactionList.add(new Transaction());
		return transactionList;
	}
	
	@Test
	public void testGetTransactions_ValidInputFile() {
		List<String> inputList = parsedInput("Input-test.txt");
		List<Transaction> expectedTransactions = mockedListOfTransactions();
		Mockito.when(service.getTransactions(inputList)).thenReturn(expectedTransactions);
		
		List<Transaction> actualTransacations = service.getTransactions(inputList);
		
		assertThat(actualTransacations).isNotEmpty();
		assertThat(actualTransacations).isSameAs(expectedTransactions);
	}
	
	@Test
	public void testGetParsedListOfTransactions_ValidClient(){
		Mockito.when(service.getParsedListOfTransactions(input, "1234")).thenReturn(Arrays.asList("CL1234 000001", "CL1234 000002"));
		
		List<String> parsedList = service.getParsedListOfTransactions(input, "1234");
		
		assertThat(parsedList).isNotEmpty();
		assertThat(parsedList).hasSize(2);
	}
	
	@Test
	public void testGetParsedListOfTransactions_InvalidClient(){
		Mockito.when(service.getParsedListOfTransactions(input, "12345")).thenReturn(new ArrayList<>());
		
		assertThat(service.getParsedListOfTransactions(input, "12345")).isEmpty();
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
