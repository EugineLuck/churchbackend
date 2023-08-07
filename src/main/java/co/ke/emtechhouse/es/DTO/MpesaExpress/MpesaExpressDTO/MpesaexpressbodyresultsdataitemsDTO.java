package co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MpesaexpressbodyresultsdataitemsDTO {
    private List<MpesaexpressbodyresultsdataitemsarrayDTO> Item;
    @JsonProperty("Item")
    public List<MpesaexpressbodyresultsdataitemsarrayDTO> getItem() {
        return Item;
    }

    public void setItem(List<MpesaexpressbodyresultsdataitemsarrayDTO> item) {
        Item = item;
    }
}
