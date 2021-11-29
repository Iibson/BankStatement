package pl.nullreference.bankstatement.model.bankstatement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@Entity(name = "bankstatementitem")
public class BankStatementItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
