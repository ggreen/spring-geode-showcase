Goto dir

```shell
cd /Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-9.15.0/bin
```

Start GemFire command line command gfsh 

```shell
./gfsh
```

Start Locator

```shell
start locator 
```

Start server 1
```shell
start server --name=redisServer1  --locators=localhost[10334]  --server-port=40404 --J=-Dgemfire-for-redis-port=6379  --J=-Dgemfire-for-redis-enabled=true  --classpath=/Users/devtools/repositories/IMDG/gemfire/gemfire-for-redis-apps-1.0.0/lib/*   
```


```shell
start server --name=redisServer2 --locators=localhost[10334]  --server-port=40405   --J=-Dgemfire-for-redis-port=6380  --J=-Dgemfire-for-redis-enabled=true --classpath=/Users/devtools/repositories/IMDG/gemfire/gemfire-for-redis-apps-1.0.0/lib/*
```


Connect with Redis CLI


```shell
cd /Users/devtools/repositories/IMDG/redis
./cli.sh 
```


Execute PING

```shell
127.0.0.1:6379> PING

```