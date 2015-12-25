package Models;

/**
 * Created by gajendrasingh on 12/12/2015.
 */
public class UserAuthentication {

    private String emilaId;
    private String authCode;
    public String getEmilaId() {
        return emilaId;
    }
    public void setEmilaId(String emilaId) {
        this.emilaId = emilaId;
    }
    public String getAuthCode() {
        return authCode;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    @Override
    public String toString() {
        return "UserAuthentication [emilaId=" + emilaId + ", authCode=" + authCode + "]";
    }

}
