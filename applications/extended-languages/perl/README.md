# PERL Apache Geode/GemFire example

This application is a simple example of connecting to
an [Apache Geode](https://geode.apache.org/)/[GemFire](https://tanzu.vmware.com/gemfire)
NoSQL in-memory data platform with the Perl
programming language using the HTTP REST API.

# Setup

- [Download GemFire](https://network.pivotal.io/products/pivotal-gemfire)


```shell

export GEMFIRE_HOME=/Users/devtools/repositories/IMDG/gemfire/pivotal-gemfire-9.10.10

cd $GEMFIRE_HOME/bin
```


Start Gfsh

```shell
gfsh
```

In Gfsh

```shell
start locator --name=locator1
configure pdx --read-serialized=true --disk-store=DEFAULT
start server --name=server1 --start-rest-api=true --http-service-port=8000 --http-service-bind-address=localhost
```

Create Region in Gfsh

```shell
create region --name=test --type=PARTITION
```

Open Swagger

```shell
open http://localhost:8000/geode/docs/index.html
```


Curl Test

```shell
curl -X POST "http://localhost:8000/geode/v1/test?key=123" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"id\" : \"123\", \"name\" : \"123\"}"
```

```shell
curl -X GET "http://localhost:8000/geode/v1/test/123" -H "accept: application/json;charset=UTF-8";echo
```


--------------------

# Perl Testing

Put data in *test* region

```shell
perl http-post.pl test nyla2 '{"id":"nyla2","name": "Nyla2"}'
```

Get data in *test* region

```shell
perl http-get.pl test nyla2
```

