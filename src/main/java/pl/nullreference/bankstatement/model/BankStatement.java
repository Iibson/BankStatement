package pl.nullreference.bankstatement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
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
    @OneToMany
    private Set<BankStatementItem> items;
}
