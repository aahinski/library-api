# Library API

### How to launch

To run an application on your computer you need to set your own parameters in the 'application.properties' file. These parameters are:
* #### server.port
    Describe what port will api use. The default value is 8080.

* #### spring.data.* and spring.jpa.*

    Describe what database api will use and the values that are needed to be assigned to connect the selected database. If your database is not MySQL, you will also need to add the dependency in 'pom.xml' file corresponded to the database of your choice.

After configuring 'application.properties' file, you will have to use Swagger(which is available at /swagger-ui/ url) or other program to test REST API. First, you will need to sign up, because the majority of urls are not available to the not authentificated users. And after that you can feel to be free to test the api.
