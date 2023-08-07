package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos;

import lombok.Data;

@Data
public class SmsDto {
    private String msisdn;
    private String text;

    public SmsDto(String msisdn, String text) {
        this.msisdn = msisdn;
        this.text = text;
    }
}
