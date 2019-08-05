# Dream Team Trading Platform

<h5 align=center>Automatic trading platform that will make you money.</h5>

## Project
This project involves implementing automatic trading 
strategies that initiate trades to an order broker based on current and historical prices.
The broker is listening for messages on an ActiveMQ queue 
and responds with an accept confirmation message on a second queue.
The transactions performed by the strategies are recorded in a database.
Different metrics will be shown to the client based on the history of the strategies.
For the project we assume there is a one user, the trader.
We designed a user interface that allows a trader to use 
the algorithms we have implemented (Two Moving Averages and Bollinger Bands).
We also allow the trader to pause, resume, and stop an algorithm as they please.
Our user interface also presents a historical view of trade 
statistics regarding the different strategies the trader initiates.

## Technologies
We will be using the following
* Front-end
    * Angular
    * Bootstrap
    * jQuery
    * canvasJS
* Back-end
    * Java (Spring Boot, Hibernate)
    * ActiveMQ
* Database
    * MySQL

## Deployment
*Requires npm and maven*
#### Front-end
```
$ cd DreamFrontEnd
$ npm install
$ npm start
```


#### Back-end
```
$ cd DreamBackEnd
$ mvn spring-boot:run
```