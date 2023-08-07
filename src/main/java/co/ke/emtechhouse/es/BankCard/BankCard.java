package co.ke.emtechhouse.es.BankCard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String cardNumber;
    private  String cardHolderName;
    @Column(nullable = false)
    private Date expiryDate;
    private String cvv;
    private Long bankFk;
    private Long usersFk;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character postedFlag;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date postedTime;
    @Column(nullable = false, length = 200)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String postedBy;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character verifiedFlag;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date verifiedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(length = 200)
    private String verifiedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(length = 200)
    private String modifiedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private Character modifiedFlag='N';
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date modifiedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private Character deletedFlag;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date deletedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String deletedBy;
}
