package pl.nullreference.bankstatement.deserializer;

import pl.nullreference.bankstatement.model.BankStatement;

public interface IDeserializer {
    BankStatement deserialize();
}
