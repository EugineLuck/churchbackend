package co.ke.emtechhouse.es.MpesaIntergration.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mpesa.daraja")
public class MpesaConfiguration {
    private String consumerKey;
    private String consumerSecret;
    private String grantType;
    private String oauthEndpoint;
    private String shortCode;
    private String responseType;
    private String confirmationURL;
    private String validationURL;
    private String registerUrl;
    private String c2BTransaction;
    private String b2cTransactionEndpoint;
    private String b2cResultUrl;
    private String b2cQueueTimeoutUrl;
    private String b2cInitiatorName;
    private String b2cInitiatorPassword;
    private String transactionResultUrl;
    private String checkAccountBalanceUrl;
    private String stkPassKey;
    private String stkPushShortCode;
    private String stkPushRequestUrl;
    private String stkPushRequestCallbackUrl;
    private String stkPushTransactionStatus;

    @Override
    public String toString(){
        return String.format("{consumerKey='%s', consumerSecret='%s', grantType='%s', oauthEndpoint='%s'}",
                consumerKey, consumerSecret, grantType, oauthEndpoint);
    }
}
