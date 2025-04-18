package com.acc.transactionservice.transaction_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acc.transactionservice.transaction_service.model.Transaction;
import com.acc.transactionservice.transaction_service.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
    private TransactionService transactionService;
	
	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestParam Long senderAccNum,
			@RequestParam Long receiverAccNum,
			@RequestParam double amount){
		
		return transactionService.transferAmount(senderAccNum, receiverAccNum, amount);
		
	}

	@GetMapping("/history/{accNum}")
	public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long accNum){
		List<Transaction> transactions = transactionService.getTransactionHistory(accNum);
		
		if(transactions.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(transactions);
		
	}
}
