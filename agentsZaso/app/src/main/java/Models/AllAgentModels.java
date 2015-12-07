package Models;

import java.util.List;

/**
 * Created by gajendrasingh on 11/25/2015.
 */

public class AllAgentModels {
    private String objectid;
    private String emailid;
    private String address;
    private String speciality;
    private List<PositionModel> location;

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getObjectid() {
        return objectid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSpeciality() {
        return speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public List<PositionModel> getLocation() {
        return location;
    }
    public void setLocation(List<PositionModel> location) {
        this.location = location;
    }
}
