package co.ke.emtechhouse.es.AppUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UpdateImageDTO {

    private String userName;
    private String imageBanner;

}
