package pl.nullreference.bankstatement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity(name = "bankstatement")
public class BankStatement {
    @Id
    private int id;
    private Date date;
//    private Double beginningbalance;
    private Double endbalance;
    private String bankname;

    public BankStatement() {

    }
//    private List<BankStatementItem> items;
}
