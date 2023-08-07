package co.ke.emtechhouse.es.GoalComponent.GivingScheduleComponent;

import co.ke.emtechhouse.es.GoalComponent.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class GivingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Double amount = 0.00;
    private Double amountDue = 0.00;
    private Double amountBalance = 0.00;
    @Enumerated(EnumType.STRING)
    private GoalType goalType;
    private Boolean saved = false;
    @Column(nullable = false)
    private Date dateOfGiving;
    private Long goalFk;
    private String memberNumber;
//    private Long familyFk;
//    private Long familyMemberFk;
//    private Long groupFk;
//    private Long groupMemberFk;
    private String status;
}
