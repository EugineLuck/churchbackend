package co.ke.emtechhouse.es.Stages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Stages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String memberNumber;
    private String stageName;
    private String stageAward;
    private String startDate;
    private String completionDate;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character postedFlag;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date postedTime;
    //    @Column(nullable = false, length = 200)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String postedBy;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character verifiedFlag;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private Character deletedFlag;
    private String modifiedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private Character modifiedFlag='N';
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date modifiedTime;
}
