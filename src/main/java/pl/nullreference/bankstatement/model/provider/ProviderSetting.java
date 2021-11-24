package pl.nullreference.bankstatement.model.provider;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Entity(name = "providerSetting")
public class ProviderSetting {
    @Id
    private int id;
    private String name;
    private String value;

    public ProviderSetting() {

    }
}
