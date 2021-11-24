package pl.nullreference.bankstatement.model.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Entity(name = "providerMappingValue")
public class ProviderMappingValue {
    @Id
    private int id;
    private int mapFrom;
    private String mapTo;

    public ProviderMappingValue() {

    }
}
