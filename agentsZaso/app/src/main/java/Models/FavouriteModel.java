package Models;

/**
 * Created by gajendrasingh on 12/22/2015.
 */
public class FavouriteModel {
    private String agentemail;

    private String address;
    //private String email;
    private String fname;
    private String lname;
    private String mobile;
    private String speciality;
    private String longitude;
    private String lattitude;
    private String picurl;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getAgentemail() {
        return agentemail;
    }

    public void setAgentemail(String agentemail) {
        this.agentemail = agentemail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    @Override
    public String toString() {
        return "FavouriteModel{" +
                "agentemail='" + agentemail + '\'' +
                ", address='" + address + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", speciality='" + speciality + '\'' +
                ", longitude='" + longitude + '\'' +
                ", lattitude='" + lattitude + '\'' +
                ", picurl='" + picurl + '\'' +
                '}';
    }
}
