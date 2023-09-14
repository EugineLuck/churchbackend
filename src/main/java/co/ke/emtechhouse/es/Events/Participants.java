package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.OutStation.OutStation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString
@Table(name = "activityParticipants")
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "eventId")
    private Events events;

    @ManyToOne
    @JoinColumn(name = "familyId")
    private Family family;
    @ManyToOne
    @JoinColumn(name = "churchId")
    private OutStation outStation;

    @ManyToOne
    @JoinColumn(name = "communityId")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Groups groups;

}
