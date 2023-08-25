package co.ke.emtechhouse.es.IDNO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class IDNOdto {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String documentNumber;
}
