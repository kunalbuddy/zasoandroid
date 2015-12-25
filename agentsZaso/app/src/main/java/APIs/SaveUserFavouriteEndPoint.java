package APIs;

import Models.FavouriteModel;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by gajendrasingh on 12/22/2015.
 */
public interface SaveUserFavouriteEndPoint {
    @POST("/agent/favourite/save/fav/{accessToken}")
    public void saveFavourite(@Path("accessToken")String accessToken,@Body FavouriteModel favouriteModel, Callback<FavouriteModel> callback);
}
