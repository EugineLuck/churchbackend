package co.ke.emtechhouse.es.Auth.Members;

import co.ke.emtechhouse.es.Groups.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberUpdateDTO {
    private String email;
    private String phoneNumber;
    private String  memberNumber;
    private List<Long> groupsId;
    public List<Long> getGroupsId() {
        return groupsId;
    }

    private Long communityId;
    private Long outStationId;
}
