package pl.nullreference.bankstatement.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Currency;
import java.util.Date;

@Data
@Entity(name = "bankStatementItem")
public class BankStatementItem {
    @Id
    private int id;
    private Date OperationDate;
    private String OperationDescription;
    private String CardAccountNumber;
    private Double Sum;
    private Currency String;
    private Double Balance;
}
