package Models;

/**
 * Created by gajendrasingh on 12/16/2015.
 */
public class ResponseBack {

    private boolean status;
    private String tocken;
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getTocken() {
        return tocken;
    }
    public void setTocken(String tocken) {
        this.tocken = tocken;
    }
}
