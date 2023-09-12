package co.ke.emtechhouse.es.Subscriptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DTO {
    private Long id;
    private String phoneNumber;
    private String fullName;
    private String memberNumber;
    private String subscriptionType;
    private Double charges;
    private String descriptionInfo;
    private String banner;
    private String dateCreated;
//    private String active;

    private List<?> subscribers;

}
