package co.ke.emtechhouse.es.Auth.Requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Lob;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UpdateGiving {
    private String givingLevel;
    private String startDate;
    private String endDate;
    private String givingTitle;
    private Long id;

    @Lob
    private String description;

    private Double targetAmount;
    private String status;

}
