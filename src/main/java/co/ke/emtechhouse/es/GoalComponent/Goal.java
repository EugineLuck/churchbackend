package co.ke.emtechhouse.es.GoalComponent;

import co.ke.emtechhouse.es.GoalComponent.GivingScheduleComponent.GivingSchedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String givingGoalName;
    private Double targetAmount; //6000
    private String memberNumber;
    private Double givingAmount;
    private String givingPeriod;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private GivingFrequency givingFrequency;
    @Enumerated(EnumType.STRING)
    private GoalType goalType;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date nextGivingDate;

    @JsonIgnore
    private Character deletedFlag;
    @JsonIgnore
    private String deletedBy;

    @OneToMany(targetEntity = GivingSchedule.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "goalFk", referencedColumnName = "id")
    private List<GivingSchedule> givingSchedules = new ArrayList<>();
}
