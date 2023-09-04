package co.ke.emtechhouse.es.Giving;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Giving {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String givingLevel;
    private String startDate;
    private String endDate;
    private String givingTitle;

    @Lob
    private String description;
//    private Long groupId;
    private Double targetAmount;
    private Double amount;
    private String status;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character postedFlag;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date postedTime;

    public void setSaved(Boolean aTrue) {

    }

//    Group,family,church,community



}
