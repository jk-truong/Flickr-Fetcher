package com.example.photogallery

import android.app.Application
import androidx.lifecycle.*

//ViewModel preserves data on configuration change
class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {

    //Stores live data object holding a list of gallery items
    val galleryItemLiveData: LiveData<List<GalleryItem>>

    private val flickrFetcher = FlickrFetcher()
    private val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm: String
        get() = mutableSearchTerm.value ?:""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        //Sends request to fetch photo data when ViewModel is init.
        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            if (searchTerm.isBlank()) {
                flickrFetcher.fetchPhotos()
            } else {
                flickrFetcher.searchPhotos(searchTerm)
            }
        }
    }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}