package co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO;


import com.fasterxml.jackson.annotation.JsonProperty;

public class MpesaexpressbodyresultsdataitemsarrayDTO {
    private String Name;
    private String Value;
    @JsonProperty("Name")

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    @JsonProperty("Value")
    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
