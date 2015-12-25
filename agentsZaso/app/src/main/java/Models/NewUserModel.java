package Models;

/**
 * Created by gajendrasingh on 12/11/2015.
 */
public class NewUserModel {

    private String fullName;
    private String emaiId;
    private String mobileNumber;
    private String password;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmaiId() {
        return emaiId;
    }
    public void setEmaiId(String emaiId) {
        this.emaiId = emaiId;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "UserRegistration [fullName=" + fullName + ", emaiId=" + emaiId + ", mobileNumber=" + mobileNumber
                + ", password=" + password + "]";
    }

}
