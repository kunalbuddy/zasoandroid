package APIs;

import Models.NewUserModel;
import Models.ResponseBack;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by gajendrasingh on 12/11/2015.
 */
public interface NewUserEndPoint {

    @POST("/agent/RegisterUser/enterInfo")
    void saveNewUser(@Body NewUserModel newUserModel,Callback<ResponseBack> callback);
}
