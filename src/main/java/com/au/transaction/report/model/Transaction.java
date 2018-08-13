package com.au.transaction.report.model;

import lombok.Data;

/**
 * Class which holds the required Transaction information
 * 
 * @author ChaitanyaPrabhu
 *
 */
@Data
public class Transaction {

	private Client client;
	
	private Product product;
	
	private Long quantityLong;
	
	private Long quantityShort;
	
	private Byte quantityLongSign;
	
	private Byte quantityShortSign;
}
