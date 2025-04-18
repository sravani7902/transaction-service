package com.acc.transactionservice.transaction_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acc.transactionservice.transaction_service.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	List<Transaction> findBySenderAccNumOrReceiverAccNum(Long senderAccNum, Long receiverAccNum);
}
