package com.acc.transactionservice.transaction_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	
	private Long accnum;
	private String name;
	private String accType;
	private Double balance;
	private String status;
	
	public AccountDTO(Long accnum, String name, String accType, Double balance, String status) {
	    this.accnum = accnum;
	    this.name = name;
	    this.accType = accType;
	    this.balance = balance;
	    this.status = status;
	}
	
	public Long getAccnum() {
		return accnum;
	}
	public void setAccnum(Long accnum) {
		this.accnum = accnum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
