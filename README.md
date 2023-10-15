# Library API

### How to launch

To run the application on your computer, you need to set your own parameters in the 'application.properties' file. These parameters are:

* #### server.port
  Describes the port the API will use. The default value is 8080.

* #### spring.data.* and spring.jpa.*

  Describes the database the API will use and the values needed to connect to the selected database. If your database is not MySQL, you will also need to add the dependency in the 'pom.xml' file corresponding to the database of your choice.

After configuring the 'application.properties' file, you will have to use Swagger (which is available at the /swagger-ui/ URL) or another program to test the REST API. First, you will need to sign up because the majority of URLs are not available to non-authenticated users. Also after signing up the bearer token will be sent to you, that you will have to send with your request in order to be authorized. After that, you are free to test the API.

### About the tests

For now, the tests are uses the same database as main code. Providing other database for the tests didn't work for me, because of the errors. The solutions on overflow etc. also didn't help me to avoid these errors.