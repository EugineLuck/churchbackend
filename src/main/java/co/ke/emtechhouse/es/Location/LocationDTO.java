package co.ke.emtechhouse.es.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LocationDTO {
    private String countyName;
    private String subcountyName;
    private String constituencyName;
    private String wardName;
    private String villageName;
    private Long countyID;
    private Long subcountyID;
    private Long constituencyID;
    private Long wardID;
}
