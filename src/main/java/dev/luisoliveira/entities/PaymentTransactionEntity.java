package dev.luisoliveira.entities;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "tb_payment_transaction")
@EqualsAndHashCode(callSuper = true)
public class PaymentTransactionEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Integer getNumberOfCurrentInstallment() {
        return numberOfCurrentInstallment;
    }

    public void setNumberOfCurrentInstallment(Integer numberOfCurrentInstallment) {
        this.numberOfCurrentInstallment = numberOfCurrentInstallment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTransactionEntity)) return false;
        if (!super.equals(o)) return false;
        PaymentTransactionEntity that = (PaymentTransactionEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getBusinessEstablishment(), that.getBusinessEstablishment()) && Objects.equals(getValue(), that.getValue()) && Objects.equals(getValueOfInstallments(), that.getValueOfInstallments()) && Objects.equals(getNumberOfInstallments(), that.getNumberOfInstallments()) && Objects.equals(getNumberOfCurrentInstallment(), that.getNumberOfCurrentInstallment()) && Objects.equals(getAccountNumber(), that.getAccountNumber()) && Objects.equals(getCreditCardBillId(), that.getCreditCardBillId()) && Objects.equals(getTransactionDate(), that.getTransactionDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getBusinessEstablishment(), getValue(), getValueOfInstallments(), getNumberOfInstallments(), getNumberOfCurrentInstallment(), getAccountNumber(), getCreditCardBillId(), getTransactionDate());
    }
}
