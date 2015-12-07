package APIs;

import java.util.List;

import Models.AllAgentModels;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by gajendrasingh on 11/25/2015.
 */
public interface AllAgentEndPoint {
    @GET("/agent/mongo/All")
    void getAllAgent(Callback<List<AllAgentModels>> callback);


}
