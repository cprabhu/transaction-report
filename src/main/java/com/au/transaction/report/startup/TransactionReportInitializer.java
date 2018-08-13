package com.au.transaction.report.startup;

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

/**
 * The class responsible for the parsing of input and 
 * generating of report(CSV) for all products per client. 
 * 
 * @author ChaitanyaPrabhu
 *
 */
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
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		/*
		 * Parse the input file into a list with also filtering the transactions only
		 * for the required customer - 1234 in this case.
		 */
		List<String> parsedList = transactionService.getParsedListOfTransactions(input, customerId);
		log.info("The parsed list of transactions for customer "+customerId+" has "+parsedList.size()+" records.");
		
		final List<Transaction> transactionList = transactionService.getTransactions(parsedList);
		final String clientInformation = transactionList.stream().findAny().get().getClient().toString();
		log.debug("List of transactions for customer "+customerId+" => "+transactionList);
		
		/*
		 * Need to group all the products to the sum total of transaction amount.
		 */
		Map<Product, LongSummaryStatistics> resultMap = transactionList.parallelStream().collect(Collectors.groupingByConcurrent(Transaction::getProduct, Collectors.summarizingLong(t -> {
			long quantityLong = t.getQuantityLongSign().equals(Byte.valueOf("1")) ? -t.getQuantityLong() : t.getQuantityLong();
			long quantityShort = t.getQuantityShortSign().equals(Byte.valueOf("1")) ? -t.getQuantityShort() : t.getQuantityShort();
			return quantityLong - quantityShort;
		})));
		log.info("Total number of products mapped to the total transaction => "+resultMap.size());
		
		log.info("The report for "+clientInformation+" will now be generated on "+outputFile+".");
		
		/* Send the product and client details for the report generation */
		PrintReport.printReport(resultMap, clientInformation, outputFile);

		/*
		 * If needed to terminate the application soon after generating the report, uncomment the below line.
		 */
		//System.exit(0);

	}

}
