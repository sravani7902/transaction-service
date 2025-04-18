package com.acc.transactionservice.transaction_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.acc.transactionservice.transaction_service.model.AccountDTO;

@Component
public class AccountClient {
	private final WebClient webclient;
	
	public AccountClient(WebClient.Builder builder) {
		this.webclient= builder.baseUrl("http://localhost:8081/accounts").build();
	}
	
	public AccountDTO getAccountByAccNum(Long accNum) {
		return webclient.get()
				.uri("/{accNum}",accNum)
				.retrieve()
				.bodyToMono(AccountDTO.class)
				.block();
	}

	public void updateAccount(Long accNum, AccountDTO account) {
        webclient.put()
                .uri("/{accNum}", accNum)
                .bodyValue(account)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
