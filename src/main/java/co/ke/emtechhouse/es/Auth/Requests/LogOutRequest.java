

package co.ke.emtechhouse.es.Auth.Requests;

public class LogOutRequest {
    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    private String modified_on;
    private String modified_by;
    private String memberNumber;
    private boolean status;

    public LogOutRequest(String modified_on, String modified_by, String memberNumber, boolean status) {
        this.modified_on = modified_on;
        this.modified_by = modified_by;
        this.memberNumber = memberNumber;
        this.status = status;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
