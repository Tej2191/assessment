# BE Assessment

This application was developed to demonstrate the use of stock related data using Spring Boot and MongoDB 

Technologies Used

- Spring Boot 2.7.0
- Spring Data MongoDB
- Lombok
- MongoDB

How to Run this application

Docker needs to be installed in your system for running MongoDB

Then pull the image of mongo,

```shell
$ dcoker pull mongo
```

Run mongo db locally,

```shell
$ docker run -d -p 27017:27017 -v D:/mongodb:/data/db --name mongo_local mongo
```

Then start the JAR file using java

```shell
$ java -jar target\assessment-0.0.1-SNAPSHOT.jar
```

# Future Enchancements
- We can use the Dow Jones API to get the stock data and also push the data back.
- We can build a front end application where we can visualise the data.
- Improve security using Oauth and spring-security in future.
- Use filters to validate the incoming requests.
- We can use a better transaction management approach and rollack policy when inserting data into mongo db. 