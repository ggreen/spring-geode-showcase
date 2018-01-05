# Overview

The following project provides coding example for basic GemFire client API features, along with Spring Data GemFire in Spring Boot Applications.


*Demonstrated Features*

 
 - Conversation from JSON to PDX
 - Read paging examples
 - Co-located regions
 - Usages of GemFire CRUD repositories
 - Querying
 - Continuous Queries 


## Gfsh Start Cluster Commands

To setup a local LDAP server, see the following:

See project [https://github.com/nyla-solutions/gedi-geode/blob/master/gedi-geode-extensions-core/README_LDAP_SecurityMgr.md](https://github.com/nyla-solutions/gedi-geode/blob/master/gedi-geode-extensions-core/README_LDAP_SecurityMgr.md)
	
	start locator --name=local --http-service-bind-address=localhost   --http-service-port=7070 --security-properties-file=/Projects/solutions/gedi/dev/gedi-geode/gedi-geode-extensions-core/src/test/resources/ldap/gfldapsecurity.properties --J=-Dgemfire.security-manager=gedi.solutions.geode.security.ldap.LdapSecurityMgr --classpath=/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/gedi-geode-extensions-core-1.1.0.jar:/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/nyla.solutions.core-1.1.0.jar --enable-cluster-configuration
	
	start server --name=server1 --server-port=9001  --classpath=/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/gedi-geode-extensions-core-1.1.0.jar:/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/nyla.solutions.core-1.1.0.jar --locators=localhost[10334] --security-properties-file=/Projects/solutions/gedi/dev/gedi-geode/gedi-geode-extensions-core/src/test/resources/ldap/gfldapsecurity.properties --J=-Dgemfire.security-manager=gedi.solutions.geode.security.ldap.LdapSecurityMgr --use-cluster-configuration=true

	start server --name=server2 --server-port=9002  --classpath=/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/gedi-geode-extensions-core-1.1.0.jar:/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/nyla.solutions.core-1.1.0.jar --locators=localhost[10334] --security-properties-file=/Projects/solutions/gedi/dev/gedi-geode/gedi-geode-extensions-core/src/test/resources/ldap/gfldapsecurity.properties --J=-Dgemfire.security-manager=gedi.solutions.geode.security.ldap.LdapSecurityMgr  --use-cluster-configuration=true


Note configure PDX 

		configure pdx --auto-serializable-classes="com.fidelity.gemfire.gemfiredemo.domain.*" --read-serialized=true

*Restart the cache server*
	
		shutdown
		
	start server --name=server1 --server-port=9001  --classpath=/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/gedi-geode-extensions-core-1.1.0.jar:/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/nyla.solutions.core-1.1.0.jar --locators=localhost[10334] --security-properties-file=/Projects/solutions/gedi/dev/gedi-geode/gedi-geode-extensions-core/src/test/resources/ldap/gfldapsecurity.properties --J=-Dgemfire.security-manager=gedi.solutions.geode.security.ldap.LdapSecurityMgr --use-cluster-configuration=true

	start server --name=server2 --server-port=9002  --classpath=/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/gedi-geode-extensions-core-1.1.0.jar:/Projects/Financial/Fidelity/dev/demos/gemfire-demo/lib/nyla.solutions.core-1.1.0.jar --locators=localhost[10334] --security-properties-file=/Projects/solutions/gedi/dev/gedi-geode/gedi-geode-extensions-core/src/test/resources/ldap/gfldapsecurity.properties --J=-Dgemfire.security-manager=gedi.solutions.geode.security.ldap.LdapSecurityMgr --use-cluster-configuration=true
		

Create the region definitions

	disconnect
	connect --locator=localhost[10334] --user=admin --password=secret
	create region --name=Order --type=PARTITION
	create region --name=Accounts --type=PARTITION --colocated-with=/Order
	

# HTTP Spring Boot Example

**REST CALL to save order data**

POST

http://localhost:8080/order

Content-type: application/json

	{ "id": "2" }


**REST CALL to save order data multiple orders**

http://localhost:8080/batchOrders

Content-type: application/json

	[{ "id": "2", },
	{ "id": "4"}
	]


**Get 3rd page with size 10**

GET
http://localhost:8080/orders/2/10


**REST CALL to save account data**

http://localhost:8080/saveAccount

Content-type: application/json

	{ "id": "2" }





