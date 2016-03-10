Steps:
1. Install redis: http://redis.io/topics/quickstart
2. Start redis on default port 6379
3. Create a schema name userSessionManagement in your MySQL: 
	-> mysql -u root -p password
	-> CREATE DATABASE userSessionManagement
4. dump userSessionManagement.sql using command: mysql -u root -p userSessionManagementNew < userSessionManagement.sql
5. Open MvcConfig.java file, change DB settings as per your configurations. (Url, Username & password) 
6. run application using: mvn spring-boot:run
7. From command line fire: curl -v http://127.0.0.1:8080/login -u john:john. You will find authorization token as x-auth-token, save this token and use it in subsequent calls.
8. To add session information: curl -v http://localhost:8080/addSessionInformation -G -H "x-auth-token:<Token>" -d "sessionInformation=session"
9. To get session information: curl -v http://localhost:8080/getSessionInformation -G -H "x-auth-token:<Token>"
10. To logout: curl -v http://localhost:8080/logout

