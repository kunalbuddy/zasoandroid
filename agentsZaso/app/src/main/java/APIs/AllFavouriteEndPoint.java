package APIs;

import java.util.List;

import Models.FavouriteModel;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by gajendrasingh on 12/22/2015.
 */
public interface AllFavouriteEndPoint {
    @GET("/agent/favourite/getAll/fav")
    public void getAllFav(Callback<List<FavouriteModel>> callback);
}
