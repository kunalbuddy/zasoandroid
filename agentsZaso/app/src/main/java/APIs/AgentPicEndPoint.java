package APIs;

import java.util.List;

import Models.AgentPicModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by gajendrasingh on 11/25/2015.
 */
public interface AgentPicEndPoint {
    @GET("/agent/mongo/get/picByEmail/{email}")
    void getPicturesOfAgent(@Path("email")String email ,Callback<List<AgentPicModel>> callback);
}
