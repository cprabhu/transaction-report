package com.au.transaction.report.startup;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.au.transaction.report.helper.PrintReport;
import com.au.transaction.report.model.Product;
import com.au.transaction.report.model.Transaction;
import com.au.transaction.report.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionReportInitializer implements ApplicationListener<ApplicationReadyEvent> {
	
	@Value("${app.transaction.output.file}")
	private String outputFile;

	@Value(value = "classpath:input/Input.txt")
	private Resource input;
	
	@Value("${app.transaction.customer.id}")
	private String customerId;
	
	@Autowired
	private TransactionService transactionService;
	
	public List<String> parseInput() {
		List<String> inputStrList = null;
		try {
			inputStrList = Files.readAllLines(input.getFile().toPath());
		} catch (IOException e) {
			log.error("Unable to read all of the input file due to "+e.getMessage());
		}
		log.info("On bootup, The input file is parsed successfully.");
		return inputStrList;
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		/*
		 * Parse the input file into a list with also filtering the transactions only
		 * for the required customer - 1234 in this case
		 */
		List<String> parsedList = parseInput().parallelStream()
				.filter(transactionString -> transactionString.substring(7, 11).equalsIgnoreCase(customerId))
				.collect(Collectors.toList());
		log.info("The parsed list of transactions for customer "+customerId+" has "+parsedList.size()+" records.");
		
		final List<Transaction> transactionList = transactionService.getTransactions(parsedList);
//		transactionList.forEach(System.out::println);
		final String clientInformation = transactionList.stream().findAny().get().getClient().toString();
		
		Map<Product, LongSummaryStatistics> resultMap = transactionList.parallelStream().collect(Collectors.groupingByConcurrent(Transaction::getProduct, Collectors.summarizingLong(t -> {
			long quantityLong = t.getQuantityLongSign().equals(Byte.valueOf("1")) ? -t.getQuantityLong() : t.getQuantityLong();
			long quantityShort = t.getQuantityShortSign().equals(Byte.valueOf("1")) ? -t.getQuantityShort() : t.getQuantityShort();
			return quantityLong - quantityShort;
		})));
//		System.out.println("TransactionReportInitializer.onApplicationEvent() "+resultMap);
		
		PrintReport.printReport(resultMap, clientInformation, outputFile);

		
		System.exit(0);
		
	}

}
