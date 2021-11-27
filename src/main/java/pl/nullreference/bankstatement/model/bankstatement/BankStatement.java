package pl.nullreference.bankstatement.model.bankstatement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity(name = "bankstatement")
public class BankStatement {
    @Id
    private int id;
    private Date date;
    private Double beginningbalance;
    private Double endbalance;
    private String bankname;

    public BankStatement() {

    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="bank_statement_id", referencedColumnName = "id")
    private List<BankStatementItem> items;
}
