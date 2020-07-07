# Restaurant online ordering system


## 1 Description
The online ordering system simplifies the ordering process for both the customer and
the restaurant. The system presents an interactive menu with all available options in an easy to use
manner. Customer can choose one or more dishes to place an order which will land in the basket. Customers can view all the
order details in the cart before checking out. In the end, the customer gets order confirmation. When the order is
confirmed, it will insert into the database. Information about all orders may be retrieved in pretty much real-time.



## 2 Installation

### 2.1 Requirements
  * jdk 11 (or above)
  * Maven
  * PostgreSQL (9+)
  * Git (optional)

### 2.2 Compiling and running

For correct usage of application you need:
   1. Create a Postgres database. Enter into psql and execute:
      ```
      CREATE DATABASE restaurant
      ```
      
   2. Download project sources. The simplest way is cloning a repo with command      
      ```
      git clone https://gitlab.mff.cuni.cz/teaching/nprg045/kliber/rp-guliev.git
      ```      
      
   3. Navigate to a directory with project (with pom.xlm) and execute
      ```
      mvn clean package -DDATABASE_USER=<your database login> -DDATABASE_PASSWORD=<your database password> -DDATABASE_URL=<database url>
      
      ```
      Required variables:
         - `DATABASE_USER` - your database login
         - `DATABASE_PASSWORD` - your database password
         - `DATABASE_URL` - is optional variable. If doesn't present, will be used a default value `jdbc:postgresql://localhost:5432/Restaurant`. Url must start with 'jdbc:postgresql://<url to your database>:<port>/<database name>
   
   4. After the previos command has been exceuted, your will see *.jar file into ./target directory.
      Navigate to ./target directory and execute command to start the application
      ```
      java -DDATABASE_USER=<your database login> -DDATABASE_PASSWORD=<your database password> -DDATABASE_URL=<database url> -jar ./demo-0.0.1-SNAPSHOT.jar
      ```
      The application will be allowed on http://localhost:8888/


## Using an application

For the first time it is required to create an admin in your pgAdmin database.
All new users have to register and after that sign in their accounts\.

The system provides three types of user roles.\
*For ADMIN role:*\
After starting an application admin is able to check and manage all the users,dishes,dish types,ingredients and orders in system.\
All table's data is displayed under corresponding pages which you can get via the buttons on the navigation bar. \
For all these entities it is possible to apply all CRUD methods such as creating new dish,updating existing dish type or deleting a user from system.\

*For COOK role:*\
Cook also has ability to manipulate all systems data besides users.\
In orders, COOK can see all orders with status IN_PROCESS and click button to finish cook. 

*For USER role:*\
Customers view all the existing dishes on the Dish page,where they can choose the count for each particular dish and add it to basket.\
On basket page users see the whole information about their orders and proceed with checkout.\
After checkout, users see the confirmation page and the status of order changes to IN_PROGRESS.\
After finishing the cooking,cook submits the order as done and the status of order changes to WAIT_PAYMENT.\
Users can see all their orders on Order page.Once order got the status WAIT_PAYMENT,user must click on Pay button and redirect to payment page.
On this step user should write a correct amount to pay and submit the form.\
Congratulations!You have purchased the order and now the status is changed to DONE. 

After finishing of the application all users must to Log out from their accounts.  


## Build with
[Java](https://docs.oracle.com/en/java/) - Backend language\
[Springboot](https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/) (MVC, Security, Data JPA)\
[Thymeleaf](https://www.thymeleaf.org/) - A server-side Java template engine\
[Maven](https://maven.apache.org/) - Dependency Management\
[HTML, CSS](https://devdocs.io/html/) - Basis of building Web pages and Web Applications\
[PostgreSQL](https://www.postgresql.org/docs/) - Database Management


## Developer
Eldar Guliyev

