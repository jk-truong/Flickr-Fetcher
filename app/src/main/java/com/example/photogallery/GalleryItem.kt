package com.example.photogallery

import android.net.Uri
import com.google.gson.annotations.SerializedName
import okhttp3.internal.platform.Platform.get

data class GalleryItem(
    var title: String = "",
    var id: String = "",
    //use Gson to convert json to kotlin
    @SerializedName("url_s") var url: String = "",
    @SerializedName("owner") var owner: String = ""
) {   //This will determine the photo URL
    val photoPageUri: Uri
        get() {
            return Uri.parse("https://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(owner)
                .appendPath(id)
                .build()
        }
}
