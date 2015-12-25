package APIs;

import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by gajendrasingh on 12/22/2015.
 */
public interface RemoveFavEndPoint {
    @POST("/agent/favourite/delete/fav/{email}")
    public void deleteFav(@Path("email")String email);
}
