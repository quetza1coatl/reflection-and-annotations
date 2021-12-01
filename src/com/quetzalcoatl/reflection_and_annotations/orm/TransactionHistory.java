package com.quetzalcoatl.reflection_and_annotations.orm;

import com.quetzalcoatl.reflection_and_annotations.orm.annotations.Column;
import com.quetzalcoatl.reflection_and_annotations.orm.annotations.PrimaryKey;

public class TransactionHistory {
    @PrimaryKey
    private Long transactionId;

    @Column
    private Integer accountNumber;

    @Column
    private String name;

    @Column
    private String transactionType;

    @Column
    private double amount;

    public TransactionHistory(){}

    public TransactionHistory(Integer accountNumber, String name, String transactionType, double amount) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.transactionType = transactionType;
        this.amount = amount;
    }


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "transactionId=" + transactionId +
                ", accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                '}';
    }
}
