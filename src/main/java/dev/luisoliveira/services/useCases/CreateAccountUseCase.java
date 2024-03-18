package dev.luisoliveira.services.useCases;

import dev.luisoliveira.dtos.AccountDto;
import dev.luisoliveira.dtos.CreateAccountDto;

import javax.inject.Singleton;

public interface CreateAccountUseCase {

    AccountDto createAccount(CreateAccountDto createAccountDto);

}
