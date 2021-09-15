@file:Suppress("DEPRECATION")

package com.example.photogallery

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.ConcurrentHashMap

private const val TAG = "ThumbnailDownloader"
private const val MESSAGE_DOWNLOAD = 0  //Used to identify messages as download requests (what)

//LifecycleObserver observes lifecycle of fragment, or activity. In this case, it's PhotoGalleryFragment
class ThumbnailDownloader<in T>(
    private val responseHandler: Handler,
    private val onThumbnailDownloaded: (T, Bitmap) -> Unit
) : HandlerThread(TAG) {

    //Observes fragment lifecycle (so that we don't make additional requests on config change and crash)
    val fragmentLifecycleObserver: LifecycleObserver = object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun setup() {
            Log.i(TAG, "Starting background thread")
            start()
            looper
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun tearDown() {
            Log.i(TAG, "Destroying background thread")
            quit()
        }
    }

    //Add a view lifecycle observer. This will stop requests running when app is closed
    val viewLifecycleObserver: LifecycleObserver = object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun clearQueue() {
            Log.i(TAG, "Clearing all requests from queue")
            requestHandler.removeMessages(MESSAGE_DOWNLOAD)
            requestMap.clear()
        }
    }

    private var hasQuit = false
    private lateinit var requestHandler: Handler

    private val requestMap = ConcurrentHashMap<T, String>()
    private val flickrFetcher = FlickrFetcher()

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        requestHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    val target = msg.obj as T
                    Log.i(TAG, "Got a request for URL: ${requestMap[target]}")
                    handleRequest(target)
                }
            }
        }
    }

    override fun quit(): Boolean {
        hasQuit = true
        return super.quit()
    }

    fun queueThumbnail(target: T, url: String) {
        Log.i(TAG, "Got a URL: $url")
        requestMap[target] = url
        requestHandler.obtainMessage(MESSAGE_DOWNLOAD, target).sendToTarget()
    }

    //This sends it back to
    private fun handleRequest(target: T) {
        val url = requestMap[target] ?: return
        val bitmap = flickrFetcher.fetchPhoto(url) ?: return

        //Executed on main thread
        responseHandler.post(Runnable {
            if (requestMap[target] != url || hasQuit) {
                return@Runnable
            }
            requestMap.remove(target)
            onThumbnailDownloaded(target, bitmap)
        })
    }
}