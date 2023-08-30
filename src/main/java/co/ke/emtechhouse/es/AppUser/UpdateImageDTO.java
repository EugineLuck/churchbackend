package co.ke.emtechhouse.es.AppUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Lob;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UpdateImageDTO {

    private String userName;
    @Lob
    private String imageBanner;

}
