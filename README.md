# FancyVideo-API
VLC API to play video and audio on Minecraft. Works on Fabric and Forge. It's an API for developers.

[![](https://jitpack.io/v/dev.polv/fancyvideo-api-polcinematics.svg)](https://jitpack.io/#dev.polv/fancyvideo-api-polcinematics)

## How to use
### For users
1. Download the latest release from the [releases](https://github.com/polvallverdu/FancyVideo-API-PolCinematics/releases) or [modrinth](https://modrinth.com/mod/fancyvideoapi-pol).

### For developers

You can you use it with Gradle by adding the following to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation("dev.polv.FancyVideo-API-PolCinematics:fancyvideo:{release or commit hash}")
}
```

And for fabric/forge subprojects:
```groovy
dependencies {
   modApi("dev.polv.FancyVideo-API-PolCinematics:fancyvideo-{fabric/forge):{release or commit hash}")
}
```

## Past contributors
- [Nick1st](https://github.com/Nick1st)

## Currently supported platforms
-[X] Windows 10/11
-[ ] macOS
-[ ] Linux
