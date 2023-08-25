package co.ke.emtechhouse.es.Auth.Requests;

public class UpdateMemberRole {
    private Long roleid;
    private Long memberId;

    public UpdateMemberRole(Long roleid, Long memberId) {
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
