package com.example.photogallery

import com.google.gson.annotations.SerializedName

data class GalleryItem(
    var title: String = "",
    var id: String = "",
    //use Gson to convert json to kotlin
    @SerializedName("url_s") var url: String = ""
)