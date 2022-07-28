GemFire ​​descargada localmente(feminine)

```shell
cd /Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-9.15.0/bin
```

Inicie el comando de línea de comandos de GemFire ​​gfsh

```shell
./gfsh
```

nicie GemFire ​​Locator que se usa para equilibrar la carga de los clientes y conectar los nodos de datos del clúster
- Puede tener varios localizadores para alta disponibilidad

```shell
start locator --name=locator
```

Inicie un servidor GemFire ​​donde se almacenarán los datos

 
```shell
start server --name=redisServer1  --locators=localhost[10334]  --server-port=40404 --J=-Dgemfire-for-redis-port=6379  --J=-Dgemfire-for-redis-enabled=true  --classpath=/Users/devtools/repositories/IMDG/gemfire/gemfire-for-redis-apps-1.0.0/lib/*   
```

Puede tener varios servidores en un clúster para obtener datos de alta disponibilidad con gran consistencia y tolerancia a fallas.

```shell
start server --name=redisServer2 --locators=localhost[10334]  --server-port=40405   --J=-Dgemfire-for-redis-port=6380  --J=-Dgemfire-for-redis-enabled=true --classpath=/Users/devtools/repositories/IMDG/gemfire/gemfire-for-redis-apps-1.0.0/lib/*
```

Ver detalles del clúster

```shell
open http://localhost:7070/pulse
```
----------------
Conéctese con la CLI de Redis


```shell
cd /Users/devtools/repositories/IMDG/redis
./cli.sh 
```


Ejecutar PING

```shell
127.0.0.1:6379> PING
```


Ver todos los comandos de Redis admitidos

[Comandos de Redis compatibles](https://docs.vmware.com/en/VMware-Tanzu-GemFire-for-Redis-Apps/1.0/tgf-for-redis-apps/GUID-compatible_redis_commands.html)

Ejemplo de aplicación Redis


applications/extended-languages/dotnet/Program.cs


```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/applications/extended-languages/dotnet
```

```shell
dotnet build
```


```shell
dotnet run
```
