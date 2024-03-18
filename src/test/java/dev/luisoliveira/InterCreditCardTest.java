package dev.luisoliveira;

import dev.luisoliveira.dtos.*;
import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.entities.PaymentTransactionEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.services.CreditCardBIllService;
import dev.luisoliveira.services.useCases.CreateCreditCardBillUseCase;
import dev.luisoliveira.services.useCases.CreatePaymentTransactionUseCase;
import dev.luisoliveira.services.useCases.GetCreditCardBillUseCase;
import dev.luisoliveira.services.useCasesImpl.CreateAccountUseCaseImpl;
import dev.luisoliveira.services.useCasesImpl.GetPaymentTransactionsUseCaseImpl;
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
    CreditCardBIllService creditCardBIllService;

    @Inject
    CreateAccountUseCaseImpl createAccountUseCase;

    @Inject
    CreatePaymentTransactionUseCase createPaymentTransactionUseCase;

    @Inject
    CreateCreditCardBillUseCase createCreditCardBillUseCase;

    @Inject
    GetCreditCardBillUseCase getCreditCardBillUseCase;

    @Inject
    GetPaymentTransactionsUseCaseImpl getPaymentTransactionsUseCase;

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
    void givenOpenBill_whenReceiveRequestToClose_thenCloseThatBill() throws ParseException {
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

        Optional<CreditCardBillEntity> julyBill = creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountDto.getAccountNumber(), Month.JULY, Year.of(2024));

        Assertions.assertTrue(julyBill.isPresent());
        Assertions.assertTrue(julyBill.get().getOpen());

        CloseCreditCardBillDto closeCreditCardBillDto = new CloseCreditCardBillDto();
        closeCreditCardBillDto.setCreditCardBillId(julyBill.get().getId());
        closeCreditCardBillDto.setAccountNumber(accountDto.getAccountNumber());

        creditCardBIllService.closeCreditCardBill(closeCreditCardBillDto);

        Assertions.assertFalse(julyBill.get().getOpen());
    }

    @Test
    void givenOpenBill_whenReceiveRequestToClose_thenCloseThatBillAndOpenTheNextIfExists() throws ParseException {
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
        Assertions.assertTrue(decemberBill.get().getOpen());

        Assertions.assertTrue(januaryBill.isPresent());
        Assertions.assertFalse(januaryBill.get().getOpen());

        CloseCreditCardBillDto closeCreditCardBillDto = new CloseCreditCardBillDto();
        closeCreditCardBillDto.setCreditCardBillId(decemberBill.get().getId());
        closeCreditCardBillDto.setAccountNumber(accountDto.getAccountNumber());

        creditCardBIllService.closeCreditCardBill(closeCreditCardBillDto);

        Assertions.assertTrue(decemberBill.isPresent());
        Assertions.assertFalse(decemberBill.get().getOpen());

        Assertions.assertTrue(januaryBill.isPresent());
        Assertions.assertTrue(januaryBill.get().getOpen());
    }

    @Test
    void givenThreePaymentTransactionsInAMonth_whenGetAListOfTransactionsForThatMonth_thenReturnThatThreePaymentTranscationsInAList() throws ParseException {
        CreateAccountDto newAccountDto = new CreateAccountDto();
        newAccountDto.setDocument("35376768830");
        newAccountDto.setName("Luis");
        AccountDto accountDto = createAccountUseCase.createAccount(newAccountDto);

        PaymentTransactionDto firstPaymentTransactionDto = new PaymentTransactionDto();
        firstPaymentTransactionDto.setTransactionDate("16/07/2024");
        firstPaymentTransactionDto.setAccountNumber(accountDto.getAccountNumber());
        firstPaymentTransactionDto.setNumberOfInstallments(1);
        firstPaymentTransactionDto.setValueOfInstallments(50.00);
        firstPaymentTransactionDto.setValue(50.00);
        firstPaymentTransactionDto.setBusinessEstablishment("Nome do estabelecimento 1");

        PaymentTransactionEntity firstPaymentTransaction = createPaymentTransactionUseCase.createNewPaymentTransaction(firstPaymentTransactionDto,
                createCreditCardBillUseCase, getCreditCardBillUseCase);

        PaymentTransactionDto secondPaymentTransactionDto = new PaymentTransactionDto();
        secondPaymentTransactionDto.setTransactionDate("17/07/2024");
        secondPaymentTransactionDto.setAccountNumber(accountDto.getAccountNumber());
        secondPaymentTransactionDto.setNumberOfInstallments(1);
        secondPaymentTransactionDto.setValueOfInstallments(60.00);
        secondPaymentTransactionDto.setValue(60.00);
        secondPaymentTransactionDto.setBusinessEstablishment("Nome do estabelecimento 2");

        PaymentTransactionEntity secondPaymentTransaction = createPaymentTransactionUseCase.createNewPaymentTransaction(firstPaymentTransactionDto,
                createCreditCardBillUseCase, getCreditCardBillUseCase);


        PaymentTransactionDto thirdPaymentTransactionDto = new PaymentTransactionDto();
        thirdPaymentTransactionDto.setTransactionDate("18/07/2024");
        thirdPaymentTransactionDto.setAccountNumber(accountDto.getAccountNumber());
        thirdPaymentTransactionDto.setNumberOfInstallments(1);
        thirdPaymentTransactionDto.setValueOfInstallments(70.00);
        thirdPaymentTransactionDto.setValue(70.00);
        thirdPaymentTransactionDto.setBusinessEstablishment("Nome do estabelecimento 3");

        PaymentTransactionEntity thirdPaymentTransaction = createPaymentTransactionUseCase.createNewPaymentTransaction(firstPaymentTransactionDto,
                createCreditCardBillUseCase, getCreditCardBillUseCase);

        List<ListPaymentTransactionDto> paymentTransactionDtos = getPaymentTransactionsUseCase.getPaymentTransactionDtoList(accountDto.getAccountNumber()
                , Month.JULY, Year.of(2024));

        Assertions.assertTrue(paymentTransactionDtos.size() == 3);
    }


}
