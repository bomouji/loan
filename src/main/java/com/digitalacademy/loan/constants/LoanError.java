package com.digitalacademy.loan.constants;

import lombok.Getter;

@Getter
public enum LoanError {
    GET_LOAN_INFO_EXCEPTION("LOAN001","cannot get loan information."),
    GET_LOAN_INFO_NOT_FOUND("LOAN002","Loan infomation not found.");

    private String code;
    private String message;

    LoanError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
