# Quota Vicarius

As a BE developer, your task is to improve the efficiency of our Web API usage by preventing abuse caused by excessive requests from multiple users (quota).

To accomplish this, implement a robust access-limiting mechanism that ensures optimal performance and resource utilization.

The new API should accept up to X API requests per user (for instance, 5 requests per user) - Beyond the quota of X requests, the user should be blocked.

Unfortunately, our users reside in two different sources: one in Elastic and one in MySQL.

During the day (9:00 - 17:00 UTC), we use MySQL as a source, During the night (17:01 - 8:59), we use Elastic.

Create a new Spring Boot application that can be run and tested. In your coding, consider scalability, extensibility, and design patterns.You should implement one database (as localhost), and the other implementation can be printing functions only.

Just pointing out:

    Please, do not use third-party libraries for the quota mechanism (spring boot starter etc.).

    No authentication is required for the server app service

    Assume single instance of your server app service

    You don’t need to take care of data in database. for your testing you can seed some users.

    If you want to expand the User model - do it - add fields

    time\recycle is not something you should care about. while the user reached his quota - he is locked

 

API functions:

    CRUD functions for the user model: (should not be monitored as quota)

    createUser(User user);

    getUser(String userId);

    updateUser(String userId, User updatedUser);

    deleteUser(String userId);

    consumeQuota(@PathVariable String userId);

        This function is the main function used for access and counting the users’ quotas.

    getUsersQuota();
       This function returns all users and their quota statuses.

*** Bonus: Add unit testing for your solution.


User model:
public class User { private String id; private String firstName; private String lastName; private LocalDateTime lastLoginTimeUtc; }

## How to execute

Script of mySql: user_quota.sql

It's a Spring boot project with gradle:
```
./gradlew bootRun
```
Or use an IDE to execute in local machine.

I included a docker-compose.yml file to run up the Mysql and Redis databases in local machine.

## Decisions

I used the MVC pattern to send and retrieve data from the controller to the repository. Since the project was quite simple, I didn't introduce unnecessary complexity in the layer architecture(like hexagonal arch etc).

Data flows between the layers as follows: controller -> service -> dao.

When passing data from one layer to another, I always perform data conversion. For example, the controller receives data
in the form of UserRequest, which is converted to the User model in the service layer, and when passed to the repository layer,
it is converted to the UserEntity class object. I've clearly separated what is input, what is model, and what is data output.

To determine which database to use, I implemented the strategy pattern, where you can choose which implementation to use based on the time. 
I believe that this way, if we add another database or have a change in the rule of which database to use, it's easy to change the code.
This rule applies only when I'm querying users in the database.

I choose Redis to save the quota information of a user as primary database. Redis is fast to read this information because is a key/value database, and it's easy to install.
But for the future of the project(in a real project), I would choose dynamoDB or Casssandra as my primary database to record the quotas.

As a single instance that was described in the problem, I'm using local cache. That way, we reduce the throughput in the database.

The diagram below shows the architecture for consume quota.

![Diagram](/assets/consume-quota-diagram.png?raw=true "Consume quota Diagram")

When making any changes to user data or creating a user, I persist this information in both databases, prioritizing data consistency across both DBs.
Firstly, I save the data in MySQL, and then I persist the information in Elasticsearch.

There are some problematic scenarios regarding this. For example, imagine if the Elasticsearch database or our service crashes after inserting data into MySQL, 
or even if there was a bug in the implementation at the moment of saving to Elasticsearch and we received an exception. 
In these cases, the data would be inconsistent between the two databases, with the user data existing in MySQL but not in Elasticsearch. 
To solve this problem, I made the method that performs this control transactional (using the @Transactional annotation). 
This ensures that data is never saved in MySQL until it's successfully saved in Elasticsearch.

![Diagram](/assets/persistUser.png?raw=true "How to persist user")

![Diagram](/assets/persistUserRollback.png?raw=true "Rollback when something was wrong to persist in the elastic database")

## Future and Improvements (TO DO list)

Here I will discuss some future possibilities on how we can evolve the architecture of this service.

### Create a distributed Redis cache
I'm using a local cache because it's a single instance only solution. But, for the future, with more instances of the application, it will be necessary to use global cache.

### Create more unit tests
Some class tests should be implemented, for example, UserService, QuotaRepositoryImpl MySqlDaoImpl etc.

### Retries

We can implement retries if any errors or delays occur, for example, if Elastic was down for 2 seconds or if there was a network issue.
If this happens, we can retry. We can put retries on all calls to external components (such as databases and message brokers).

If, even after the retry, the problem persists, we can try again after some time. Of course, we'll need to set a limit and let the exception be thrown.

One problem this entails is if the service is down for a longer period and we continue to retry, overloading the server with calls. To address this type of situation, we can use a circuit breaker, for example.
The circuit breaker will stop sending all requests, reducing the load on the server and allowing it to return to normal. The circuit breaker will release the requests once it identifies that the service has returned to normal.

![Diagram](/assets/retries.png?raw=true "Retries")

### About saving data in two databases

To maintain good system performance, especially if we receive many future requests, I believe we can make future changes to this architecture.
There is a scenario that could occur, for example, if MySQL or Elastic takes too long to execute operations and times out frequently.
Errors may become frequent, and operations may become slow. Additionally, if errors occur frequently or the system becomes slow,
users tend to make more requests in the hope that their request will finally be executed.
To resolve this scenario, we need to evolve this architecture into a more complex one. For example, we can save the data in Elastic asynchronously by sending it to a
message broker, which will be consumed by another service responsible for Elastic. This new service would consume the data from the broker and save it in Elastic, making the solution more scalable.

This approach creates another problem because if there is an error when saving to Elastic, the data would be saved in MySQL but not in Elastic.
We could use the Outbox Pattern to solve this type of situation, as shown in the image below.

In this pattern, we have an additional table called the Outbox table, where we would save the data in addition to saving it in the users table.
Instead of sending it to a message broker, we use a Message Relay service that consumes from this table periodically and sends it to the message broker.
Thus, the service responsible for Elastic will consume from the broker and save the data there.

If saved correctly in Elastic, we remove or mark the data as consumed in the outbox table. If an error occurs before saving in Elastic, it would not be a problem because the service
would run again, and the data would be saved.

If the data is saved in Elastic and the service crashes before removing the data from the outbox table, we have a data duplication problem, if it is an operation
that is not idempotent (in the case of POST, for example). To solve this, we can add a unique UUID for each transaction, thus ensuring no duplicates.

![Diagram](/assets/futureArchSaveUser.png?raw=true "Future arch in order to save user in two databases")

### Pagination

When we search all user quota (getUsersQuota), it would be advantageous to utilize pagination to retrieve data from the database in smaller groups (e.g., 10 at a time) rather than retrieving all data at once.

### Monitoring

It's very important we monitor everything in production, so one thing that we can do in the future is add more logs and 
capture these logs by a tool, and create a dashboard with information and insights about the service.

This way we know how many requests we have every day(or every hour), errors, how the clients are interacting with the platform, and identify bottlenecks.

