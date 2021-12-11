package pl.nullreference.bankstatement.model.bankstatement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
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
    @Column(columnDefinition = "integer default '0'")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    public Category category = Category.NONE;

    public BankStatementItem() {

    }
}
