package api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FlickrApi {

    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
        "&api_key=b1aec1bebe379564968afb8504938c71" +
        "&format=json" +
        "&nojsoncallback=1" +
        "&extras=url_s"
    )
    fun fetchPhotos(): Call<FlickrResponse> //Tells gson that it should use FlickrResponse to deserialize JSON data

    //Allows support for downloading images
    @GET
    fun fetchUrlBytes(@Url url: String): Call<ResponseBody>
}