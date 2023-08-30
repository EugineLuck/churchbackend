package co.ke.emtechhouse.es.Auth.Responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String userName;
    private String imageBanner;

    ;
    private Character firstLogin;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id,  String userName, String imageBanner, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.userName=userName;
        this.imageBanner=imageBanner;
        this.roles = roles;
        this.firstLogin = firstLogin;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getFirstLogin() {
        return firstLogin;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
  public String getImageBanner() {
        return imageBanner;
    }

    public void setImageBanner(String imageBanner) {
        this.imageBanner = imageBanner;
    }

    public List<String> getRoles() {
        return roles;
    }


    public void setUsername(String userName)
    {this.userName = userName;
    }
}


