package co.ke.emtechhouse.es.PersonalDetails;

import co.ke.emtechhouse.es.USSD.USSD;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class PersonalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String memberNumber;
    @Lob
    private String profilePicture;


//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personalDetails")
//    private USSD ussd;
//
//
//    public void setUssd(USSD ussd){
//        ussd.setPersonalDetails(this);
//        this.ussd=ussd;}
}
