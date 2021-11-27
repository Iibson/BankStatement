package pl.nullreference.bankstatement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Builder
@Entity(name = "bankStatementItem")
public class BankStatementItem {
    @Id
    private int id;
    private Date operationDate;
    private String operationDescription;
    private String cardAccountNumber;
    private Double sum;
    private String currency;
    private Double balance;

    public BankStatementItem() {

    }
}
