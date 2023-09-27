package co.ke.emtechhouse.es.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class County {
    private String countyName;
    private String countyCapital;
    private String countyCode;
    private List subCounties;
}