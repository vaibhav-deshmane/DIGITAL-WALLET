package com.bank.exceptions;

public class BankAccountNotFound extends Exception {
    public BankAccountNotFound(String bank_account_not_found) {
        super(bank_account_not_found);
    }
}
