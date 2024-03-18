package dev.luisoliveira;

import dev.luisoliveira.dtos.AccountDto;
import dev.luisoliveira.dtos.CreateAccountDto;
import dev.luisoliveira.dtos.PaymentTransactionDto;
import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.entities.PaymentTransactionEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.services.useCases.CreateCreditCardBillUseCase;
import dev.luisoliveira.services.useCases.CreatePaymentTransactionUseCase;
import dev.luisoliveira.services.useCases.GetCreditCardBillUseCase;
import dev.luisoliveira.services.useCasesImpl.CreateAccountUseCaseImpl;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.text.ParseException;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@MicronautTest
class InterCreditCardTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    CreateAccountUseCaseImpl createAccountUseCase;

    @Inject
    CreatePaymentTransactionUseCase createPaymentTransactionUseCase;

    @Inject
    CreateCreditCardBillUseCase createCreditCardBillUseCase;

    @Inject
    GetCreditCardBillUseCase getCreditCardBillUseCase;

    @Inject
    CreditCardBillRepository creditCardBillRepository;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void givenCreateAccountDto_WhenTryCreateANewAccount_ThenReturnANewAccountNumber(){
        CreateAccountDto newAccountDto = new CreateAccountDto();
        newAccountDto.setDocument("35376768830");
        newAccountDto.setName("Luis");

        AccountDto accountDto = createAccountUseCase.createAccount(newAccountDto);
        Assertions.assertFalse(accountDto.getAccountNumber().isEmpty());
        Assertions.assertTrue(accountDto.getAccountNumber().length() == 16);
    }

    @Test
    void givenPaymentTransaction_whenGetNewPaymentTransaction_thenCreateNewCreditCardBill() throws ParseException {
        CreateAccountDto newAccountDto = new CreateAccountDto();
        newAccountDto.setDocument("35376768830");
        newAccountDto.setName("Luis");
        AccountDto accountDto = createAccountUseCase.createAccount(newAccountDto);

        PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
        paymentTransactionDto.setTransactionDate("16/07/2024");
        paymentTransactionDto.setAccountNumber(accountDto.getAccountNumber());
        paymentTransactionDto.setNumberOfInstallments(1);
        paymentTransactionDto.setValueOfInstallments(100.00);
        paymentTransactionDto.setValue(100.00);
        paymentTransactionDto.setBusinessEstablishment("Nome do estabelecimento");

        PaymentTransactionEntity paymentTransaction = createPaymentTransactionUseCase.createNewPaymentTransaction(paymentTransactionDto,
                createCreditCardBillUseCase, getCreditCardBillUseCase);

        Assertions.assertTrue(paymentTransaction.getCreditCardBillId() > 0);
    }

    @Test
    void givenPaymentTransactionWithTwoInstallments_whenGetNewPaymentTransaction_thenCreateNewTwoCreditCardBills() throws ParseException {
        CreateAccountDto newAccountDto = new CreateAccountDto();
        newAccountDto.setDocument("35376768830");
        newAccountDto.setName("Luis");
        AccountDto accountDto = createAccountUseCase.createAccount(newAccountDto);

        PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
        paymentTransactionDto.setTransactionDate("16/07/2024");
        paymentTransactionDto.setAccountNumber(accountDto.getAccountNumber());
        paymentTransactionDto.setNumberOfInstallments(2);
        paymentTransactionDto.setValueOfInstallments(50.00);
        paymentTransactionDto.setValue(100.00);
        paymentTransactionDto.setBusinessEstablishment("Nome do estabelecimento");

        PaymentTransactionEntity paymentTransaction = createPaymentTransactionUseCase.createNewPaymentTransaction(paymentTransactionDto,
                createCreditCardBillUseCase, getCreditCardBillUseCase);

        List<CreditCardBillEntity> creditCardBillEntityList = creditCardBillRepository.findAll();

        Assertions.assertTrue(creditCardBillEntityList.size() == 2);
    }

    @Test
    void givenPaymentTransactionWithThreeInstallments_whenGetNewPaymentTransaction_thenCreateNewCardBillForCurrentMonthAndTwoCreditCardBillsForTheNextTwoMonths() throws ParseException {
        CreateAccountDto newAccountDto = new CreateAccountDto();
        newAccountDto.setDocument("35376768830");
        newAccountDto.setName("Luis");
        AccountDto accountDto = createAccountUseCase.createAccount(newAccountDto);

        PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
        paymentTransactionDto.setTransactionDate("16/07/2024");
        paymentTransactionDto.setAccountNumber(accountDto.getAccountNumber());
        paymentTransactionDto.setNumberOfInstallments(3);
        paymentTransactionDto.setValueOfInstallments(33.33);
        paymentTransactionDto.setValue(100.00);
        paymentTransactionDto.setBusinessEstablishment("Nome do estabelecimento");

        PaymentTransactionEntity paymentTransaction = createPaymentTransactionUseCase.createNewPaymentTransaction(paymentTransactionDto,
                createCreditCardBillUseCase, getCreditCardBillUseCase);

        Optional<CreditCardBillEntity> julyBill = creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountDto.getAccountNumber(), Month.JULY, Year.of(2024));
        Optional<CreditCardBillEntity> augustBill = creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountDto.getAccountNumber(), Month.AUGUST, Year.of(2024));
        Optional<CreditCardBillEntity> septemberBill = creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountDto.getAccountNumber(), Month.SEPTEMBER, Year.of(2024));

        Assertions.assertTrue(julyBill.isPresent());
        Assertions.assertTrue(augustBill.isPresent());
        Assertions.assertTrue(septemberBill.isPresent());
    }

    @Test
    void givenPaymentTransactionWithTwoInstallmentsOverTheCurrentYear_whenGetNewPaymentTransaction_thenCreateNewCardBillForCurrentYearAndANewCreditCardBillsForTheNextYear() throws ParseException {
        CreateAccountDto newAccountDto = new CreateAccountDto();
        newAccountDto.setDocument("35376768830");
        newAccountDto.setName("Luis");
        AccountDto accountDto = createAccountUseCase.createAccount(newAccountDto);

        PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
        paymentTransactionDto.setTransactionDate("16/12/2024");
        paymentTransactionDto.setAccountNumber(accountDto.getAccountNumber());
        paymentTransactionDto.setNumberOfInstallments(2);
        paymentTransactionDto.setValueOfInstallments(50.00);
        paymentTransactionDto.setValue(100.00);
        paymentTransactionDto.setBusinessEstablishment("Nome do estabelecimento");

        PaymentTransactionEntity paymentTransaction = createPaymentTransactionUseCase.createNewPaymentTransaction(paymentTransactionDto,
                createCreditCardBillUseCase, getCreditCardBillUseCase);

        Optional<CreditCardBillEntity> decemberBill = creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountDto.getAccountNumber(), Month.DECEMBER, Year.of(2024));
        Optional<CreditCardBillEntity> januaryBill = creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountDto.getAccountNumber(), Month.JANUARY, Year.of(2025));

        Assertions.assertTrue(decemberBill.isPresent());
        Assertions.assertTrue(januaryBill.isPresent());
    }

    @Test
    void givenOpenBill_whenReceiveRequestToClose_thenCloseThatBill(){

    }

    @Test
    void givenOpenBill_whenReceiveRequestToClose_thenCloseThatBillAndOpenTheNextIfExists(){

    }

    @Test
    void given_when_then(){

    }


}
