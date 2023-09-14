package co.ke.emtechhouse.es.Subscribers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class subscriberDTO {
    private Long id;
    private String phoneNumber;
    private String memberNumber;
    private Long subscriptionItemId;
    private  String dateSubscribed;

    private List<?> subscriptions;
}
