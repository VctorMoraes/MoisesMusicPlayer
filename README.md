# [MoisesMusicPlayer](https://github.com/VctorMoraes/MoisesMusicPlayer)

## Project

## How To

## Architecture
* Multi Module for separation of concerns.
* Clean Architecture with MVVM.
* Hilt for dependency injection.
* Retrofit and OkHttp for APIs consumption.
* Media3 ExoPlayer and MediaSession as Media Player.

### Project Organization
Multi Module project separated into 3 main layers: `:feature`, `:domain` and `:data`. There is also a `:core` module for common classes used accross multiple submodules.\

### Architcture diagram:
![layer_arch](https://github.com/user-attachments/assets/22504bc1-8e9d-42c4-b6be-5a1a20a13d43)

### MediaPlayerService
A Foreground Service that encapsulates the **ExoPlayer** and the **MediaSession** so that Media can be played on the backgorund, without the app needing to be onscreen.

More information:
* [Using MediaPlayer in a service](https://developer.android.com/media/platform/mediaplayer#mpandservices)
* [Background playback with a MediaSessionService](https://developer.android.com/media/media3/session/background-playback)

## API
Media information requests made to [iTunes Search API](https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/Searching.html#//apple_ref/doc/uid/TP40017632-CH5-SW1).

## Tests
* Unit test with JUnit4 in the components: `SearchRepository` and `SearchPagingSource`
* Mockk for mocks
* OkHttp MockWebServer for API testing
* Kover for code coverage

## Test Coverage
Test Coverage metrics are being generated using [**Kover**](https://github.com/Kotlin/kotlinx-kover).

Currently, the only module with Unit test is `:data:song-search`, with a coverage of **53%** in all methods.

To check overall code coverage, run:
```bash
  ./gradlew koverHtmlReportDebug
```

![image](https://github.com/user-attachments/assets/7a596085-4475-4246-8f93-7e36f960af9e)

## Aditional Features
### Loading animation when searching for a song:
![image](https://github.com/user-attachments/assets/6e8c601b-32ee-491a-ae25-b55cae4adda7)

### Loading animation when paginating a list of songs:
![image](https://github.com/user-attachments/assets/a375422d-b1d3-4b42-b14c-0bac5cabbdef)

### Currently playing animation:
![image](https://github.com/user-attachments/assets/514222fb-c27b-4732-b2fe-2741a51735dc)

### Currently playing notification with functional actions:
![image](https://github.com/user-attachments/assets/532819fa-2a7c-4d4c-a328-85d9214e72e6)

### Playlists
* When a song in the Songs Search is clicked, all 10 following songs will be added to the playlist in order, and the clicked song will be played.
* When a song in the album list is clicked, all songs from that album will be added to the playlist in order, and the clicked song will be played.

  ![image](https://github.com/user-attachments/assets/a795da07-7a37-42c0-ac3f-befdb3b8d8cf)

### Seek to position and player actions
Functional seek to position in the slider and player actions.

## Next Steps
* Increase Unit test code coverage
* Create a Local Database with **Room** or **Realm** to act as a Single Source of Truth and cache song searchs.
* Create a *currentPlaying* component available in all Screens that can directly open the **SongPlayer**.
* Optimize Song selection in the Album Screen.
