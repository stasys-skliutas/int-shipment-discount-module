[![Pipeline Status](https://gitlab.com/stasys/int-shipment-discount-module/badges/master/pipeline.svg)](https://gitlab.com/stasys/int-shipment-discount-module/pipelines)

## Build and run
### Prerequisites
+ Java 11 JDK installed ([download](https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=openj9))
### Build
To build service executable issue
```shell script 
# In *nix like environments
./gradlew clean jar
# In Windows
gradlew.bat clean jar
```
This will result in executable named `shipment-discount-module-1.0-SNAPSHOT.jar` built in project's `/build/libs` directory.