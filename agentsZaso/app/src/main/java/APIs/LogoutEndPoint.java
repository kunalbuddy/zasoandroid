package APIs;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by gajendrasingh on 12/22/2015.
 */
public interface  LogoutEndPoint {

    @POST("/agent/logout/deleteInfo/{accessToken}")
    public void logoutUser(@Path("accessToken")String accessToken, Callback<String> callback);
}
