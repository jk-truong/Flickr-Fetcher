package com.example.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

//ViewModel preserves data on configuration change
class PhotoGalleryViewModel : ViewModel() {

    //Stores live data object holding a list of gallery items
    val galleryItemLiveData: LiveData<List<GalleryItem>>


    init {
        //Sends request to fetch photo data when ViewModel is init.
        galleryItemLiveData = FlickrFetcher().fetchPhotos()
    }
}