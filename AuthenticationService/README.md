Steps:
1. Install redis: http://redis.io/topics/quickstart
2. Start redis on default port 6379
3. Create a schema name userSessionManagement in your MySQL: 
	-> mysql -u root -p password
	-> CREATE DATABASE userSessionManagement
4. dump userSessionManagement.sql using command: mysql -u root -p userSessionManagementNew < userSessionManagement.sql
5. run application using: mvn spring-boot:run
