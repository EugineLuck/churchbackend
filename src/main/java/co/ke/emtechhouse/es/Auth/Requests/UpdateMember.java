package co.ke.emtechhouse.es.Auth.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UpdateMember {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String nationalID;
    @NotBlank
    private String idOwnership;

    private String memberRole;

    @NotBlank
    @Size(min = 10, max = 12)
    private String phoneNo;
    private Long communityId;
    private Long familyId;
//    private Long outStationId;
//    private String modeOfRegistration;
    private List<Long> groupsId;

    // Getters and setters

    public List<Long> getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(List<Long> groupsId) {
        this.groupsId = groupsId;
    }

    private String memberNumber;
//    @NotBlank
//    @Size(min = 10, max = 40)
//    private String dateOfBirth;
}
