package co.ke.emtechhouse.es.Auth.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class GivingRequest {
    private String givingLevel;
    private String startDate;
    private String endDate;
    private String givingTitle;

    @Lob
    private String description;
    private List<Long> familyId;
    private List<Long> churchId;
    private List<Long> communityId;
    private List<Long> groupId;
    private Double targetAmount;
    private String status;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character postedFlag;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date postedTime;

}
