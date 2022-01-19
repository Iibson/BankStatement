package pl.nullreference.bankstatement.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.model.source.SourceType;

public class BankStatementSourceViewModel {
    public BankStatementSourceViewModel(BankStatementSource source){
        this.path = new SimpleStringProperty(source.getSourcePath());
        this.bankName = new SimpleStringProperty(source.getProvider().getName());
        this.extension = new SimpleStringProperty(source.getProvider().getExtension());
        this.type = new SimpleObjectProperty<>(source.getType());
    }
    private StringProperty path;
    private StringProperty bankName;
    private StringProperty extension;
    private ObjectProperty<SourceType> type;

    public SourceType getType() {
        return type.get();
    }

    public String getPath() {
        return path.get();
    }

    public String getBankName() {
        return bankName.get();
    }
    public String getExtension(){
        return extension.get();
    }
    public void setPath(String path) {
        this.path.set(path);
    }

    public void setBankName(String bankName){
        this.bankName.set(bankName);
    }
    public void setExtension(String extension){
        this.extension.set(extension);
    }

    public void setType(SourceType type) {
        this.type.set(type);
    }
    public StringProperty getPathProperty(){
        return this.path;
    }
    public StringProperty getBankNameProperty(){
        return this.bankName;
    }
    public StringProperty getExtensionProperty(){
        return this.extension;
    }
    public ObjectProperty<SourceType> getTypeProperty(){
        return this.type;
    }


}
