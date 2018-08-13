package com.au.transaction.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransctionReportApplicationTests {

	@Test
	public void testReportGeneration() throws IOException {
		File outputFile = new File(getClass().getClassLoader().getResource("file:Output.csv").getPath());
		List<String> transactionList = Files.readAllLines(outputFile.toPath());
		Assert.assertTrue(transactionList.stream().anyMatch(t -> t.contains("1234")));
	}

}
