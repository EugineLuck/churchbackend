package co.ke.emtechhouse.es.Auth.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePassword {
    private String memberId;
    private String password;


    public UpdatePassword() {
        // Default constructor
    }

    // Constructor with parameters
    public UpdatePassword(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    @JsonProperty("memberId")
    public String getMemberId() {
        return memberId;
    }

    @JsonProperty("memberId")
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
