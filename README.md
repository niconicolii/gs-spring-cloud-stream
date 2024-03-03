# gs-spring-cloud-stream
IESO demand data retrieval and display on website using SCS framework


### 下载project
git clone https://github.com/niconicolii/gs-spring-cloud-stream.git


### How to Run
1. Download Docker destop, run in terminal:
    ```
    docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
    ```
   Start RabbitMQ on Docker.
3. Open `gs-spring-cloud-stream` using IntelliJ
4. Run `name-source/target/classes/com/example/stream/namesource/NameSourceApplication.class`
5. Run `name-processor/target/classes/com/example/stream/nameprocessor/NameProcessorApplication.class`
6. Run `name-sink/target/classes/com/example/stream/namesink/NameSinkApplication.class`

