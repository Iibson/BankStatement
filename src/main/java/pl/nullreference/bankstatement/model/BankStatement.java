package pl.nullreference.bankstatement.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BankStatement {
    private Date Date;
    private Double BeginningBalance;
    private Double EndBalance;
    private String BankName;
    private List<BankStatementItem> items;
}
