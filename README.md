# microservices
Microservices project using springboot.

# microservices
Microservices project using springboot.

Start Services in following order – 
1.	Discover client or service registry (Eureka Server)
2.	Cloud config service
3.	API gateway service
4.	Other microservices (Eureka Clients)

URLs - 

http://192.168.113.98:9191/api/catalogue/homepageCatalogue -- GET
http://192.168.113.98:9191/api/catalogue/addToCart -- POST

Payload - 
{
      "id": 11,
      "name": "Micromax 10",
      "description": "Micromax Mobile Phone",
      "price": 12999,
      "category": "Mobile"
}

http://192.168.113.98:9191/api/orders/displayCart/A -- GET

http://192.168.113.98:9191/api/orders/checkout/A -- POST


Following services are present -
1. Catalogue Service - To view all the products and add the product to cart
2. Order Service - View the products added to cart and make payment
3. Payment Service - Complete the payment transaction
4. Cloud Config Service - To read all the configuration information from centralized location
5. Cloud API gateway Service - provide single point of entry to access all the services in the backend
6. Service Registry - To register all the microservices to discovery server.



1. Catalogue Service - Create a new spring boot project, to show all the products in the store.
Show cart - http://localhost:8081/api/catalogue/homepageCatalogue
Add to cart - http://localhost:8081/api/catalogue/addToCart

payload - 
{
      "id": 1,
      "name": "RedmiNote4",
      "description": "Xiaomi Mobile Phone",
      "price": 13999,
      "category": "Mobile"
}

addToCart makes a post call to OrderService for saving the items in cart using restTemplate.

2. Order Service - Create a new spring boot project to add items to cart, catalogueService makes a call to orderService for saving the item in cart.

Show Items in cart - http://localhost:8082/api/orders/displayCart/A
Make Payment - http://localhost:8082/api/orders/checkout/A

For making payment, orderService calls PaymentSvc using Open Feign Client.

3. Payment Service - Create a new spring boot project to facilitate payment

4. Service Registry - 
Without service discover, we will end up hardcoding the URLs of microservice while invoking them.
Like http://localhost:8081/api/catalogue/homepageCatalogue

Create a spring boot project, add Eureka Server dependecy and then add @EnableEurekaServer to the main class.
Discovery service runs on port 8761 by default, discovery server should not register with itself. so add below code in application.yml

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false

In Catalogue, Order and Payment Service, we need to add Eureka Client dependecy and register with eureka server. Registering individually is avoided using cloud config server.

Now, we can invoke the service using the service name instead of IP and Port. 
Like http://ORDERS-SERVICE/api/catalogue/addToCart

5. API Gateway - 
API Gateway is a concept of having a single point of entry to access all the services in the backend
Without API gateway, from front end we need to explicitly call the appropriate API URLs. If there is any change in microservice, this would require a change in front end as well. 
API gateway provides an abstraction layer. API gateway acts as router. This is referred as edge microservice as well.

Create a new Spring boot project, add spring-cloud-starter-gateway dependency. Runs on port 9191
All the routes of microservice is configured in the application.yml

URL after adding API Gateway - 
http://192.168.113.98:9191/api/catalogue/homepageCatalogue -- GET
http://192.168.113.98:9191/api/catalogue/addToCart -- POST

Payload - 
{
      "id": 11,
      "name": "Micromax 10",
      "description": "Micromax Mobile Phone",
      "price": 12999,
      "category": "Mobile"
}

http://192.168.113.98:9191/api/orders/displayCart/A -- GET

http://192.168.113.98:9191/api/orders/checkout/A -- POST


6. Cloud Config Service - 
Without cloud config server, we need to add discovery service or service registry URL or other common configuration like zipkin in all the microservices. This can be avoided by creating a config service, which reads all the information from centralized file location like github. This ensures any change in discovery service need not be updated in all other service. Just a change in file stored in github will take care of loading the service configuration for all the service.

Add cloud-config-server dependency.
Add github details from where data needs to be read in application.yml -

spring:
  application:
    name: CONFIG-SERVER
  cloud:
    config:
      server:
        git:
          uri: https://github.com/nithinurs46/config-server
          clone-on-start: true
        default-label: main


Create a bootstrap.yml file in all catalogue, orders, payment service and add below lines to read data from cloud config server -
spring:
  cloud:
    config:
      enabled: true
      uri: http://localhost:9296
	  

As we are using Eureka Service discovery, we need to register all the clients with Eureka Client, by adding register-with-eureka:true in application.yml of all the services.
Rather than doing that, moved all the Eureka client related information to github. So all the services which has bootstrap.yml will read the github config data.


Zipkin and sleuth is used for distributed tracing, it provides span id and trace id for all the routes to identify how the request travelled.

Zipkin server can be downloaded from - https://zipkin.io/pages/quickstart.html

Add Sleuth configuration in cloud config.
spring:
  zipkin:
    base-url: http://127.0.0.1:9411/

Zipkin is disabled by default in all the microservice. It can be enabled by setting the below property to true.
spring:
  zipkin:
    enabled: false
  sleuth:
    enabled: false	


build.txt file includes maven build steps
start-all.bat starts all the services. 
	  
    

