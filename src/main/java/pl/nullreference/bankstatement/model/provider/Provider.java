package pl.nullreference.bankstatement.model.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@Entity(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String extension;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="provider_id", referencedColumnName = "id")
    private List<ProviderSetting> settings;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="provider_id", referencedColumnName = "id")
    private List<ProviderMappingValue> mappingValues;

    public Provider() {

    }

    public Map<String, String> getSettingsAsMap() {
        return this.settings
                .stream()
                .collect(Collectors.toMap(ProviderSetting::getName, ProviderSetting::getValue));
    }

    public Map<String, Integer> getMappingValuesAsMap() {
        return this.mappingValues
                .stream()
                .collect(Collectors.toMap(ProviderMappingValue::getMapTo, ProviderMappingValue::getMapFrom));
    }
}
