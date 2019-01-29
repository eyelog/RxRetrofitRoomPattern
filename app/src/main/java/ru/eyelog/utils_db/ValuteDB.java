package ru.eyelog.utils_db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ru.eyelog.models.ValuteTO;

@Entity
public class ValuteDB {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String xmlID;
    private String NumCode;
    private String CharCode;
    private String Nominal;
    private String Name;
    private String Value;

    public ValuteDB() {
    }

    public ValuteDB(String numCode, String charCode, String nominal, String name, String value) {
        NumCode = numCode;
        CharCode = charCode;
        Nominal = nominal;
        Name = name;
        Value = value;
    }

    public ValuteDB(int id, String numCode, String charCode, String nominal, String name, String value) {
        this.id = id;
        NumCode = numCode;
        CharCode = charCode;
        Nominal = nominal;
        Name = name;
        Value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getXmlID() {
        return xmlID;
    }

    public void setXmlID(String xmlID) {
        this.xmlID = xmlID;
    }

    public String getNumCode() {
        return NumCode;
    }

    public void setNumCode(String numCode) {
        NumCode = numCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public String getNominal() {
        return Nominal;
    }

    public void setNominal(String nominal) {
        Nominal = nominal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public ValuteTO getListTO(){
        return new ValuteTO(
                getId(),
                getXmlID(),
                getNumCode(),
                getCharCode(),
                getNominal(),
                getName(),
                getValue());
    }
}
