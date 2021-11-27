package pl.nullreference.bankstatement.deserializer;

import pl.nullreference.bankstatement.model.bankstatement.BankStatement;

public interface IDeserializer {
    BankStatement deserialize();
}
