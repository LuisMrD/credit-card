package dev.luisoliveira.dtos;

import dev.luisoliveira.entities.PaymentTransactionEntity;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class ListPaymentTransactionDto {

    private Long id;

    private String businessEstablishment;

    private Double value;

    private Double valueOfInstallments;

    private Integer numberOfInstallments;

    private Integer numberOfCurrentInstallment;

    private String accountNumber;

    private Long creditCardBillId;

    private Date transactionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessEstablishment() {
        return businessEstablishment;
    }

    public void setBusinessEstablishment(String businessEstablishment) {
        this.businessEstablishment = businessEstablishment;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValueOfInstallments() {
        return valueOfInstallments;
    }

    public void setValueOfInstallments(Double valueOfInstallments) {
        this.valueOfInstallments = valueOfInstallments;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public Integer getNumberOfCurrentInstallment() {
        return numberOfCurrentInstallment;
    }

    public void setNumberOfCurrentInstallment(Integer numberOfCurrentInstallment) {
        this.numberOfCurrentInstallment = numberOfCurrentInstallment;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getCreditCardBillId() {
        return creditCardBillId;
    }

    public void setCreditCardBillId(Long creditCardBillId) {
        this.creditCardBillId = creditCardBillId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public static ListPaymentTransactionDto of(PaymentTransactionEntity paymentTransactionEntity){
        ListPaymentTransactionDto getPaymentTransactionDto = new ListPaymentTransactionDto();
        getPaymentTransactionDto.setId(paymentTransactionEntity.getId());
        getPaymentTransactionDto.setTransactionDate(paymentTransactionEntity.getTransactionDate());
        getPaymentTransactionDto.setAccountNumber(paymentTransactionEntity.getAccountNumber());
        getPaymentTransactionDto.setBusinessEstablishment(paymentTransactionEntity.getBusinessEstablishment());
        getPaymentTransactionDto.setValue(paymentTransactionEntity.getValue());
        getPaymentTransactionDto.setNumberOfCurrentInstallment(paymentTransactionEntity.getNumberOfCurrentInstallment());
        getPaymentTransactionDto.setNumberOfInstallments(paymentTransactionEntity.getNumberOfInstallments());
        getPaymentTransactionDto.setValueOfInstallments(paymentTransactionEntity.getValueOfInstallments());
        getPaymentTransactionDto.setCreditCardBillId(paymentTransactionEntity.getCreditCardBillId());

        return getPaymentTransactionDto;
    }
}
