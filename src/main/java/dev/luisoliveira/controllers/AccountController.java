package dev.luisoliveira.controllers;

import dev.luisoliveira.dtos.AccountDto;
import dev.luisoliveira.dtos.CreateAccountDto;
import dev.luisoliveira.services.AccountService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.validation.Valid;

@Controller("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Post
    public HttpResponse<AccountDto> createNewAccount(@Body @Valid CreateAccountDto createAccountDto){
        return HttpResponse.created(accountService.createAccount(createAccountDto));
    }

    @Get("/{accountNumber}")
    public HttpResponse<AccountDto> getAccountByAccountNumber(String accountNumber){
        return HttpResponse.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

}
