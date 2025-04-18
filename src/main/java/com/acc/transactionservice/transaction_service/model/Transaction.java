package com.acc.transactionservice.transaction_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Transaction {
	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private Long senderAccNum;
	 private Long receiverAccNum;
	 private double amount;
	 @Column(columnDefinition = "DATETIME")
	 private LocalDateTime timestamp;

	 public Transaction() {
	    }
	 
	 public Transaction(Long senderAccNum, Long receiverAccNum, double amount) {
	        this.senderAccNum = senderAccNum;
	        this.receiverAccNum = receiverAccNum;
	        this.amount = amount;
	        this.timestamp = LocalDateTime.now();
	    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSenderAccNum() {
		return senderAccNum;
	}

	public void setSenderAccNum(Long senderAccNum) {
		this.senderAccNum = senderAccNum;
	}

	public Long getReceiverAccNum() {
		return receiverAccNum;
	}

	public void setReceiverAccNum(Long receiverAccNum) {
		this.receiverAccNum = receiverAccNum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
