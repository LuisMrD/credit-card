package dev.luisoliveira.services;

import dev.luisoliveira.dtos.AccountDto;
import dev.luisoliveira.dtos.CreateAccountDto;
import dev.luisoliveira.services.useCases.CreateAccountUseCase;
import dev.luisoliveira.services.useCasesImpl.CreateAccountUseCaseImpl;
import dev.luisoliveira.services.useCasesImpl.GetAccountUseCaseImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountService {


    CreateAccountUseCaseImpl createAccountUseCase;
    GetAccountUseCaseImpl getAccountUseCase;

    public AccountService(CreateAccountUseCaseImpl createAccountUseCase, GetAccountUseCaseImpl getAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountUseCase = getAccountUseCase;
    }

    public AccountDto createAccount(CreateAccountDto createAccountDto){
        return createAccountUseCase.createAccount(createAccountDto);
    }

    public AccountDto getAccountByAccountNumber(String accountNumber){
        return getAccountUseCase.getAccountByAccountNumber(accountNumber);
    }
}
