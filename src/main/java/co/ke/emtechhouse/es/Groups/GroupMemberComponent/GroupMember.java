package co.ke.emtechhouse.es.Groups.GroupMemberComponent;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Groups.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString
@Table(name = "member_groups")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members member;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private String status;

    private Date modifiedTime;

    @JsonIgnore
    private Date updationTime;



}
