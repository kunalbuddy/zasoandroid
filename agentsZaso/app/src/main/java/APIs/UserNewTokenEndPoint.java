package APIs;

import Models.ResponseBack;
import Models.UserAuthentication;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by gajendrasingh on 12/12/2015.
 */

public interface UserNewTokenEndPoint {

    @POST("/agent/Login/authentication")
    void storeNewTokenInfo( @Body UserAuthentication userAuthentication,Callback<ResponseBack> callback);
}
