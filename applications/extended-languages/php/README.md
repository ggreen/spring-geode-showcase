# PHP Apache Geode/GemFire example

This application is a simple example of connecting to 
an [Apache Geode](https://geode.apache.org/) / [GemFire](https://tanzu.vmware.com/gemfire) 
NoSQL in-memory data platform with the PHP 
programming language using the [experimental Redis API](https://geode.apache.org/docs/guide/114/developing/geode_apis_compatible_with_redis.html).


This example requires [Apache Geode](https://geode.apache.org/) version 1.14 or higher. 


# Installation of the Redis client APIs


```shell
pecl install redis

pickle install redis
```

# Local Cluster Startup

Enable the Redis Adapter in Apache Geode version 1.14 or higher

See [Host Machine Requirements](https://geode.apache.org/docs/guide/114/getting_started/system_requirements/host_machine.html)

Note that the HTTP REST endpoint will also be enabled

```shell
start locator --name=locator --initial-heap=1g --max-heap=1g --J=-Dgemfire.statistic-archive-file=locator.gfs --J=-D-gemfire.statistic-sampling-enabled=true --J=-Dgemfire.archive-disk-space-limit=5 --J=-Dgemfire.archive-file-size-limit=1 --J=-Dgemfire.conserve-sockets=false
configure pdx --read-serialized=true --disk-store=DEFAULT

start server --name=server1 --locators=localhost[10334] --J=-XX:+UseG1GC  --J=-Xms3g --J=-Xmx3g   --use-cluster-configuration=true  --compatible-with-redis-bind-address=localhost --compatible-with-redis-port=6379 --compatible-with-redis-password=CHANGEME --start-rest-api=true --http-service-port=8000 --http-service-bind-address=localhost --statistic-archive-file=server1.gfs --J=-Dgemfire.log-disk-space-limit=3   --J=-Dgemfire.log-file-size-limit=1 --J=-Dgemfire.statistic-archive-file=server1.gfs --J=-D-gemfire.statistic-sampling-enabled=true  --J=-Dgemfire.archive-file-size-limit=1 --J=-Dgemfire.conserve-sockets=false
  
```


# Testing the application


```shell
php redis-put-get.php
```


Export output

```
Hello world 
```


## Experiment Redis API Notes

- Redis adapter uses a Partition region
- Data persistent is not currently supported
- Apache Geode 1.14 has one redundant copy of data 
- Apache Geode 1.15 supports a configurable redundant copies (between 0-3).  
