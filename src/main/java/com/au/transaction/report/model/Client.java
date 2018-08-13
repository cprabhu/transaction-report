package com.au.transaction.report.model;

import lombok.Data;

/**
 * Class which holds client related information. It always forms a part of
 * <code>Transaction</code>
 * 
 * @author ChaitanyaPrabhu
 *
 */
@Data
public class Client {

	private String clientType;

	private String clientNumber;

	private String accountNumber;

	private String subAccountNumber;

	@Override
	public String toString() {
		return clientType.trim().replaceAll("\\s", "") + "-" + clientNumber.trim().replaceAll("\\s", "") + "-"
				+ accountNumber.trim().replaceAll("\\s", "") + "-" + subAccountNumber.trim().replaceAll("\\s", "");
	}
	

}
