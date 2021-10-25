# Flickr-Fetcher

Demo application to demonstrate the use of popular photography sharing website Flickr (https://www.flickr.com/). 

Uses REST API to grab photos and display them in a grid-style RecyclerView.
Includes a feature to search for specific types of photos (eg. search "dog", shows photos with dogs).

This app demonstrates the following:
- Fragments
- Retrofit Dependency, Creating a REST API instance
- Fetching JSON from Flickr and deserializing into model objects
- Displaying results in RecyclerView
- Image downloading via background threads
- Messages and message handlers
- Webview

Screenshots:

Home page (Grid RecyclerView)

![image](https://user-images.githubusercontent.com/41808114/138782053-32cebdcd-4aa5-4c6e-a35b-1de42cc1a56d.png)

Detailed View (WebView)

![image](https://user-images.githubusercontent.com/41808114/138782131-7a1794c3-3cdd-491b-912a-31a6e1d4404e.png)

API Search Function 

![image](https://user-images.githubusercontent.com/41808114/138782166-b637efd0-c029-4ce2-9a30-9313cb73f45d.png)
