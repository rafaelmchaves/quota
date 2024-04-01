# quota

TODOs:

- Rethink and refactor name of classes
- maybe create a redis to include the quota numbers
- Create diagrams explaining the code
- Update readme
- Implement Unit tests
- Use of ConcurrentHashMap?

## Decisions

I used the MVC pattern to send and retrieve data from the controller to the repository. Since the project was quite simple, I didn't introduce unnecessary complexity in the layer architecture.

Data flows between the layers as follows: controller -> service -> repository.

When passing data from one layer to another, I always perform data conversion. For example, the controller receives data
in the form of UserRequest, which is converted to the User model in the service layer, and when passed to the repository layer,
it is converted to the UserEntity class object. I've clearly separated what is input, what is model, and what is data output.

To determine which database to use, I implemented the strategy pattern, where you can choose which implementation to use based on the time. 
I believe that this way, if we add another database or have a change in the rule of which database to use, it's easy to change the code.
This rule applies only when I'm querying users in the database.

When making any changes to user data or creating a user, I persist this information in both databases, prioritizing data consistency across both DBs.
Firstly, I save the data in MySQL, and then I persist the information in Elasticsearch.

There are some problematic scenarios regarding this. For example, imagine if the Elasticsearch database or our service crashes after inserting data into MySQL, 
or even if there was a bug in the implementation at the moment of saving to Elasticsearch and we received an exception. 
In these cases, the data would be inconsistent between the two databases, with the user data existing in MySQL but not in Elasticsearch. 
To solve this problem, I made the method that performs this control transactional (using the @Transactional annotation). 
This ensures that data is never saved in MySQL until it's successfully saved in Elasticsearch.

## Future

Here I will discuss some future possibilities on how we can evolve the architecture of this service.

### Salvar os dados em dois bancos de dados

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

### Retries

We can implement retries if any errors or delays occur, for example, if Elastic was down for 2 seconds or if there was a network issue. 
If this happens, we can retry. We can put retries on all calls to external components (such as databases and message brokers).

If, even after the retry, the problem persists, we can try again after some time. Of course, we'll need to set a limit and let the exception be thrown.

One problem this entails is if the service is down for a longer period and we continue to retry, overloading the server with calls. To address this type of situation, we can use a circuit breaker, for example. 
The circuit breaker will stop sending all requests, reducing the load on the server and allowing it to return to normal. The circuit breaker will release the requests once it identifies that the service has returned to normal.

### Monitoring

It's very important we monitor everything in production, so one thing that we can do in the future is add more logs and 
capture these logs by a tool, and create a dashboard with information and insights about the service.

This way we know how many requests we have every day(or every hour), errors, how the clients are interacting with the platform, and identify bottlenecks.

