package dev.luisoliveira.services.useCases;

import dev.luisoliveira.dtos.AccountDto;

public interface GetAccountUseCase {

    AccountDto getAccountByAccountNumber(String accountNumber);

}
