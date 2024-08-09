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

## Next Steps
* Increase Unit test code coverage
* Create a Local Database with **Room** or **Realm** to act as a Single Source of Truth and cache song searchs.
* Create a *currentPlaying* component available in all Screens that can directly open the **SongPlayer**.
