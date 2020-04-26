[![Pipeline Status](https://gitlab.com/stasys/int-shipment-discount-module/badges/master/pipeline.svg)](https://gitlab.com/stasys/int-shipment-discount-module/pipelines)

## Build and run
### Prerequisites
+ Java 11 JDK installed ([download](https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=openj9))
### Build
This will result in executable named `shipment-discount-module-1.0-SNAPSHOT.jar` built in project's `/build/libs` directory.
#### Run acceptance tests
```shell script 
# In *nix like environments
./gradlew clean acceptance
# In Windows
gradlew.bat clean acceptance
```
#### Make executable
To build service executable issue
```shell script 
# In *nix like environments
./gradlew clean jar
# In Windows
gradlew.bat clean jar
```
This will result in executable named `shipment-discount-module-1.0-SNAPSHOT.jar` built in project's `/build/libs` directory.
#### Run executable
```shell script 
# to process specific file
java -jar shipment-discount-module-1.0-SNAPSHOT.jar myInput.txt
# providing no input searches for default input file named input.txt
java -jar shipment-discount-module-1.0-SNAPSHOT.jar
```