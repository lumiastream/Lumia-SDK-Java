# The official Lumia Stream SDK for Java

## Publishing

First update the version in `build.gradle.kts`

Then publish by running:

```
./gradlew publishToMaven
./gradlew publish --no-daemon --no-parallel
./gradlew closeAndReleaseRepository
```
