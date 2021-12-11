package pl.nullreference.bankstatement.seed;

import pl.nullreference.bankstatement.deserializer.staticData.MappingValuesData;
import pl.nullreference.bankstatement.deserializer.staticData.SettingsData;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.provider.ProviderMappingValue;
import pl.nullreference.bankstatement.model.provider.ProviderSetting;

import java.util.List;

public class ProviderSeed {

    public static List<Provider> getProviderSeed() {

        return List.of(
                Provider.builder()
                        .mappingValues(List.of(
                                ProviderMappingValue.builder().mapFrom(0).mapTo(MappingValuesData.date).build(),
                                ProviderMappingValue.builder().mapFrom(4).mapTo(MappingValuesData.cardAccountNumber).build(),
                                ProviderMappingValue.builder().mapFrom(8).mapTo(MappingValuesData.sum).build(),
                                ProviderMappingValue.builder().mapFrom(9).mapTo(MappingValuesData.currency).build(),
                                ProviderMappingValue.builder().mapFrom(15).mapTo(MappingValuesData.balance).build(),
                                ProviderMappingValue.builder().mapFrom(6).mapTo(MappingValuesData.operationDescription).build()
                        ))
                        .settings(List.of(
                                ProviderSetting.builder().name(SettingsData.skipLines).value("20").build(),
                                ProviderSetting.builder().name(SettingsData.dateFormat).value("dd.mm.yyyy").build(),
                                ProviderSetting.builder().name(SettingsData.separator).value(";").build()
                        ))
                        .extension("csv")
                        .name("ING")
                        .build(),
                Provider.builder()
                        .mappingValues(List.of(
                                ProviderMappingValue.builder().mapFrom(0).mapTo(MappingValuesData.date).build(),
                                ProviderMappingValue.builder().mapFrom(4).mapTo(MappingValuesData.cardAccountNumber).build(),
                                ProviderMappingValue.builder().mapFrom(8).mapTo(MappingValuesData.sum).build(),
                                ProviderMappingValue.builder().mapFrom(9).mapTo(MappingValuesData.currency).build(),
                                ProviderMappingValue.builder().mapFrom(15).mapTo(MappingValuesData.balance).build(),
                                ProviderMappingValue.builder().mapFrom(6).mapTo(MappingValuesData.operationDescription).build()
                        ))
                        .settings(List.of(
                                ProviderSetting.builder().name(SettingsData.skipLines).value("20").build(),
                                ProviderSetting.builder().name(SettingsData.dateFormat).value("dd.mm.yyyy").build()
                        ))
                        .extension("xlsx")
                        .name("ING")
                        .build(),
                Provider.builder()
                        .mappingValues(List.of(
                                ProviderMappingValue.builder().mapFrom(1).mapTo(MappingValuesData.date).build(),
                                ProviderMappingValue.builder().mapFrom(0).mapTo(MappingValuesData.cardAccountNumber).build(),
                                ProviderMappingValue.builder().mapFrom(7).mapTo(MappingValuesData.sum).build(),
                                ProviderMappingValue.builder().mapFrom(10).mapTo(MappingValuesData.currency).build(),
                                ProviderMappingValue.builder().mapFrom(9).mapTo(MappingValuesData.balance).build(),
                                ProviderMappingValue.builder().mapFrom(6).mapTo(MappingValuesData.operationDescription).build()
                        ))
                        .settings(List.of(
                                ProviderSetting.builder().name(SettingsData.separator).value(",").build(),
                                ProviderSetting.builder().name(SettingsData.skipLines).value("1").build(),
                                ProviderSetting.builder().name(SettingsData.dateFormat).value("yyyy-mm-dd").build()
                        ))
                        .extension("csv")
                        .name("Millennium")
                        .build(),
                Provider.builder()
                        .mappingValues(List.of(
                                ProviderMappingValue.builder().mapFrom(1).mapTo(MappingValuesData.date).build(),
                                ProviderMappingValue.builder().mapFrom(0).mapTo(MappingValuesData.cardAccountNumber).build(),
                                ProviderMappingValue.builder().mapFrom(7).mapTo(MappingValuesData.sum).build(),
                                ProviderMappingValue.builder().mapFrom(10).mapTo(MappingValuesData.currency).build(),
                                ProviderMappingValue.builder().mapFrom(9).mapTo(MappingValuesData.balance).build(),
                                ProviderMappingValue.builder().mapFrom(6).mapTo(MappingValuesData.operationDescription).build()
                        ))
                        .settings(List.of(
                                ProviderSetting.builder().name(SettingsData.skipLines).value("1").build(),
                                ProviderSetting.builder().name(SettingsData.dateFormat).value("yyyy-mm-dd").build()
                        ))
                        .extension("xlsx")
                        .name("Millennium")
                        .build()
        );
    }
}
