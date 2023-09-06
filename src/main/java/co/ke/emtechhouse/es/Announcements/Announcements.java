package co.ke.emtechhouse.es.Announcements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Announcements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String message;
    private String announcementDate;



    private String postedBy;
    private String announcementCategory;
    @Column(name = "isActive")
    private boolean isActive = true;
}