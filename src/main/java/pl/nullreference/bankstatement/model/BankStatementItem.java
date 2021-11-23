package pl.nullreference.bankstatement.model;

import lombok.Data;

import java.util.Currency;
import java.util.Date;

@Data
public class BankStatementItem {
    private Long id;
    private Date OperationDate;
    private String OperationDescription;
    private String CardAccountNumber;
    private Double Sum;
    private Currency String;
    private Double Balance;
}
