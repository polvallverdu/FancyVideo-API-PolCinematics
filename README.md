# VLCVideoAPI
VLC API to play video and audio on Minecraft. Works on Fabric and Forge. It's an API for developers.

[![](https://jitpack.io/v/dev.polv/vlcvideo-api-polcinematics.svg)](https://jitpack.io/#dev.polv/vlcvideo-api-polcinematics)

## How to use
### For users
1. Download the latest release from the [releases](https://github.com/polvallverdu/VLCVideo-API-PolCinematics/releases) or [modrinth](https://modrinth.com/mod/vlcvideoapi-pol).

### For developers

You can you use it with Gradle by adding the following to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation("dev.polv.VLCVideo-API-PolCinematics:vlcvideoapi:2.1.0")
}
```

And for fabric/forge subprojects:
```groovy
dependencies {
   modApi("dev.polv.VLCVideo-API-PolCinematics:vlcvideoapi-{fabric/forge):2.1.0")
}
```

## Past contributors
- [Nick1st](https://github.com/Nick1st)

## Currently supported platforms
- [x] Windows 10/11
- [ ] macOS
- [ ] Linux
