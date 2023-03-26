# FancyVideo-API
VLC API to play video and audio on Minecraft. Works on Fabric and Forge. It's an API for developers.

Uses [Architectury API](https://docs.architectury.dev/).

## How to use
### For users
1. Download Architectury API from [modrinth](https://modrinth.com/mod/architectury-api/versions) or [curseforge](https://www.curseforge.com/minecraft/mc-mods/architectury-api/files).
2. Download the latest release from the [releases](https://github.com/polvallverdu/FancyVideo-API-PolCinematics/releases) page.

### For developers

You can you use it with Gradle by adding the following to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation("com.github.polvallverdu:FancyVideo-API-PolCinematics:{version or commit hash}")
}
```

And for fabric/forge subprojects:
```groovy
dependencies {
   modApi("com.github.polvallverdu:FancyVideo-API-PolCinematics-{fabric/forge):{version or commit hash}")
}
```

## Past contributors
- [Nick1st](https://github.com/Nick1st)

## Currently supported platforms
- Windows 10/11
- macOS
- Linux
