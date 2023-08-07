package co.ke.emtechhouse.es.AppUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserReg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String userName;
    @Size(min = 6, max = 40)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Long roleFk;
}
