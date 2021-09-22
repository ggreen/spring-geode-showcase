# Spring Geode Showcase

The project contains various examples using the In-Memory [NO-SQL](https://en.wikipedia.org/wiki/NoSQL) data management solution [Apache Geode](https://geode.apache.org/)


## OLTP Apache Geode Support

Real-time transactions can be fast and furious. Think about building the next big retail market app. 
Users of the app need to get the answers as quickly as possible. Any slow down to response times will have those users going somewhere else. 
When the application is popular more and more users will come. Having highly scalable and fast data services is essential.
This session will highlight and describe

[What is OLTP?](https://www.oracle.com/database/what-is-oltp/)
What are its characteristics?
What are the data service challenges?

How Apache Geode can be used to meet needs such as

- Performance
- Scalability
- Strong Consistency
- NoSQL characteristics
- High Availability
- Fault Tolerance
- WAN Replication


## Applications


Application                                     | Notes
----------------------------------------------- | ----------------------
[applications/account-location-rest-service](applications/account-location-rest-service)        | Kotlin [Spring Boot](https://spring.io/projects/spring-boot) application thats demonstrates [transactions](https://geode.apache.org/docs/guide/114/developing/transactions/chapter_overview.html)
[applications/account-rest-service](applications/account-rest-service)                          | Java [Spring Boot](https://spring.io/projects/spring-boot) application for [Spring Data Geode](https://spring.io/projects/spring-data-geode) [CRUD](https://docs.spring.io/spring-data/geode/docs/current/reference/html/#gemfire-repositories) operations