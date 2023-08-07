package co.ke.emtechhouse.es.Auth.MailService;

import lombok.Data;

@Data
public class MailDto {
    private String to;
    private String subject;
    private String message;
}
