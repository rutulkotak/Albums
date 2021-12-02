# Albums
Demo application to showcase latest recommendations from Android Community for architecture approach and libraries

Architecture and main libraries used:
- Kotlin
- AndroidX
- MVVM
- Data Binding
- Room
- Retrofit
- Recycle
- List pull to refresh
- LiveData
- Coroutine
- Local testing
- Instrumentation testing

Application:
- MinSDK Version is 23
- Application have a screen which displays albums list sorted by title
- Remote data source "https://jsonplaceholder.typicode.com/albums" to get list of albums
- Romm is the Local data store
- Offline display from local data store
- Following architecture

![image](https://user-images.githubusercontent.com/3943212/144451944-61d3d413-ee63-41a2-aaa3-28d16e7f64d7.png)


Future Improvements:
- Sync local-remote :- Current sync simply remove all the local data and add all the remote records.
- Albums list :- Search can be helpful for the list of albums screen
- WorkManager :- Helpful to update local store from remote data source periodically
- Navigation Component
