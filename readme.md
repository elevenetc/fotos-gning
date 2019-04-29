# Photo search app - Fotosøgning

## Setup (optional)

Configure `FLICKR_API_KEY` in `build.gradle` 
## Test

```bash
./gradlew test
```
## Build

```bash
./gradlew assembleDebug
```
## Package structure

Package-by-feature is used as package structure
- `com.elevenetc.fotosgning.search`: photos search
- `com.elevenetc.fotosgning.home`: main screen of the app 
- `com.elevenetc.fotosgning.common`: common classes for other packages
## DI graph
![GitHub Logo](/docs/di-graph.png)
## Search feature
![GitHub Logo](/docs/feature-photo-search.png)