package pl.nullreference.bankstatement.model.source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.nullreference.bankstatement.model.provider.Provider;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Builder
@Entity(name = "bankstatementsource")
public class BankStatementSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private SourceType type;
    private String sourcePath;
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    private Provider provider;

    public BankStatementSource() {}
}
