# GOOGLE CLOUD MESSAGING REST SERVICE #

## Environment: ##

* JDK 1.7
* Maven 3
* Apache Tomcat 7.0.47
* Maven 3.1.1
* Spring 3.2.4.RELEASE

## INSTRUCTIONS ##
* add your gcm_server_key into ${PROJECT_HOME}/src/main/resources/configuration.properties file. Something like gcmserverkey=AIyePnUNq4H1MlOqDXlB9oMdAZvWaEOCVz7RBA4
* **mvn clean install** in ${PROJECT_HOME} folder (where pom.xml file is)
* copy target/gcm-rest.war file into ${APACHE_TOMCAT_HOME}/webapps/ folder
* start Apache Tomcat

## API ##
