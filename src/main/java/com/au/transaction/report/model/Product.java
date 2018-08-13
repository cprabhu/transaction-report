package com.au.transaction.report.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;

/**
 * Class which holds all Product related information. It always forms a part of
 * <code>Transaction</code>
 * 
 * @author ChaitanyaPrabhu
 *
 */
@Data
public class Product {

	private String productGroupCode;

	private String exchangeCode;

	private String symbol;

	private String expirationDate;

	@Override
	public String toString() {
		return exchangeCode.trim().replaceAll("\\s", "") + "-" + productGroupCode.trim().replaceAll("\\s", "") + "-"
				+ symbol.trim().replaceAll("\\s", "") + "-"
				+ LocalDate.from(DateTimeFormatter.ofPattern("yyyyMMdd").parse(expirationDate));
	}

}
