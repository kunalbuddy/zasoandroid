package APIs;

import Models.ResponseBack;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by gajendrasingh on 12/12/2015.
 */
public interface LoginInfoEndPoint {

    @POST("/agent/RegisterUser/getLoginInfo/{emailid}/{password}")
    void getLoginInfo(@Path("emailid")String emailid,@Path("password")String password,Callback<ResponseBack> callback);
}
