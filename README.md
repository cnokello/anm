**Prerequisites**

* Solr - version 6 or later
* Orace JDK - version 1.7 or later
* SBT - version 0.13 or later

** Configuration**

* Edit the `airports_near_me.properties` file to reflect the setup of your system. This file is located in the resources sub directory.

**Build and run**

* Run `sbt clean run` to test the application
* Run `sbt clean assembly` to build a deployable jar file of the application

**Test shortest distance search**

* Once the application is started, navigate to 

`http://${HOST_NAME}:${PORT}/airports/near_me?lat=${LAT}&lon=${LON}`

replacing the variables as needed.

**NOTE**
If you wish to index the relevant data on application startup, turn on the preprocessor by setting the `preprocessor.run` property to `1`. 

**Next steps**

Configure a docker container:

* Install solr;
* Retrieve and build the source code from github;
*  Integrate metrics collection and monitoring. 