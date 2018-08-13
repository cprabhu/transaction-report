package com.au.transaction.report;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransctionReportApplicationTests {

	@Test(expected = FileAlreadyExistsException.class)
	public void testContextLoads() throws IOException {
		Path newFilePath = Paths.get("src/../output/Output.csv");
		List<String> transactionList = Files.readAllLines(Files.createFile(newFilePath));
		Assert.assertTrue(transactionList.stream().anyMatch(t -> t.contains("1234")));
	}

}
