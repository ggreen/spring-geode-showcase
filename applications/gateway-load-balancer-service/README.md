
```shell
curl -X GET "http://localhost:8080/geode/v1/test/123" -H "accept: application/json;charset=UTF-8"
```


```shell
curl -X PUT "http://localhost:8080/geode/v1/test/123?op=PUT" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":\"123\",\"name\":\"test\"}"
```