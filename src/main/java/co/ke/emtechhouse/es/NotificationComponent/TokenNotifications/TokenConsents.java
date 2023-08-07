package co.ke.emtechhouse.es.NotificationComponent.TokenNotifications;

//import co.ke.emtechhouse.es.NotificationComponent.ConsentComponent.Consent;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenConsents {
    @EmbeddedId
    TokenConsentKey tokenConsentKey;

    @ManyToOne
    @MapsId("token")
    @JoinColumn(name = "token_id")
    private Token token;

//    @ManyToOne
//    @MapsId("consent")
//    @JoinColumn(name = "consent_id")
//    private Consent consent;
            ;
}
