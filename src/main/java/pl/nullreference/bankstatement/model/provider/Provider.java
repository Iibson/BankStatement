package pl.nullreference.bankstatement.model.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Entity(name = "provider")
public class Provider {
    @Id
    private int id;
    private String name;
    private String extension;
    @OneToMany
    private Set<ProviderSetting> settings;
    @OneToMany
    private Set<ProviderMappingValue> mappingValues;

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
