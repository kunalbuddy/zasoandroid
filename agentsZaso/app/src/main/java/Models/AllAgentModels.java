package Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gajendrasingh on 11/25/2015.
 */

public class AllAgentModels implements Parcelable {

    private String objectid;
    private String fName;
    private  String lName;
    private String mobileNumber;
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

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

        protected AllAgentModels(Parcel in) {
            objectid = in.readString();
            fName = in.readString();
            lName = in.readString();
            mobileNumber = in.readString();
            emailid = in.readString();
            address = in.readString();
            speciality = in.readString();
            if (in.readByte() == 0x01) {
                location = new ArrayList<PositionModel>();
                in.readList(location, PositionModel.class.getClassLoader());
            } else {
                location = null;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(objectid);
            dest.writeString(fName);
            dest.writeString(lName);
            dest.writeString(mobileNumber);
            dest.writeString(emailid);
            dest.writeString(address);
            dest.writeString(speciality);
            if (location == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeList(location);
            }
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<AllAgentModels> CREATOR = new Parcelable.Creator<AllAgentModels>() {
            @Override
            public AllAgentModels createFromParcel(Parcel in) {
                return new AllAgentModels(in);
            }

            @Override
            public AllAgentModels[] newArray(int size) {
                return new AllAgentModels[size];
            }
        };

    public AllAgentModels()
    {
        super();
    }
}
