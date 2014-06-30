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
* Registrations
```
Url: http://YOUR_SERVER_IP:PORT/gcm-rest/api/registrations
Content-Type: application/x-www-form-urlencoded
Method: POST
Params: deviceId=APA91bE9f_SCHrrUvTlkibvAyfpk3Ai9YoEIAPhn50tVkryBLolM0RHdbh53tC27VdRcMTWwyervn4zL4SiDewp103qV1Rx_AaFs9szEnT1TKuptWm9p-4WLuGUiVvDy2VVoMy5X2YupjtKD-XA8Bf6b-4MW7U_mdojhU9JB1CD0-MIUW9qFNY0
```

* Notifications
```
Url: http://YOUR_SERVER_IP:PORT/gcm-rest/api/notifications
Content-Type: application/json
Method: POST
Request Raw Body: 
{
	"badge":1,
	"title":"Notification title",
	"message":"A message",
	"deviceIdsToSend":[
	"APA91bE9f_SCHrrUvTlkibvAyfpk3Ai9YoEIAPhn50tVkryBLolM0RHdbh53tC27VdRcMTWwyervn4zL4SiDewp103qV1Rx_AaFs9szEnT1TKuptWm9p-4WLuGUiVvDy2VVoMy5X2YupjtKD-XA8Bf6b-4MW7U_mdojhU9JB1CD0-MIUW9qFNY0",
	"OAN64bE7f_SCHrrUvTlkibvAyfpk9Ai3YoEIAPhn49tVkryaaOlM0RHdbh33tC42VdRcMTWwyervn5zL6SiDewp456qV1Rx_AaFs1szEnT1TKuptWm4p-5WLuGUiVvDy7VVoMy8X0YepjtKD-WA3Bf2a-1MW8P_mdojhU9JB2CD6-MIUW5qFNY4"
	]
}
```