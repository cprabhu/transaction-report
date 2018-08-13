package com.au.transaction.report.helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LongSummaryStatistics;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import com.au.transaction.report.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PrintReport {

	public void printReport(Map<Product, LongSummaryStatistics> productSummaryMap,
			final String clientInformation, final String outputFile) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile));
			 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Client_Information",
						"Product_Information", "Total_Transaction_Amount"))) {

			productSummaryMap.forEach((product, statistics) -> {
				try {
					csvPrinter.printRecord(clientInformation, product.toString(), statistics.getSum());
				} catch (IOException e) {
					log.error("Writing product statistics to " + outputFile + " has resulted in error - "
							+ e.getMessage());
				}
			});
			csvPrinter.flush();
			log.info(outputFile + " is written successfully!");
		} catch (IOException e) {
			log.error("Writing to " + outputFile + " has resulted in error - " + e.getMessage());
		}
	}

}
