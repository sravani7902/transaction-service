package com.acc.transactionservice.transaction_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acc.transactionservice.transaction_service.client.AccountClient;
import com.acc.transactionservice.transaction_service.model.AccountDTO;
import com.acc.transactionservice.transaction_service.model.Transaction;
import com.acc.transactionservice.transaction_service.repo.TransactionRepository;

public class TransactionServiceTest {

    @Mock
    private AccountClient accountClient;

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransferAmount_success() {
        AccountDTO sender = new AccountDTO(1001L, "Alice", "Savings", 1000.0, "Active");
        AccountDTO receiver = new AccountDTO(1002L, "Bob", "Savings", 5000.0, "Active");

        when(accountClient.getAccountByAccNum(1001L)).thenReturn(sender);
        when(accountClient.getAccountByAccNum(1002L)).thenReturn(receiver);

        ResponseEntity<String> response = service.transferAmount(1001L, 1002L, 200.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transaction successful", response.getBody());

        verify(accountClient).updateAccount(eq(1001L), any(AccountDTO.class));
        verify(accountClient).updateAccount(eq(1002L), any(AccountDTO.class));
        verify(repository).save(any(Transaction.class));
    }

    @Test
    void testTransferAmount_invalidSenderOrReceiver() {
        when(accountClient.getAccountByAccNum(1001L)).thenReturn(null);
        when(accountClient.getAccountByAccNum(1002L)).thenReturn(null);

        ResponseEntity<String> response = service.transferAmount(1001L, 1002L, 200.0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid account details", response.getBody());

        verify(repository, never()).save(any());
    }

    @Test
    void testTransferAmount_insufficientBalance() {
        AccountDTO sender = new AccountDTO(1001L, "Alice", "Savings", 100.0, "Active");
        AccountDTO receiver = new AccountDTO(1002L, "Bob", "Savings", 500.0, "Active");

        when(accountClient.getAccountByAccNum(1001L)).thenReturn(sender);
        when(accountClient.getAccountByAccNum(1002L)).thenReturn(receiver);

        ResponseEntity<String> response = service.transferAmount(1001L, 1002L, 200.0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient balance for this account", response.getBody());

        verify(repository, never()).save(any());
    }

    @Test
    void testGetTransactionHistory() {
        Transaction t1 = new Transaction(1001L, 1002L, 200.0);
        Transaction t2 = new Transaction(1002L, 1001L, 100.0);

        when(repository.findBySenderAccNumOrReceiverAccNum(1001L, 1001L))
            .thenReturn(Arrays.asList(t1, t2));

        List<Transaction> result = service.getTransactionHistory(1001L);

        assertEquals(2, result.size());
        assertEquals(1002L, result.get(0).getReceiverAccNum());
        assertEquals(1001L, result.get(1).getReceiverAccNum());
    }
}
