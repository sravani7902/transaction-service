package com.acc.transactionservice.transaction_service.controller;

import com.acc.transactionservice.transaction_service.model.Transaction;
import com.acc.transactionservice.transaction_service.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController controller;

    @Test
    void testTransfer() {
        when(transactionService.transferAmount(1001L, 1002L, 500.0))
            .thenReturn(ResponseEntity.ok("Transaction successful"));

        ResponseEntity<String> response = controller.transfer(1001L, 1002L, 500.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transaction successful", response.getBody());
        verify(transactionService, times(1)).transferAmount(1001L, 1002L, 500.0);
    }

    @Test
    void testGetHistory_withTransactions() {
        List<Transaction> txList = Arrays.asList(new Transaction(1001L, 1002L, 500.0));
        when(transactionService.getTransactionHistory(1001L)).thenReturn(txList);

        ResponseEntity<List<Transaction>> response = controller.getHistory(1001L);

        assertEquals(HttpStatus.OK, response.getStatusCode()); // ✅ Fixed
        assertEquals(1, response.getBody().size());
        verify(transactionService).getTransactionHistory(1001L);
    }

    @Test
    void testGetHistory_empty() {
        when(transactionService.getTransactionHistory(1001L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Transaction>> response = controller.getHistory(1001L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // ✅ Fixed
        assertNull(response.getBody());
        verify(transactionService).getTransactionHistory(1001L);
    }

}
