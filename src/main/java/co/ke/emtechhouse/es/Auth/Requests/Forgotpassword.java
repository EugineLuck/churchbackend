package co.ke.emtechhouse.es.Auth.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Forgotpassword {
    private String userName;

    private String newPassword;
}
