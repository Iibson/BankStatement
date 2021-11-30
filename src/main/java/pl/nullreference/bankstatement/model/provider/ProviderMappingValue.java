package pl.nullreference.bankstatement.model.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Builder
@Entity(name = "providermappingvalue")
public class ProviderMappingValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int mapFrom;
    private String mapTo;

    public ProviderMappingValue() {

    }
}
