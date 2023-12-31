package co.ke.emtechhouse.es.Auth.Requests;

public class UpdateMemberRole {
    private Long roleid;
    private String memberId;

    public UpdateMemberRole(Long roleid, String memberId) {
        this.roleid = roleid;
        this.memberId = memberId;
    }

    public UpdateMemberRole() {
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
