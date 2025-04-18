package com.acc.transactionservice.transaction_service.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.acc.transactionservice.transaction_service.client.AccountClient;
import com.acc.transactionservice.transaction_service.model.AccountDTO;
import com.acc.transactionservice.transaction_service.model.Transaction;
import com.acc.transactionservice.transaction_service.repo.TransactionRepository;


@Service
public class TransactionService {

	private  AccountClient accountClient;
    private  TransactionRepository repository;
    
    public TransactionService(AccountClient accountClient, TransactionRepository repository) {
    	this.accountClient = accountClient;
    	this.repository =  repository;
    }
        
    public ResponseEntity<String> transferAmount(Long senderAccNum, Long receiverAccNum, double amount){
    	
    	 AccountDTO sender = accountClient.getAccountByAccNum(senderAccNum);
         AccountDTO receiver = accountClient.getAccountByAccNum(receiverAccNum);
    	
    	if(sender == null || receiver == null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account details");
    	}
    	
    	if(sender.getBalance() < amount) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance for this account");
    	}
    	
    	sender.setBalance(sender.getBalance() - amount);
    	receiver.setBalance(receiver.getBalance() + amount);
    	
    	accountClient.updateAccount(senderAccNum, sender);
    	accountClient.updateAccount(receiverAccNum, receiver);
    	
    	Transaction tx = new Transaction(senderAccNum, receiverAccNum, amount);
    	repository.save(tx);
    	
    	return ResponseEntity.ok("Transaction successful");
    	
  
    }
    
    public List<Transaction> getTransactionHistory(Long accNum){
    	return repository.findBySenderAccNumOrReceiverAccNum(accNum, accNum);
    }

}
