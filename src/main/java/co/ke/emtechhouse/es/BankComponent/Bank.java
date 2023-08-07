package co.ke.emtechhouse.es.BankComponent;

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
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String bankName;
    @Lob
    private String backgroundImage;
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
