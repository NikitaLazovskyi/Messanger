# Quick start

-NOTE! The Docker need to be installed. If it isn't please proceed and install it:
https://www.docker.com/get-started/

## 1. Launching:
- Proceed to the `remote` folder of the root folder by path `messenger/remote`
- Open CLI here and enter next:
`docker-compose up`
- wait till images will be downloaded and deployed on local host

# You can run application by another way:
## 1. Get source
- Clone or download this repository. 

## 2. Build services
- Proceed to the folder `messenger/user-service`
- Open CLI here
- Run next command:
```mvn clean package spring-boot:repackage```
- Do the same actions inside `messanger/message-service` folder

## 3. Run 
- Then you should proceed to `messenger` folder and open your CLI here. 
- Enter next: ```docker-compose up```
- Wait download and deployment were finished.
- Use :)

# Stack
- Microservices
- Java 8
- RESTful
- Spring Boot
- MongoDB & PostgreSQL
- RabbitMQ

# Event-driven architecture
![Diagram of event-driven architecture](https://github.com/NikitaLazovskyi/Messenger/blob/master/images/app_opaque.drawio.png)

# Demo 
## Main logic demo
![Gif-demo](https://github.com/NikitaLazovskyi/Messenger/blob/master/gifs/crud_gif.gif)

## Gif-demo of event-driven architecture
![Gif-demo](https://github.com/NikitaLazovskyi/Messenger/blob/master/gifs/register_user_gif.gif)
<!-- 
## Gif-demo of docker-compose
![Gif-demo](https://github.com/NikitaLazovskyi/Messenger/blob/master/gifs/docker_compose_gif.gif) 
-->
