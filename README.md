# gs-spring-cloud-stream
IESO demand data retrieval and display on website using SCS framework


### 下载project
```git clone https://github.com/niconicolii/gs-spring-cloud-stream.git```

### 更新project
##### local没有更改
`git pull`

##### 当本地有需要上传的更改
1. 保存更改
   - 用 `git add <文件名>` 添加本地有更改的文件，如果有多个有更改的可以`git add`多次，也可以用`git add . `上传所有更改
   - 用`git commit -m "<message>"` 给这次更改内容加一个description
2. 为了确保本地project同步repository最新进展，用`git pull`下载最新内容
3. 用`git push` 完成上传本地内容


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

