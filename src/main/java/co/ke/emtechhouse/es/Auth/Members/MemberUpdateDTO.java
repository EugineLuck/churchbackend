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
    private Set<Groups> groups = new HashSet<>();
    private Long communityId;
    private Long outStationId;
}
