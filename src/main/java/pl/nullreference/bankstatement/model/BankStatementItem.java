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

@Data
@Builder
@AllArgsConstructor
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

    public BankStatementItem(String[] data, Map<String, Integer> mapping) throws ParseException {
        this.id = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        this.operationDate = new SimpleDateFormat("dd-MMM-yy").parse(data[mapping.get("date")]);
        this.cardAccountNumber = data[mapping.get("cardAccountNumber")];
        this.sum = Double.parseDouble(data[mapping.get("sum")]);
        this.currency = data[mapping.get("currency")];
        this.balance = Double.parseDouble(data[mapping.get("balance")]);
    }
}
