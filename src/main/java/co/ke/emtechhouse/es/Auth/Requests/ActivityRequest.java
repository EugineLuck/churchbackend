package co.ke.emtechhouse.es.Auth.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ActivityRequest {

    private String eventName;
    private String participants;

    private String eventDate;
    private String requirements;
    private String description;
    private String status;

    private List<Long> familyId;
    private List<Long> churchId;
    private List<Long> communityId;
    private List<Long> groupsId;
}
